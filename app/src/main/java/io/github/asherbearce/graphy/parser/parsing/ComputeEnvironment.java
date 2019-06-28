package io.github.asherbearce.graphy.parser.parsing;

import java.util.HashMap;

public class ComputeEnvironment{
  private TokenList.TokenContainer currentToken;
  private TokenList tokens;
  private HashMap<String, Function> functions;
  public static HashMap<String, BuiltInMethods> builtIn;

  public ComputeEnvironment(){
    functions = new HashMap<>();
  }

  static{
    builtIn = new HashMap<>();
    builtIn.put("ln", BuiltInMethods.LOG);
    builtIn.put("log10", BuiltInMethods.LOG_10);
    builtIn.put("cos", BuiltInMethods.COS);
    builtIn.put("sin", BuiltInMethods.SIN);
    builtIn.put("tan", BuiltInMethods.TAN);
  }

  public Function getFunction(String identifier){
    Function result;

    if (functions.containsKey(identifier)){
      result = functions.get(identifier);
    }
    else{
      result = null;
    }

    return result;
  }

  public void putFunction(Function newFunction){
    String identifier = newFunction.getIdentifier();
    if (functions.containsKey(identifier)){
      functions.remove(identifier);
    }
    newFunction.setEnv(this);
    functions.put(identifier, newFunction);
  }

  public void clearContent(){
    functions.clear();
  }
}
