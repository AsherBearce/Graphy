package io.github.asherbearce.graphy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import io.github.asherbearce.graphy.parser.parsing.ComputeEnvironment;

//@Entity
public class CalculatorInput {
  //@ColumnInfo(name = "source")
  private String input;

  //@PrimaryKey(autoGenerate = true)
  private String identifier;

  //@ColumnInfo(name = "graph_id")
  //@ForeignKey(entity = Graph.class, parentColumns = "id", childColumns = "graphId")
  private Long graphId;

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
}
