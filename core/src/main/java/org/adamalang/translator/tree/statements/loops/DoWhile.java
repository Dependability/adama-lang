/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE'
 * which is in the root directory of the repository. This file is part of the 'Adama'
 * project which is a programming language and document store for board games.
 * 
 * See http://www.adama-lang.org/ for more information.
 * 
 * (c) 2020 - 2021 by Jeffrey M. Barber (http://jeffrey.io)
*/
package org.adamalang.translator.tree.statements.loops;

import java.util.function.Consumer;
import org.adamalang.translator.env.ComputeContext;
import org.adamalang.translator.env.Environment;
import org.adamalang.translator.parser.token.Token;
import org.adamalang.translator.tree.common.StringBuilderWithTabs;
import org.adamalang.translator.tree.expressions.Expression;
import org.adamalang.translator.tree.statements.Block;
import org.adamalang.translator.tree.statements.ControlFlow;
import org.adamalang.translator.tree.statements.Statement;

/** classic do {} while (cond); loop */
public class DoWhile extends Statement {
  public final Token closeParen;
  public final Block code;
  public final Expression condition;
  public final Token doToken;
  public final Token endToken;
  public final Token openParen;
  public final Token whileToken;

  public DoWhile(final Token doToken, final Block code, final Token whileToken, final Token openParen, final Expression condition, final Token closeParen, final Token endToken) {
    this.doToken = doToken;
    this.code = code;
    this.whileToken = whileToken;
    this.openParen = openParen;
    this.condition = condition;
    this.closeParen = closeParen;
    this.endToken = endToken;
    ingest(doToken);
    ingest(endToken);
  }

  @Override
  public void emit(final Consumer<Token> yielder) {
    yielder.accept(doToken);
    code.emit(yielder);
    yielder.accept(whileToken);
    yielder.accept(openParen);
    condition.emit(yielder);
    yielder.accept(closeParen);
    yielder.accept(endToken);
  }

  @Override
  public ControlFlow typing(final Environment environment) {
    final var flow = code.typing(environment);
    final var conditionType = condition.typing(environment.scopeWithComputeContext(ComputeContext.Computation), null);
    environment.rules.IsBoolean(conditionType, false);
    return flow;
  }

  @Override
  public void writeJava(final StringBuilderWithTabs sb, final Environment environment) {
    sb.append("do ");
    code.writeJava(sb, environment);
    sb.append(" while (__goodwill(").append(condition.toArgs(true)).append(") && (");
    condition.writeJava(sb, environment.scopeWithComputeContext(ComputeContext.Computation));
    sb.append("));");
  }
}
