package io.github.asherbearce.graphy.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "calculatorgraphjoin",
    primaryKeys = {"graphId", "functionId"},
    indices = {@Index("graphId"), @Index("functionId")},
    foreignKeys = {
      @ForeignKey(entity = Graph.class,
          parentColumns = "id",
          childColumns = "graphId"),
        @ForeignKey(entity = CalculatorInput.class,
          parentColumns = "function_id",
          childColumns = "functionId")
    })
public class CalculatorGraphJoin {
  private long graphId;
  private long functionId;
  private String src;

  public long getGraphId() {
    return graphId;
  }

  public void setGraphId(long graphId) {
    this.graphId = graphId;
  }

  public long getFunctionId() {
    return functionId;
  }

  public void setFunctionId(long functionId) {
    this.functionId = functionId;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }
}
