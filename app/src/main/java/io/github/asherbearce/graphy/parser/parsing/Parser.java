package io.github.asherbearce.graphy.parser.parsing;

import io.github.asherbearce.graphy.parser.exception.ParseException;
import io.github.asherbearce.graphy.parser.token.IdentifierToken;
import io.github.asherbearce.graphy.parser.token.Token;
import io.github.asherbearce.graphy.parser.token.TokenTypes;
import java.util.HashMap;
import java.util.LinkedList;

public class Parser extends TokenHandler {

  public Parser(LinkedList<Token> tokens) {
    super(tokens);
  }

  public Function parseStatement() throws ParseException {
    if (getCurrent().getTokenType() == TokenTypes.IDENTIFIER){
      //Create a new function. The next token is either going to be an open parenthesis, or an equals sign.
      String identifier = ((IdentifierToken)getCurrent()).getValue();
      nextToken();

      if (getCurrent().getTokenType() == TokenTypes.OPEN_PAREN){
        nextToken();
        HashMap<String, Integer> parameters = new HashMap<>();
        int paramNumber = 0;

        while (getCurrent().getTokenType() == TokenTypes.IDENTIFIER){
          String varName = ((IdentifierToken)getCurrent()).getValue();

          parameters.put(varName, paramNumber);
          nextToken();

          if (getCurrent().getTokenType() == TokenTypes.COMMA){
            nextToken();
          }

          paramNumber++;
        }

        expectToken(TokenTypes.CLOSE_PAREN);
        nextToken();
        expectToken(TokenTypes.EQUALS);
        nextToken();

        LinkedList<Token> toks = new LinkedList<>();

        while (getCurrent().getTokenType() != TokenTypes.END){
          toks.addLast(getCurrent());
          nextToken();
        }
        toks.addLast(TokenTypes.END);

        return new Function(identifier, toks, paramNumber, parameters);
      }
      else if (getCurrent().getTokenType() == TokenTypes.EQUALS){
        nextToken();
        LinkedList<Token> toks = new LinkedList<>();

        while (getCurrent().getTokenType() != TokenTypes.END){
          toks.addLast(getCurrent());
          nextToken();
        }
        toks.addLast(TokenTypes.END);

        return new Function(identifier, toks, 0, new HashMap<>());
      }
      else{
        throw new ParseException("I don't understand this.");
      }

    } else{

      return new Function(this.toString(), tokens.tokenLinkedList(), 0, new HashMap<>());
    }
  }
}
