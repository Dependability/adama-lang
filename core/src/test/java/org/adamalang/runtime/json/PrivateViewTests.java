/* The Adama Programming Language For Board Games!
 *    See http://www.adama-lang.org/ for more information.
 * (c) copyright 2020 Jeffrey M. Barber (http://jeffrey.io) */
package org.adamalang.runtime.json;

import org.adamalang.runtime.contracts.Perspective;
import org.adamalang.runtime.natives.NtClient;
import org.adamalang.runtime.stdlib.Utility;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class PrivateViewTests {
    @Test
    public void killing() {
        ArrayList<String> list = new ArrayList<>();
        PrivateView pv = new PrivateView(NtClient.NO_ONE, new Perspective() {
            @Override
            public void data(String data) {
                list.add(data);
            }

            @Override
            public void disconnect() {

            }
        }) {

            @Override
            public void dumpViewer(JsonStreamWriter writer) {
            }

            @Override
            public void update(JsonStreamWriter writer) {

            }

            @Override
            public void ingest(JsonStreamReader reader) {

            }
        };
        Assert.assertTrue(pv.isAlive());
        pv.kill();
        Assert.assertFalse(pv.isAlive());
        pv.deliver("{}");
        Assert.assertEquals("{}", list.get(0));
    }
}
