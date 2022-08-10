/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See https://www.adama-platform.com/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber ( http://jeffrey.io )
 */
package org.adamalang.common.capacity;

import org.junit.Assert;
import org.junit.Test;

public class BinaryEventOrGateTests {
  @Test
  public void flow() {
    StringBuilder sb = new StringBuilder();
    BinaryEventOrGate gate = new BinaryEventOrGate() {
      @Override
      public void start() {
        sb.append("START");
      }

      @Override
      public void stop() {
        sb.append("STOP");
      }
    };
    gate.a(true);
    gate.b(true);
    gate.a(false);
    gate.b(false);
    gate.a(true);
    gate.b(true);
    gate.a(false);
    gate.b(false);
    Assert.assertEquals("STARTSTOPSTARTSTOP", sb.toString());
  }
}
