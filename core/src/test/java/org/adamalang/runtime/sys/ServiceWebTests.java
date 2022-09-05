/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See https://www.adama-platform.com/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber ( http://jeffrey.io )
 */
package org.adamalang.runtime.sys;

import org.adamalang.common.Callback;
import org.adamalang.common.ErrorCodeException;
import org.adamalang.common.TimeSource;
import org.adamalang.common.metrics.NoOpMetricsFactory;
import org.adamalang.runtime.ContextSupport;
import org.adamalang.runtime.LivingDocumentTests;
import org.adamalang.runtime.data.Key;
import org.adamalang.runtime.mocks.MockTime;
import org.adamalang.runtime.natives.NtPrincipal;
import org.adamalang.runtime.natives.NtDynamic;
import org.adamalang.runtime.remote.Deliverer;
import org.adamalang.runtime.sys.mocks.MockInstantDataService;
import org.adamalang.runtime.sys.mocks.MockInstantLivingDocumentFactoryFactory;
import org.adamalang.runtime.sys.mocks.NullCallbackLatch;
import org.adamalang.runtime.sys.web.WebContext;
import org.adamalang.runtime.sys.web.WebDelete;
import org.adamalang.runtime.sys.web.WebGet;
import org.adamalang.runtime.sys.web.WebResponse;
import org.adamalang.translator.jvm.LivingDocumentFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ServiceWebTests {
  private static final CoreMetrics METRICS = new CoreMetrics(new NoOpMetricsFactory());
  private static final Key KEY = new Key("space", "key");
  private static WebContext CONTEXT = new WebContext(NtPrincipal.NO_ONE, "Origin", "1.2.3.4");
  private static final String SIMPLE_CODE_MSG = "@static { create { return true; } }" +
      "@web get / {\n" + "  return {html:\"root\"};\n" + "}\n" + "\n" + //
      "@web get /fixed {\n" + "  return {html:\"fixed path\"};\n" + "}\n" + "\n" + //
      "@web options /fixed {\n" + "  return {cors:true};\n" + "}\n" + "\n" + //
      "@web get /path0/$x:int {\n" + "  return {html:\"path integer:\" + x,cache_ttl_seconds:42};\n" + "}\n" + "\n" + //
      "@web get /path1/$x:double {\n" + "  return {html:\"path double:\" + x};\n" + "}\n" + "\n" + //
      "@web get /path2/$x:long {\n" + "  return {html:\"path long without child:\" + x};\n" + "}\n" + "\n" + //
      "@web get /path2/$x:long/child {\n" + "  return {html:\"path long with child: \" + x + \"!\"};\n" + "}\n" + "\n" + //
      "@web get /path3/$a* {\n" + "  return {html:\"tail:\" + a};\n" + "}\n" + "\n" +
      "@web delete /medelete/$a* {\n" + "  return {html:\"deleted:\" + a};\n" + "}\n" + "\n" +
      "@web get /\"something.js\" {\n" + "  return {js:\"function... blah...blah...blah\"};\n" + "}\n" + "\n" +
      "@web get /\"something.css\" {\n" + "  return {css:\".class{}\"};\n" + "}\n" + "\n" +
      "@web get /asset/$a* {\n" + "  return {asset:@nothing,asset_transform:\"foo\"};\n" + "}\n" + "\n" +
      "@web get /path3/$a:string/child {\n" + "  return {html:\"abort tail and go with direct child:\" + a};\n" + "}";

  @Test
  public void web_basic() throws Exception {
    LivingDocumentFactory factory = LivingDocumentTests.compile(SIMPLE_CODE_MSG, Deliverer.FAILURE);
    MockInstantLivingDocumentFactoryFactory factoryFactory =
        new MockInstantLivingDocumentFactoryFactory(factory);
    TimeSource time = new MockTime();
    MockInstantDataService dataService = new MockInstantDataService();
    CoreService service = new CoreService(METRICS, factoryFactory, (bill) -> {}, dataService, time, 3);
    try {
      NullCallbackLatch created = new NullCallbackLatch();
      service.create(ContextSupport.WRAP(NtPrincipal.NO_ONE), KEY, "{}", null, created);
      created.await_success();
      CountDownLatch latch = new CountDownLatch(9);
      service.webGet(KEY, new WebGet(CONTEXT, "/", new TreeMap<>(), new NtDynamic("{}")), new Callback<>() {
        @Override
        public void success(WebResponse value) {
          Assert.assertEquals("root", value.body);
          latch.countDown();
        }

        @Override
        public void failure(ErrorCodeException ex) {

        }
      });
      service.webOptions(KEY, new WebGet(CONTEXT, "/fixed", new TreeMap<>(), new NtDynamic("{}")), new Callback<>() {
        @Override
        public void success(WebResponse value) {
          Assert.assertTrue(value.cors);
          latch.countDown();
        }

        @Override
        public void failure(ErrorCodeException ex) {

        }
      });
      service.webGet(KEY, new WebGet(CONTEXT, "/path0/14", new TreeMap<>(), new NtDynamic("{}")), new Callback<>() {
        @Override
        public void success(WebResponse value) {
          Assert.assertEquals("path integer:14", value.body);
          Assert.assertEquals(42, value.cache_ttl_seconds);
          latch.countDown();
        }

        @Override
        public void failure(ErrorCodeException ex) {

        }
      });
      System.err.println("web deleted --> ");
      service.webDelete(KEY, new WebDelete(CONTEXT, "/medelete/14", new TreeMap<>(), new NtDynamic("{}")), new Callback<>() {
        @Override
        public void success(WebResponse value) {
          System.err.println("web delete success");
          Assert.assertEquals("deleted:14", value.body);
          latch.countDown();
        }

        @Override
        public void failure(ErrorCodeException ex) {
          System.err.println("web delete failure-" + ex.code);
        }
      });
      service.webGet(KEY, new WebGet(CONTEXT, "/something.js", new TreeMap<>(), new NtDynamic("{}")), new Callback<>() {
        @Override
        public void success(WebResponse value) {
          Assert.assertEquals("function... blah...blah...blah", value.body);
          latch.countDown();
        }

        @Override
        public void failure(ErrorCodeException ex) {
          ex.printStackTrace();
        }
      });
      service.webGet(KEY, new WebGet(CONTEXT, "/something.css", new TreeMap<>(), new NtDynamic("{}")), new Callback<>() {
        @Override
        public void success(WebResponse value) {
          Assert.assertEquals(".class{}", value.body);
          latch.countDown();
        }

        @Override
        public void failure(ErrorCodeException ex) {
          ex.printStackTrace();
        }
      });
      service.webGet(KEY, new WebGet(CONTEXT, "/nope", new TreeMap<>(), new NtDynamic("{}")), new Callback<>() {
        @Override
        public void success(WebResponse value) {

        }

        @Override
        public void failure(ErrorCodeException ex) {
          Assert.assertEquals(133308, ex.code);
          latch.countDown();
        }
      });
      service.webGet(new Key("nope", "noep"), new WebGet(CONTEXT, "/", new TreeMap<>(), new NtDynamic("{}")), new Callback<>() {
        @Override
        public void success(WebResponse value) {
        }

        @Override
        public void failure(ErrorCodeException ex) {
          Assert.assertEquals(625676, ex.code);
          latch.countDown();
        }
      });
      service.webGet(new Key("nope", "noep"), new WebGet(CONTEXT, "/asset", new TreeMap<>(), new NtDynamic("{}")), new Callback<>() {
        @Override
        public void success(WebResponse value) {
          Assert.assertNotNull(value.asset);
          Assert.assertEquals("foo", value.asset_transform);
        }

        @Override
        public void failure(ErrorCodeException ex) {
          Assert.assertEquals(625676, ex.code);
          latch.countDown();
        }
      });
      Assert.assertTrue(latch.await(5000, TimeUnit.MILLISECONDS));
    } finally {
      service.shutdown();
    }
  }
}
