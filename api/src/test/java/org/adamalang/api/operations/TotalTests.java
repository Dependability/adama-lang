/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE'
 * which is in the root directory of the repository. This file is part of the 'Adama'
 * project which is a programming language and document store for board games.
 * 
 * See http://www.adama-lang.org/ for more information.
 * 
 * (c) 2020 - 2021 by Jeffrey M. Barber (http://jeffrey.io)
*/
package org.adamalang.api.operations;

import org.junit.Assert;
import org.junit.Test;

public class TotalTests {
  @Test
  public void flow() {
    Total t = new Total();
    t.inc();
    t.inc();
    t.inc();
    Assert.assertEquals(3, t.get());
    t.dec();
    Assert.assertEquals(2, t.get());
    t.set(42);
    Assert.assertEquals(42, t.get());
  }
}
