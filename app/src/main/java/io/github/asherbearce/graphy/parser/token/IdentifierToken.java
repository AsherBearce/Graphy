package io.github.asherbearce.graphy.parser.token;

public class IdentifierToken implements Token {

  private final String value;

  public IdentifierToken(String value){
    this.value = value;
  }

  public String getValue(){
    return value;
  }

  @Override
  public TokenTypes getTokenType() {
    return TokenTypes.IDENTIFIER;
  }
}
