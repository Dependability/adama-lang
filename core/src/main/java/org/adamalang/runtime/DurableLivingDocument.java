package org.adamalang.runtime;

import org.adamalang.runtime.contracts.*;
import org.adamalang.runtime.json.JsonStreamReader;
import org.adamalang.runtime.json.JsonStreamWriter;
import org.adamalang.runtime.json.PrivateView;
import org.adamalang.runtime.natives.NtClient;
import org.adamalang.translator.jvm.LivingDocumentFactory;

public class DurableLivingDocument {
  private final static int DURABLE_LIVING_DOCUMENT_STAGE_FRESH_DRIVE = 1000;
  private final static int DURABLE_LIVING_DOCUMENT_STAGE_FRESH_TRANSFORM = 1001;
  private final static int DURABLE_LIVING_DOCUMENT_STAGE_INGEST_DRIVE = 1010;
  private final static int DURABLE_LIVING_DOCUMENT_STAGE_INGEST_PARTIAL = 1011;
  private final static int DURABLE_LIVING_DOCUMENT_STAGE_INGEST_DONE = 1012;
  private final static int DURABLE_LIVING_DOCUMENT_STAGE_LOAD = 1020;
  private final static int DURABLE_LIVING_DOCUMENT_STAGE_PARSE = 1021;

  public final long documentId;
  public final LivingDocument document;
  public final TimeSource time;
  private final DataService service;

  public DurableLivingDocument(final long documentId, final LivingDocument document, final TimeSource time, final DataService service) {
    this.documentId = documentId;
    this.document = document;
    this.time = time;
    this.service = service;
  }

  public static void fresh(
          long documentId,
          final LivingDocumentFactory factory,
          final NtClient who,
          final String arg,
          final String entropy,
          final DocumentMonitor monitor,
          final TimeSource time,
          final DataService service,
          final DataCallback<DurableLivingDocument> callback) {
    try {
      DurableLivingDocument document = new DurableLivingDocument(documentId, factory.create(monitor), time, service);
      document.construct(who, arg, entropy, DataCallback.transform(callback, DURABLE_LIVING_DOCUMENT_STAGE_FRESH_TRANSFORM, (seq) -> document));
    } catch (Exception exception) {
      callback.failure(DURABLE_LIVING_DOCUMENT_STAGE_FRESH_DRIVE, exception);
    }
  }

  public static void load(
          long documentId,
          final LivingDocumentFactory factory,
          final DocumentMonitor monitor,
          final TimeSource time,
          final DataService service,
          final DataCallback<DurableLivingDocument> callback) {
    try {
      LivingDocument doc = factory.create(monitor);
      service.get(documentId, DataCallback.transform(callback, DURABLE_LIVING_DOCUMENT_STAGE_PARSE, (data) -> {
          doc.__insert(new JsonStreamReader(data.patch));
          DurableLivingDocument result = new DurableLivingDocument(documentId, doc, time, service);
          return result;
        }));
    } catch (Exception exception) {
      callback.failure(DURABLE_LIVING_DOCUMENT_STAGE_LOAD, exception);
    }
  }

  public JsonStreamWriter forge(final String command, final NtClient who) {
    final var writer = new JsonStreamWriter();
    writer.beginObject();
    writer.writeObjectFieldIntro("command");
    writer.writeFastString(command);
    writer.writeObjectFieldIntro("timestamp");
    writer.writeLong(time.nowMilliseconds());
    if (who != null) {
      writer.writeObjectFieldIntro("who");
      writer.writeNtClient(who);
    }
    return writer;
  }

  private void ingest(String request, DataCallback<Integer> callback) {
    try {
      final var update = document.__transact(request.toString());
      if (update.requiresFutureInvalidation && update.whenToInvalidateMilliseconds == 0) {
        service.patch(documentId, update, DataCallback.transform(callback, DURABLE_LIVING_DOCUMENT_STAGE_INGEST_PARTIAL, (v) -> {
          invalidate(callback);
          return null;
        }));
      } else {
        service.patch(documentId, update, DataCallback.transform(callback, DURABLE_LIVING_DOCUMENT_STAGE_INGEST_DONE, (v) -> update.seq));
      }
    } catch (Exception ex) {
      callback.failure(DURABLE_LIVING_DOCUMENT_STAGE_INGEST_DRIVE, ex);
    }
  }

  private void construct(final NtClient who, final String arg, final String entropy, DataCallback<Integer> callback) {
    final var writer = forge("construct", who);
    writer.writeObjectFieldIntro("arg");
    writer.injectJson(arg);
    if (entropy != null) {
      writer.writeObjectFieldIntro("entropy");
      writer.writeFastString(entropy);
    }
    writer.endObject();
    ingest(writer.toString(), callback);
  }

  public void invalidate(DataCallback<Integer> callback) {
    final var request = forge("invalidate", null);
    request.endObject();
    ingest(request.toString(), callback);
  }

  public int getCodeCost() {
    return document.__getCodeCost();
  }

  public void bill(DataCallback<Integer> callback) {
    final var request = forge("bill", null);
    request.endObject();
    ingest(request.toString(), callback);
  }

  public void connect(final NtClient who, DataCallback<Integer> callback) {
    final var request = forge("connect", who);
    request.endObject();
    ingest(request.toString(), callback);
  }

  public boolean isConnected(final NtClient who) {
    return document.__isConnected(who);
  }

  public PrivateView createPrivateView(final NtClient who, final Perspective perspective) {
    return document.__createView(who, perspective);
  }

  public int garbageCollectPrivateViewsFor(final NtClient who) {
    return document.__garbageCollectViews(who);
  }

  public void disconnect(final NtClient who, DataCallback<Integer> callback) {
    final var request = forge("disconnect", who);
    request.endObject();
    ingest(request.toString(), callback);
  }

  public void send(final NtClient who, final String channel, final String message, DataCallback<Integer> callback) {
    final var writer = forge("send", who);
    writer.writeObjectFieldIntro("channel");
    writer.writeFastString(channel);
    writer.writeObjectFieldIntro("message");
    writer.injectJson(message);
    writer.endObject();
    ingest(writer.toString(), callback);
  }

  public String json() {
    final var writer = new JsonStreamWriter();
    document.__dump(writer);
    return writer.toString();
  }
}
