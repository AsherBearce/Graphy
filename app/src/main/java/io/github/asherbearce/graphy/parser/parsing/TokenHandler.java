package io.github.asherbearce.graphy.parser.parsing;

import io.github.asherbearce.graphy.parser.exception.UnexpectedTokenException;
import io.github.asherbearce.graphy.parser.token.Token;
import java.util.LinkedList;

public abstract class TokenHandler {
  private TokenList.TokenContainer currentToken;
  protected TokenList tokens;

  public TokenHandler(LinkedList<Token> tokens){
    this.tokens = new TokenList(tokens);
    currentToken = this.tokens.getFirst();
  }

  public TokenHandler(TokenList tokens){
    this.tokens = tokens;
    currentToken = this.tokens.getFirst();
  }

  public void reset(){
    currentToken = this.tokens.getFirst();
  }

  protected Token nextToken(){
    currentToken = currentToken.next;
    return currentToken.value;
  }

  protected Token getCurrent(){
    return currentToken.value;
  }

  protected void expectToken(Token expected) throws UnexpectedTokenException {
    if (expected.getTokenType() != currentToken.value.getTokenType()){
      throw new UnexpectedTokenException("Expected " + expected.getTokenType() + ", got "
          + currentToken.value.getTokenType());
    }
  }
}
