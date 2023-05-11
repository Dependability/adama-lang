/*
 * This file is subject to the terms and conditions outlined in the
 * file 'LICENSE' (hint: it's MIT-based) located in the root directory
 * near the README.md which you should also read. For more information
 * about the project which owns this file, see https://www.adama-platform.com/ .
 *
 * (c) 2020 - 2023 by Jeffrey M. Barber ( http://jeffrey.io )
 */
package org.adamalang.common.template.tree;

import com.fasterxml.jackson.databind.JsonNode;
import org.adamalang.common.template.Settings;

/** constant text to append to document */
public class TText implements T {
  public final String text;

  public TText(String text) {
    this.text = text;
  }

  @Override
  public void render(Settings settings, JsonNode node, StringBuilder output) {
    output.append(text);
  }
}
