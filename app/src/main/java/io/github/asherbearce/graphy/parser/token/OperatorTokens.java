package io.github.asherbearce.graphy.parser.token;

import io.github.asherbearce.graphy.parser.math.NumberValue;

public enum OperatorTokens implements Token, Operator {
  PLUS((NumberValue lhs, NumberValue rhs)-> lhs.add(rhs),
      (NumberValue lhs, NumberValue rhs)-> rhs, 1, 0, true),

  MINUS((NumberValue lhs, NumberValue rhs)-> lhs.sub(rhs),
      (NumberValue lhs, NumberValue rhs)-> rhs.negative(), 1, 2, true),

  MULTIPLY((NumberValue lhs, NumberValue rhs)-> lhs.mul(rhs),
      (NumberValue lhs, NumberValue rhs)-> lhs.conjugate(), 2, 2, true),

  DIVIDE((NumberValue lhs, NumberValue rhs)-> lhs.div(rhs),
      (NumberValue lhs, NumberValue rhs)-> lhs, 2, 0, true),

  EXPONENT((NumberValue lhs, NumberValue rhs)-> lhs.pow(rhs),
      (NumberValue lhs, NumberValue rhs)-> rhs, 3, 0, false);

  private final Operation unaryOperation;//Just in case this token is detected to be a unary operation
  private final Operation binaryOperation;
  private final int precedence;
  private final int unaryPrecedence;
  private final boolean leftAssociative;
  private boolean binary;

  OperatorTokens(Operation binaryOperation, Operation unaryOperation, int precedence,
      int unaryPrecedence, boolean leftAssociative){
    this.binaryOperation = binaryOperation;
    this.unaryOperation = unaryOperation;
    this.precedence = precedence;
    this.unaryPrecedence = unaryPrecedence;
    this.leftAssociative = leftAssociative;
  }

  @Override
  public NumberValue computeBinaryOperation(NumberValue lhs, NumberValue rhs) {
    return binaryOperation.compute(lhs, rhs);//TODO This needs to take in to account unary operations
  }

  @Override
  public NumberValue computeUnaryOperation(NumberValue rhs){
    return unaryOperation.compute(rhs, rhs);
  }

  @Override
  public boolean isLeftAssociative() {
    return leftAssociative;
  }

  @Override
  public int getPrecedence() {
    return binary ? precedence : unaryPrecedence;
  }

  @Override
  public TokenTypes getTokenType() {
    return TokenTypes.OPERATOR;
  }

  @Override
  public boolean isBinary(){
    return binary;
  }

  private interface Operation{
    NumberValue compute(NumberValue lhs, NumberValue rhs);
  }
}
