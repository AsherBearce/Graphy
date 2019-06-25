package io.github.asherbearce.graphy.parser.token;

import io.github.asherbearce.graphy.parser.math.NumberValue;

public class NumberToken implements Token {

  private final NumberValue value;

  public NumberToken(NumberValue value){
    this.value = value;
  }

  public NumberValue getValue(){
    return value;
  }

  @Override
  public TokenTypes getTokenType() {
    return TokenTypes.NUMBER;
  }
}
