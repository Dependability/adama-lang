/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE'
 * which is in the root directory of the repository. This file is part of the 'Adama'
 * project which is a programming language and document store for board games.
 * 
 * See http://www.adama-lang.org/ for more information.
 * 
 * (c) 2020 - 2021 by Jeffrey M. Barber (http://jeffrey.io)
*/
package org.adamalang.translator.tree.expressions.constants;

import java.util.function.Consumer;
import org.adamalang.translator.env.Environment;
import org.adamalang.translator.parser.token.Token;
import org.adamalang.translator.tree.expressions.Expression;
import org.adamalang.translator.tree.types.TyType;
import org.adamalang.translator.tree.types.TypeBehavior;
import org.adamalang.translator.tree.types.natives.TyNativeDouble;

/** double-wide floating point precision numbers (3.14) */
public class DoubleConstant extends Expression {
  public final Token token;
  public final double value;

  public DoubleConstant(final Token token, final double value) {
    this.token = token;
    this.value = value;
    ingest(token);
  }

  @Override
  public void emit(final Consumer<Token> yielder) {
    yielder.accept(token);
  }

  @Override
  protected TyType typingInternal(final Environment environment, final TyType suggestion) {
    environment.mustBeComputeContext(this);
    return new TyNativeDouble(TypeBehavior.ReadOnlyNativeValue, null, token).makeCopyWithNewPosition(this, TypeBehavior.ReadOnlyNativeValue);
  }

  @Override
  public void writeJava(final StringBuilder sb, final Environment environment) {
    sb.append(value);
  }
}
