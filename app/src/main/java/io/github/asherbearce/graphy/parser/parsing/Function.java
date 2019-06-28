package io.github.asherbearce.graphy.parser.parsing;

import io.github.asherbearce.graphy.parser.exception.ParseException;
import io.github.asherbearce.graphy.parser.exception.UnkownIdentifierException;
import io.github.asherbearce.graphy.parser.math.NumberValue;
import io.github.asherbearce.graphy.parser.token.IdentifierToken;
import io.github.asherbearce.graphy.parser.token.NumberToken;
import io.github.asherbearce.graphy.parser.token.OperatorTokens;
import io.github.asherbearce.graphy.parser.token.Token;
import io.github.asherbearce.graphy.parser.token.TokenTypes;
import java.util.HashMap;
import java.util.LinkedList;

public class Function extends TokenHandler implements Callable {
  private String identifier;
  private int numArgs;
  private HashMap<String, Integer> parameters;
  private NumberValue[] assignedParams;
  private ComputeEnvironment env;

  public void setEnv(ComputeEnvironment env) {
    this.env = env;
  }

  public Function(
      String identifier, LinkedList<Token> tokens, int numArgs, HashMap<String, Integer> params){
    super(tokens);
    this.identifier = identifier;
    this.tokens = new TokenList(tokens);
    this.numArgs = numArgs;
    this.parameters = params;
    assignedParams = new NumberValue[numArgs];
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

  private NumberValue[] getArgs(Callable func) throws ParseException {
    int numArgs = func.getNumArgs();
    NumberValue[] args = new NumberValue[numArgs];
    int argNum = 0;

    while (getCurrent().getTokenType() == TokenTypes.NUMBER ||
        getCurrent().getTokenType() == TokenTypes.IDENTIFIER){
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

    if (getCurrent().getTokenType() == TokenTypes.NUMBER){
      result = ((NumberToken)getCurrent()).getValue();

      nextToken();
    }
    else if (getCurrent().getTokenType() == TokenTypes.OPEN_PAREN){
      nextToken();
      result = computeExpression(0);
      expectToken(TokenTypes.CLOSE_PAREN);
      nextToken();
    }
    else if (getCurrent().getTokenType() == TokenTypes.OPERATOR){
      OperatorTokens operator = (OperatorTokens)getCurrent();
      nextToken();
      result = operator.computeUnaryOperation(computeExpression(0));
    }
    else{
      //This token is an identifier, which may be followed by an opening paren, an expression, and a closing paren
      String identifierName = ((IdentifierToken)getCurrent()).getValue();
      if (nextToken().getTokenType() == TokenTypes.OPEN_PAREN){
        //Calling a function within an expression.
        if (env != null && env.getFunction(identifierName) != null){
          //Get all the arguments for this guy.
          Function func = env.getFunction(identifierName);
          NumberValue[] args = getArgs(func);

          result = env.getFunction(identifierName).invoke(args);
        }
        else if (ComputeEnvironment.builtIn.containsKey(identifierName)){
          //Check if the function is part of the built in functions
          nextToken();

          BuiltInMethods func = ComputeEnvironment.builtIn.get(identifierName);
          NumberValue[] args = getArgs(func);

          result = ComputeEnvironment.builtIn.get(identifierName).invoke(args);

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
        else if (env != null && env.getFunction(identifierName) != null){
          result = env.getFunction(identifierName).invoke();
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
    if (getCurrent().getTokenType() == TokenTypes.END){
      throw new ParseException("Unexpected end of expression.");
    }

    NumberValue result = computeAtom();

    while (getCurrent().getTokenType() == TokenTypes.OPERATOR &&
        ((OperatorTokens)getCurrent()).getPrecedence() >= minPrecedence){
      OperatorTokens operator = (OperatorTokens)getCurrent();
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
      reset();
      return result;
    }
  }
}
