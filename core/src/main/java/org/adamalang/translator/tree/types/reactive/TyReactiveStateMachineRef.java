/*
 * This file is subject to the terms and conditions outlined in the
 * file 'LICENSE' (hint: it's MIT-based) located in the root directory
 * near the README.md which you should also read. For more information
 * about the project which owns this file, see https://www.adama-platform.com/ .
 *
 * (c) 2020 - 2023 by Jeffrey M. Barber ( http://jeffrey.io )
 */
package org.adamalang.translator.tree.types.reactive;

import org.adamalang.runtime.json.JsonStreamWriter;
import org.adamalang.translator.env.Environment;
import org.adamalang.translator.parser.token.Token;
import org.adamalang.translator.tree.common.DocumentPosition;
import org.adamalang.translator.tree.expressions.Expression;
import org.adamalang.translator.tree.expressions.constants.StateMachineConstant;
import org.adamalang.translator.tree.types.ReflectionSource;
import org.adamalang.translator.tree.types.TySimpleReactive;
import org.adamalang.translator.tree.types.TyType;
import org.adamalang.translator.tree.types.TypeBehavior;
import org.adamalang.translator.tree.types.natives.TyNativeStateMachineRef;
import org.adamalang.translator.tree.types.traits.IsOrderable;

/**
 * The type representing a valid reference in the state machine; this uses the reactive 'RxString'
 * java type
 */
public class TyReactiveStateMachineRef extends TySimpleReactive implements //
    IsOrderable //
{
  public TyReactiveStateMachineRef(final Token token) {
    super(token, "RxFastString");
  }

  @Override
  public String getAdamaType() {
    return "r<label>";
  }

  @Override
  public TyType makeCopyWithNewPositionInternal(final DocumentPosition position, final TypeBehavior newBehavior) {
    return new TyReactiveStateMachineRef(token).withPosition(position);
  }

  @Override
  public void writeTypeReflectionJson(JsonStreamWriter writer, ReflectionSource source) {
    writer.beginObject();
    writer.writeObjectFieldIntro("nature");
    writer.writeString("reactive_value");
    writeAnnotations(writer);
    writer.writeObjectFieldIntro("type");
    writer.writeString("label");
    writer.endObject();
  }

  @Override
  public Expression inventDefaultValueExpression(final DocumentPosition forWhatExpression) {
    return new StateMachineConstant(Token.WRAP("#")).withPosition(forWhatExpression);
  }

  @Override
  public TyType typeAfterGet(final Environment environment) {
    return new TyNativeStateMachineRef(TypeBehavior.ReadOnlyNativeValue, null, token);
  }
}
