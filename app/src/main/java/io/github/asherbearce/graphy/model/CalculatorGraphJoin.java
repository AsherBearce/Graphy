package io.github.asherbearce.graphy.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

/**
 * The entity which models the CalculatorGraphJoin table
 */
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

  /**
   * Gets the current graph id associated with this entry
   * @return long
   */
  public long getGraphId() {
    return graphId;
  }

  /**
   * Sets the current graph id to be associated with this entry
   * @param graphId The new graph id
   */
  public void setGraphId(long graphId) {
    this.graphId = graphId;
  }

  /**
   * Gets the current CalculatorInput id associated with this entry
   * @return
   */
  public long getFunctionId() {
    return functionId;
  }

  /**
   * Sets the current CalculatorInput id associated with this entry
   * @param functionId
   */
  public void setFunctionId(long functionId) {
    this.functionId = functionId;
  }

  /**
   * Gets the string source to be parsed by {@link io.github.asherbearce.graphy.controller.GraphEditorFragment}
   * @return {@link String}
   */
  public String getSrc() {
    return src;
  }

  /**
   * Sets the string source to be parsed by {@link io.github.asherbearce.graphy.controller.GraphEditorFragment}
   * @param src The new source sting
   */
  public void setSrc(String src) {
    this.src = src;
  }
}
