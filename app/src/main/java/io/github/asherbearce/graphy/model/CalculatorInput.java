package io.github.asherbearce.graphy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "functions", foreignKeys =
    @ForeignKey(entity = Graph.class,
    parentColumns = "id",
    childColumns = "graph_id"))
public class CalculatorInput {
  @ColumnInfo(name = "source")
  private String input;
  @ColumnInfo(name = "function_id")
  @PrimaryKey
  private Long functionId;

  @ColumnInfo(name = "graph_id")
  private Long graphId;

  public Long getFunctionId() {
    return functionId;
  }

  public void setFunctionId(Long functionId) {
    this.functionId = functionId;
  }

  public Long getGraphId() {
    return graphId;
  }

  public void setGraphId(Long graphId) {
    this.graphId = graphId;
  }

  public String getInput(){
    return input;
  }

  public void setInput(String input){
    this.input = input;
  }
}
