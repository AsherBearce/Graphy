package io.github.asherbearce.graphy.parser.parsing;

import io.github.asherbearce.graphy.parser.exception.ParseException;
import io.github.asherbearce.graphy.parser.exception.UnexpectedTokenException;
import io.github.asherbearce.graphy.parser.math.NumberValue;
import io.github.asherbearce.graphy.parser.token.IdentifierToken;
import io.github.asherbearce.graphy.parser.token.Token;
import io.github.asherbearce.graphy.parser.token.TokenTypes;
import java.util.HashMap;
import java.util.LinkedList;

public class ComputeEnvironment {
  private TokenList.TokenContainer currentToken;
  private TokenList tokens;
  private HashMap<String, Method> functions;
  private HashMap<String, NumberValue> variables;
  public static HashMap<String, BuiltInMethods> builtIn;

  public ComputeEnvironment(){
    //this.tokens = new TokenList(tokens);
    //currentToken = this.tokens.getFirst();
    functions = new HashMap<>();
    variables = new HashMap<>();

    if (builtIn == null){
      builtIn = new HashMap<>();
      builtIn.put("log", BuiltInMethods.LOG);
      builtIn.put("log10", BuiltInMethods.LOG_10);
      builtIn.put("cos", BuiltInMethods.COS);
      builtIn.put("sin", BuiltInMethods.SIN);
      builtIn.put("tan", BuiltInMethods.TAN);
    }
  }

  private Token nextToken(){
    currentToken = currentToken.next;
    return currentToken.value;
  }

  private void expectToken(Token expected) throws UnexpectedTokenException {
    if (expected.getTokenType() !=  currentToken.value.getTokenType()){
      throw new UnexpectedTokenException("Expected " + expected.getTokenType() + ", got "
          + currentToken.value.getTokenType());
    }
  }

  public NumberValue<?> getVariable(String identifier){
    NumberValue<?> result;

    if (variables.containsKey(identifier)){
      result = variables.get(identifier);
    }
    else{
      result = null;
    }

    return result;
  }

  public Method getFunction(String identifier){
    Method result;

    if (functions.containsKey(identifier)){
      result = functions.get(identifier);
    }
    else{
      result = null;
    }

    return result;
  }

  public void clearContent(){
    functions.clear();
    variables.clear();
  }

  public void parseStatement(LinkedList<Token> tokenList) throws ParseException {
    tokens = new TokenList(tokenList);
    currentToken = tokens.getFirst();

    if (currentToken.value.getTokenType() == TokenTypes.IDENTIFIER){
      String identifierName = ((IdentifierToken) currentToken.value).getValue();

      if (functions.containsKey(identifierName) || variables.containsKey(identifierName)){
        //Do a compute expression, put it into a new anonymous variable.
        NumberValue newVar = Method.computeConstant(tokens.tokenLinkedList(), this);
        String anonymousName = String.format("$%d", variables.size());

        variables.put(anonymousName, newVar);
      }
      else{
        //Other wise, we're declaring either a new variable or function.
        nextToken();
        if (currentToken.value.getTokenType() == TokenTypes.OPEN_PAREN){
          //Define a new function
          nextToken();
          HashMap<String, Integer> parameters = new HashMap<>();
          int paramNumber = 0;

          while (currentToken.value.getTokenType() == TokenTypes.IDENTIFIER){
            String varName = ((IdentifierToken) currentToken.value).getValue();

            parameters.put(varName, paramNumber);
            nextToken();

            if (currentToken.value.getTokenType() == TokenTypes.COMMA){
              nextToken();
            }

            paramNumber++;
          }

          expectToken(TokenTypes.CLOSE_PAREN);
          nextToken();
          expectToken(TokenTypes.EQUALS);
          nextToken();
          //make a new token list starting from currentToken to the end of the list of tokens.
          LinkedList<Token> toks = new LinkedList<>();

          while (currentToken.value.getTokenType() != TokenTypes.END){
            toks.addLast(currentToken.value);
            nextToken();
          }
          toks.addLast(TokenTypes.END);

          Method func = new Method(identifierName, toks, paramNumber, this, parameters);
          functions.put(identifierName, func);
        }
        else{
          //Define a new variable
          expectToken(TokenTypes.EQUALS);
          nextToken();

          LinkedList<Token> toks = new LinkedList<>();

          while (currentToken.value != TokenTypes.END){
            toks.addLast(currentToken.value);
            nextToken();
          }

          toks.addLast(TokenTypes.END);

          NumberValue newVar = Method.computeConstant(toks, this);
          String anonymousName = String.format(identifierName, variables.size());

          variables.put(anonymousName, newVar);
        }
      }

    }
    else{
      //Do the same thing as we did for declaring a new variable but give it an anonymous name such as $a

      NumberValue newVar = Method.computeConstant(tokens.tokenLinkedList(), this);
      String anonymousName = String.format("$%d", variables.size());

      variables.put(anonymousName, newVar);
    }

    tokens = null;
    currentToken = null;
  }
}
