/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See https://www.adama-platform.com/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber ( http://jeffrey.io )
 */
package org.adamalang.transforms;

import org.adamalang.common.Callback;
import org.adamalang.common.ErrorCodeException;
import org.adamalang.connection.Session;
import org.adamalang.transforms.results.AuthenticatedUser;
import org.adamalang.web.io.ConnectionContext;
import org.junit.Assert;
import org.junit.Test;

import java.security.KeyPair;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PerSessionAuthenticatorTests {
  @Test
  public void tokenParsing() {
    try {
      new PerSessionAuthenticator.ParsedToken("{}");
      Assert.fail();
    } catch (ErrorCodeException ece) {
      Assert.assertEquals(995342, ece.code);
    }
    try {
      new PerSessionAuthenticator.ParsedToken("x.x.x");
      Assert.fail();
    } catch (ErrorCodeException ece) {
      Assert.assertEquals(908303, ece.code);
    }
  }

  @Test
  public void keys() throws Exception {
    KeyPair hostKeyPair = PerSessionAuthenticator.inventHostKey();
    PerSessionAuthenticator.decodePublicKey(PerSessionAuthenticator.encodePublicKey(hostKeyPair));
  }

  @Test
  public void anonymous() throws Exception {
    PerSessionAuthenticator authenticator = new PerSessionAuthenticator(null, new ConnectionContext("a", "b", "c", "D"));
    Assert.assertEquals("D", authenticator.assetKey());
    authenticator.updateAssetKey("E");
    Assert.assertEquals("E", authenticator.assetKey());
    Session session = new Session(authenticator);
    CountDownLatch success = new CountDownLatch(1);
    authenticator.execute(session, "anonymous:jeffrey", new Callback<AuthenticatedUser>() {
      @Override
      public void success(AuthenticatedUser who) {
        Assert.assertEquals("jeffrey", who.who.agent);
        Assert.assertEquals("anonymous", who.who.authority);
        success.countDown();
      }

      @Override
      public void failure(ErrorCodeException ex) {

      }
    });
    Assert.assertTrue(success.await(1000, TimeUnit.MILLISECONDS));
  }
}