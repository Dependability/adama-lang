/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See https://www.adama-platform.com/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber ( http://jeffrey.io )
 */
package org.adamalang.api;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.adamalang.common.Callback;
import org.adamalang.common.ErrorCodeException;
import org.adamalang.web.io.*;

public class BillingUsageResponder {
  public final JsonResponder responder;

  public BillingUsageResponder(JsonResponder responder) {
    this.responder = responder;
  }

  public void next(Integer hour, Long cpu, Long memory, Integer connections, Integer documents, Integer messages, Long storageBytes, Long bandwidth, Long firstPartyServiceCalls, Long thirdPartyServiceCalls) {
    ObjectNode _obj = new JsonMapper().createObjectNode();
    _obj.put("hour", hour);
    _obj.put("cpu", cpu);
    _obj.put("memory", memory);
    _obj.put("connections", connections);
    _obj.put("documents", documents);
    _obj.put("messages", messages);
    _obj.put("storageBytes", storageBytes);
    _obj.put("bandwidth", bandwidth);
    _obj.put("firstPartyServiceCalls", firstPartyServiceCalls);
    _obj.put("thirdPartyServiceCalls", thirdPartyServiceCalls);
    responder.stream(_obj.toString());
  }

  public void finish() {
    responder.finish(null);
  }

  public void error(ErrorCodeException ex) {
    responder.error(ex);
  }
}
