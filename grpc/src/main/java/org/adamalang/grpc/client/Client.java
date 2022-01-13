/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.grpc.client;

import org.adamalang.ErrorCodes;
import org.adamalang.common.*;
import org.adamalang.grpc.client.contracts.*;
import org.adamalang.grpc.client.routing.RoutingEngine;
import org.adamalang.grpc.client.sm.Connection;
import org.adamalang.grpc.client.sm.ConnectionBase;

import java.util.*;
import java.util.function.Consumer;

public class Client {
  private final SimpleExecutor routingExecutor;
  private final RoutingEngine engine;
  private final InstanceClientFinder finder;
  private final SimpleExecutor[] executors;
  private final Random rng;

  public Client(MachineIdentity identity) {
    this.routingExecutor = SimpleExecutor.create("routing");
    this.engine =
        new RoutingEngine(
            routingExecutor,
            new SpaceTrackingEvents() {
              @Override
              public void gainInterestInSpace(String space) {}

              @Override
              public void shareTargetsFor(String space, Set<String> targets) {}

              @Override
              public void lostInterestInSpace(String space) {}
            },
            250,
            250);
    this.finder =
        new InstanceClientFinder(
            identity,
            SimpleExecutorFactory.DEFAULT,
            4,
            engine,
            (t, c) -> {
              t.printStackTrace();
            });
    this.executors = SimpleExecutorFactory.DEFAULT.makeMany("connections", 2);
    this.rng = new Random();
  }

  public void getDeploymentTargets(String space, Consumer<String> stream) {
    engine.list(space, new Consumer<TreeSet<String>>() {
      @Override
      public void accept(TreeSet<String> targets) {
        finder.findCapacity(targets, (set) -> {
          for (String target : set) {
            stream.accept(target);
          }
        }, 3);
      }
    });
  }

  public void notifyDeployment(String target, String space) {
    finder.find(target, new QueueAction<>(ErrorCodes.API_DEPLOY_TIMEOUT, ErrorCodes.API_DEPLOY_REJECTED) {
      @Override
      protected void executeNow(InstanceClient client) {
        client.scanDeployments(space, new ScanDeploymentCallback() {
          @Override
          public void success() {
            // TODO: metric
          }

          @Override
          public void failure() {
            // TODO: metric
          }
        });
      }

      @Override
      protected void failure(int code) {
        // TODO: metric
      }
    });
  }

  public Consumer<Collection<String>> getTargetPublisher() {
    return (targets) -> finder.sync(new TreeSet<>(targets));
  }

  public void reflect(String space, String key, Callback<String> callback) {
    engine.get(space, key, (target) -> {
      if (target != null) {
        finder.find(target, new QueueAction<>(ErrorCodes.API_REFLECT_TIMEOUT, ErrorCodes.API_REFLECT_REJECTED) {
          @Override
          protected void executeNow(InstanceClient client) {
            client.reflect(space, key, new Callback<String>() {
              @Override
              public void success(String value) {
                callback.success(value);
              }

              @Override
              public void failure(ErrorCodeException ex) {
                callback.failure(ex);
              }
            });
          }

          @Override
          protected void failure(int code) {
            callback.failure(new ErrorCodeException(code));
          }
        });
      } else {
        callback.failure(new ErrorCodeException(ErrorCodes.API_REFLECT_CANT_FIND_CAPACITY));
      }
    });
  }

  public void create(String agent, String authority, String space, String key, String entropy, String arg, CreateCallback callback) {
    engine.get(space, key, (target) -> {
      if (target != null) {
        finder.find(target, new QueueAction<>(ErrorCodes.API_CREATE_TIMEOUT, ErrorCodes.API_CREATE_REJECTED) {
          @Override
          protected void executeNow(InstanceClient client) {
            client.create(agent, authority, space, key, entropy, arg, callback);
          }

          @Override
          protected void failure(int code) {
            callback.error(code);
          }
        });
      } else {
        callback.error(ErrorCodes.API_CREATE_CANT_FIND_CAPACITY);
      }
    });
  }

  public Connection connect(
      String agent, String authority, String space, String key, SimpleEvents events) {
    ConnectionBase base =
        new ConnectionBase(engine, finder, executors[rng.nextInt(executors.length)]);
    Connection connection = new Connection(base, agent, authority, space, key, events);
    connection.open();
    return connection;
  }

  public void shutdown() {
    finder.shutdown();
    for (SimpleExecutor executor : executors) {
      executor.shutdown();
    }
    routingExecutor.shutdown();
  }
}
