/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE'
 * which is in the root directory of the repository. This file is part of the 'Adama'
 * project which is a programming language and document store for board games.
 * 
 * See http://www.adama-lang.org/ for more information.
 * 
 * (c) 2020 - 2021 by Jeffrey M. Barber (http://jeffrey.io)
*/
package org.adamalang.translator.tree.types;

import org.adamalang.translator.env.Environment;
import org.adamalang.translator.tree.types.traits.CanBeNativeArray;
import org.adamalang.translator.tree.types.traits.details.DetailInventDefaultValueExpression;

public abstract class TySimpleNative extends TyType implements //
    CanBeNativeArray, //
    DetailInventDefaultValueExpression //
{
  private final String javaBoxType;
  private final String javaConcreteType;

  public TySimpleNative(final TypeBehavior behavior, final String javaConcreteType, final String javaBoxType) {
    super(behavior);
    this.javaConcreteType = javaConcreteType;
    this.javaBoxType = javaBoxType;
  }

  @Override
  public String getJavaBoxType(final Environment environment) {
    return javaBoxType;
  }

  @Override
  public String getJavaConcreteType(final Environment environment) {
    return javaConcreteType;
  }

  @Override
  public void typing(final Environment environment) {
  }
}
