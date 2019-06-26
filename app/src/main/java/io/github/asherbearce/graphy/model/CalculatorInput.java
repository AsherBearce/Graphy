package io.github.asherbearce.graphy.model;

import io.github.asherbearce.graphy.parser.parsing.ComputeEnvironment;

public class CalculatorInput {
  private String input;
  private String identifier;
  private ComputeEnvironment environment;

  public CalculatorInput(ComputeEnvironment environment){
    this.environment = environment;
  }

  public String getInputString(){
    return input;
  }

  public void setInputString(String input){
    this.input = input;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public ComputeEnvironment getEnvironment() {
    return environment;
  }
}
