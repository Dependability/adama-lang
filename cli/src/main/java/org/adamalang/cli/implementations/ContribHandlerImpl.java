/*
 * This file is subject to the terms and conditions outlined in the
 * file 'LICENSE' (it's dual licensed) located in the root directory
 * near the README.md which you should also read. For more information
 * about the project which owns this file, see https://www.adama-platform.com/ .
 *
 * (c) 2021 - 2023 by Adama Platform Initiative, LLC
 */
package org.adamalang.cli.implementations;

import org.adamalang.GenerateTables;
import org.adamalang.cli.Util;
import org.adamalang.cli.router.Arguments;
import org.adamalang.cli.router.ContribHandler;
import org.adamalang.cli.runtime.Output;
import org.adamalang.common.DefaultCopyright;
import org.adamalang.common.codec.CodecCodeGen;
import org.adamalang.net.codec.ClientMessage;
import org.adamalang.net.codec.ServerMessage;
import org.adamalang.support.GenerateLanguageTests;
import org.adamalang.support.GenerateTemplateTests;
import org.adamalang.web.service.BundleJavaScript;

import java.io.File;
import java.nio.file.Files;

public class ContribHandlerImpl implements ContribHandler {
  private static void copyrightScan(File root) throws Exception {
    for (File f : root.listFiles()) {
      if (f.isDirectory()) {
        copyrightScan(f);
      } else {
        if (f.getName().endsWith(".java")) {
          String code = Files.readString(f.toPath());
          int start = code.indexOf("/*");
          int end = code.indexOf("*/");
          String newCode = null;
          if (start >= 0 && start <= 5 && end > start) {
            newCode = DefaultCopyright.COPYRIGHT_FILE_PREFIX + code.substring(end + 2).trim() + "\n";
          } else {
            newCode = DefaultCopyright.COPYRIGHT_FILE_PREFIX + code.trim() + "\n";
          }
          if (!code.equals(newCode)) {
            Files.writeString(f.toPath(), newCode);
          }
        }
      }
    }
  }

  @Override
  public void bundleJs(Arguments.ContribBundleJsArgs args, Output.YesOrError output) throws Exception {
    System.out.println(Util.prefix("Bundling JavaScript", Util.ANSI.Cyan));
    Files.writeString(new File("web/src/main/java/org/adamalang/web/service/JavaScriptClient.java").toPath(), BundleJavaScript.bundle("./release/libadama.js"));
    output.out();
  }

  @Override
  public void copyright(Arguments.ContribCopyrightArgs args, Output.YesOrError output) throws Exception {
    System.out.println(Util.prefix("Ensuring all files have copyright notice", Util.ANSI.Cyan));
    copyrightScan(new File("."));
    output.out();
  }

  @Override
  public void makeApi(Arguments.ContribMakeApiArgs args, Output.YesOrError output) throws Exception {
    System.out.println(Util.prefix("Assembling Public API", Util.ANSI.Cyan));
    org.adamalang.apikit.Tool.build("saas/api.xml", new File("."));
    System.out.println(Util.prefix("Assembling Global Control Plane API", Util.ANSI.Cyan));
    org.adamalang.apikit.Tool.build("saas/control.xml", new File("."));
    output.out();
  }

  @Override
  public void makeCli(Arguments.ContribMakeCliArgs args, Output.YesOrError output) throws Exception {
    System.out.println(Util.prefix("Building CLI Router", Util.ANSI.Cyan));
    org.adamalang.clikit.Tool.buildFileSystem("./cli/commands.xml");
    output.out();
  }

  @Override
  public void makeCodec(Arguments.ContribMakeCodecArgs args, Output.YesOrError output) throws Exception {
    System.out.println(Util.prefix("Creating Network Codec between Web to Adama", Util.ANSI.Cyan));
    String client = CodecCodeGen.assembleCodec("org.adamalang.net.codec", "ClientCodec", ClientMessage.class.getDeclaredClasses());
    String server = CodecCodeGen.assembleCodec("org.adamalang.net.codec", "ServerCodec", ServerMessage.class.getDeclaredClasses());
    Files.writeString(new File("./net/src/main/java/org/adamalang/net/codec/ClientCodec.java").toPath(), client);
    Files.writeString(new File("./net/src/main/java/org/adamalang/net/codec/ServerCodec.java").toPath(), server);
    output.out();
  }

  @Override
  public void makeEt(Arguments.ContribMakeEtArgs args, Output.YesOrError output) throws Exception {
    System.out.println(Util.prefix("Making Error Table", Util.ANSI.Cyan));
    Files.writeString(new File("errors/src/main/java/org/adamalang/ErrorTable.java").toPath(), GenerateTables.generate());
    output.out();
  }

  @Override
  public void testsAdama(Arguments.ContribTestsAdamaArgs args, Output.YesOrError output) throws Exception {
    System.out.println(Util.prefix("Generate Adama Tests", Util.ANSI.Cyan));
    GenerateLanguageTests.generate(args.input, args.output, args.errors);
    output.out();
  }

  @Override
  public void testsRxhtml(Arguments.ContribTestsRxhtmlArgs args, Output.YesOrError output) throws Exception {
    System.out.println(Util.prefix("Generating RxHTML Tests", Util.ANSI.Cyan));
    GenerateTemplateTests.generate(args.input, args.output);
    output.out();
  }
}