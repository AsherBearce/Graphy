package io.github.asherbearce.graphy.parser.parsing;

import io.github.asherbearce.graphy.parser.exception.ParseException;
import io.github.asherbearce.graphy.parser.exception.UnexpectedTokenException;
import io.github.asherbearce.graphy.parser.exception.UnkownIdentifierException;
import io.github.asherbearce.graphy.parser.math.NumberValue;
import io.github.asherbearce.graphy.parser.token.IdentifierToken;
import io.github.asherbearce.graphy.parser.token.NumberToken;
import io.github.asherbearce.graphy.parser.token.OperatorTokens;
import io.github.asherbearce.graphy.parser.token.Token;
import io.github.asherbearce.graphy.parser.token.TokenTypes;
import java.util.HashMap;
import java.util.LinkedList;

public class Method implements Callable {
  private String identifier;
  private TokenList tokens;
  private int numArgs;
  private HashMap<String, Integer> parameters;
  private TokenList.TokenContainer currentToken;
  private NumberValue[] assignedParams;
  private final ComputeEnvironment env;

  public Method(String indentifier, LinkedList<Token> tokens, int numArgs, ComputeEnvironment env,
      HashMap<String, Integer> params){
    this.identifier = identifier;
    this.tokens = new TokenList(tokens);
    this.numArgs = numArgs;
    this.env = env;
    this.parameters = params;
    assignedParams = new NumberValue[numArgs];
    currentToken = this.tokens.getFirst();
  }

  public String getIdentifier() {
    return identifier;
  }

  public TokenList getTokens() {
    return tokens;
  }

  public int getNumArgs() {
    return numArgs;
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

  private NumberValue[] getArgs(Callable func) throws ParseException {
    int numArgs = func.getNumArgs();
    NumberValue[] args = new NumberValue[numArgs];
    int argNum = 0;

    while (currentToken.value.getTokenType() == TokenTypes.NUMBER ||
        currentToken.value.getTokenType() == TokenTypes.IDENTIFIER){
      args[argNum] = computeExpression(0);

      if (argNum < numArgs - 1) {
        expectToken(TokenTypes.COMMA);
        nextToken();
      }

      argNum++;
    }

    expectToken(TokenTypes.CLOSE_PAREN);
    nextToken();

    return args;
  }

  private NumberValue computeAtom() throws ParseException{
    NumberValue result;

    if (currentToken.value.getTokenType() == TokenTypes.END){
      throw new ParseException("Empty expression");
    }

    if (currentToken.value.getTokenType() == TokenTypes.NUMBER){
      result = ((NumberToken)currentToken.value).getValue();
      nextToken();
    }
    else if (currentToken.value.getTokenType() == TokenTypes.OPEN_PAREN){
      nextToken();
      result = computeExpression(0);
      expectToken(TokenTypes.CLOSE_PAREN);
      nextToken();
    }
    else if (currentToken.value.getTokenType() == TokenTypes.OPERATOR){
      OperatorTokens operator = (OperatorTokens)currentToken.value;
      nextToken();
      result = operator.computeUnaryOperation(computeExpression(0));
    }
    else{
      //This token is an identifier, which may be followed by an opening paren, an expression, and a closing paren
      String identifierName = ((IdentifierToken) currentToken.value).getValue();
      if (nextToken().getTokenType() == TokenTypes.OPEN_PAREN){
        //Calling a function within an expression.
        if (env != null && env.getFunction(identifierName) != null){
          //Get all the arguments for this guy.
          Method func = env.getFunction(identifierName);
          NumberValue[] args = getArgs(func);

          result = env.getFunction(identifierName).invoke(args);
        }
        else if (ComputeEnvironment.builtIn.containsKey(identifierName)){
          //Check if the function is part of the built in functions
          nextToken();

          BuiltInMethods func = ComputeEnvironment.builtIn.get(identifierName);
          NumberValue[] args = getArgs(func);

          result = env.builtIn.get(identifierName).invoke(args);

        }
        else{
          throw new UnkownIdentifierException("Unknown identifier: " + identifierName);
        }
      }
      else{
        //Referencing a variable
        if (parameters.containsKey(identifierName)){
          result = assignedParams[parameters.get(identifierName)];
        }
        else if (env != null && env.getVariable(identifierName) != null){
          result = env.getVariable(identifierName);
        }
        else{
          //Throw an exception
          throw new UnkownIdentifierException("Unknown identifier: " + identifierName);
        }
      }
    }

    return result;
  }

  //TODO add support for implicit type promotion using reflection.
  private NumberValue computeExpression(int minPrecedence) throws ParseException{
    NumberValue result = computeAtom();

    while (currentToken.value.getTokenType() == TokenTypes.OPERATOR &&
        ((OperatorTokens)currentToken.value).getPrecedence() >= minPrecedence){
      OperatorTokens operator = (OperatorTokens)currentToken.value;
      int prec = operator.getPrecedence();
      boolean isLeftAssociative = operator.isLeftAssociative();
      int nextMinPrec;

      if (isLeftAssociative){
        nextMinPrec = prec + 1;
      }else{
        nextMinPrec = prec;
      }

      nextToken();
      NumberValue rhs = computeExpression(nextMinPrec);
      result = operator.computeBinaryOperation(result, rhs);
    }

    return result;
  }

  public NumberValue invoke(NumberValue... args) throws ParseException{
    if (args.length != assignedParams.length){
      throw new ParseException("Number of parameters don't match function signature");
    }
    else{
      assignedParams = args;
      //TODO do more parsing to add support for piece-wise functions
      NumberValue result = computeExpression(0);
      currentToken = tokens.getFirst();
      return result;
    }
  }

  public static NumberValue computeConstant(LinkedList<Token> constTokens, ComputeEnvironment env) throws ParseException{
    Method tempMethod = new Method("", constTokens, 0, env, new HashMap<>());
    NumberValue result = tempMethod.computeExpression(0);
    tempMethod = null;
    return result;
  }
}
