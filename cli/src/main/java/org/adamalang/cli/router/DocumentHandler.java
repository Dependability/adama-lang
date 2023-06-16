/*
 * This file is subject to the terms and conditions outlined in the
 * file 'LICENSE' (hint: it's MIT-based) located in the root directory
 * near the README.md which you should also read. For more information
 * about the project which owns this file, see https://www.adama-platform.com/ .
 *
 * (c) 2020 - 2023 by Jeffrey M. Barber ( http://jeffrey.io )
 */
package org.adamalang.cli.router;

import org.adamalang.cli.router.Arguments.*;
import org.adamalang.cli.runtime.Output.*;

public interface DocumentHandler {
  void connect(DocumentConnectArgs args, YesOrError output) throws Exception;
  void create(DocumentCreateArgs args, YesOrError output) throws Exception;
  void delete(DocumentDeleteArgs args, YesOrError output) throws Exception;
  void list(DocumentListArgs args, JsonOrError output) throws Exception;
  void attach(DocumentAttachArgs args, YesOrError output) throws Exception;
}