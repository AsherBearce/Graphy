package io.github.asherbearce.graphy.parser.token;

public enum TokenTypes implements Token {
  NUMBER,
  IDENTIFIER,
  OPERATOR,
  OPEN_PAREN,
  CLOSE_PAREN,
  OPEN_CURLY,
  CLOSE_CURLY,
  COMMA,
  COLON,
  EQUALS,
  LESS_THAN,
  GREATER_THAN,
  LESS_THAN_EQUAL,
  GREATER_THAN_EQUAL,
  END;

  @Override
  public TokenTypes getTokenType() {
    return this;
  }
}
