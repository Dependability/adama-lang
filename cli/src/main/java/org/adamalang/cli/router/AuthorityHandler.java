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

public interface AuthorityHandler {
  void create(AuthorityCreateArgs args, YesOrError output) throws Exception;
  void set(AuthoritySetArgs args, YesOrError output) throws Exception;
  void get(AuthorityGetArgs args, YesOrError output) throws Exception;
  void destroy(AuthorityDestroyArgs args, YesOrError output) throws Exception;
  void list(AuthorityListArgs args, JsonOrError output) throws Exception;
  void createLocal(AuthorityCreateLocalArgs args, YesOrError output) throws Exception;
  void appendLocal(AuthorityAppendLocalArgs args, YesOrError output) throws Exception;
  void sign(AuthoritySignArgs args, YesOrError output) throws Exception;
}