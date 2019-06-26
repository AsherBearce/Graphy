package io.github.asherbearce.graphy.parser.parsing;

import static io.github.asherbearce.graphy.parser.math.MathUtil.*;
import io.github.asherbearce.graphy.parser.exception.ParseException;
import io.github.asherbearce.graphy.parser.math.NumberValue;

public enum BuiltInMethods implements Callable {
  LOG((NumberValue... args) -> log(args[0]), 1),
  LOG_10((NumberValue... args) -> log10(args[0]), 1),
  COS((NumberValue... args) -> cos(args[0]), 1),
  SIN((NumberValue... args) -> sin(args[0]), 1),
  TAN((NumberValue... args) -> tan(args[0]), 1);

  private Invokable method;
  private int numArgs;

  BuiltInMethods(Invokable method, int numArgs){
    this.method = method;
    this.numArgs = numArgs;
  }

  public NumberValue invoke(NumberValue... args) throws ParseException {
    if (args.length != numArgs){
      throw new ParseException("Number of parameters don't match function signature");
    }
    else{
      return method.invoke(args);
    }
  }

  public int getNumArgs(){
    return numArgs;
  }
}
