/* The Adama Programming Language For Board Games!
 *    See http://www.adama-lang.org/ for more information.
 * (c) copyright 2020 Jeffrey M. Barber (http://jeffrey.io) */
package org.adamalang.runtime.delta;

import org.adamalang.runtime.json.PrivateLazyDeltaWriter;
import org.adamalang.runtime.natives.NtAsset;
import org.adamalang.runtime.natives.NtClient;

/** the asset we sent to the client */
public class DAsset {
  private NtAsset prior;

  public DAsset() {
    prior = null;
  }

  public void hide(final PrivateLazyDeltaWriter writer) {
    if (prior != null) {
      writer.writeNull();
      prior = null;
    }
  }

  public void show(final NtAsset value, final PrivateLazyDeltaWriter writer) {
    if (prior == null) {
      if (value != null) {
        prior = value;
        writeOut(writer);
      }
    } else {
      if (value != null && !value.equals(prior)) {
        prior = value;
        writeOut(writer);
      }
    }
  }

  private void writeOut(final PrivateLazyDeltaWriter writer) {
    final var obj = writer.planObject();
    // note; we don't send the name as that may leak private information from the uploader
    obj.planField("id").writeFastString("" + prior.id);
    obj.planField("size").writeFastString("" + prior.size);
    obj.planField("type").writeString(prior.contentType);
    obj.planField("md5").writeString(prior.md5);
    obj.planField("sha384").writeString(prior.sha384);
  }
}
