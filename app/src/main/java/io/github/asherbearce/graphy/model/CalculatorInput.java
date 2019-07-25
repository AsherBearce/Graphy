package io.github.asherbearce.graphy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * The entity which models the CalculatorInput table
 */
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

  /**
   * Gets the functionId of this entry in the table
   * @return {@link Long}
   */
  public Long getFunctionId() {
    return functionId;
  }

  /**
   * Sets the functionId of this entry in the table
   * @param functionId The new id of this input
   */
  public void setFunctionId(Long functionId) {
    this.functionId = functionId;
  }

  /**
   * Gets the current graph id this input is associated with
   * @return {@link Long}
   */
  public Long getGraphId() {
    return graphId;
  }

  /**
   * Sets the current graph id this input is associated with
   * @param graphId the new graph id
   */
  public void setGraphId(Long graphId) {
    this.graphId = graphId;
  }

  /**
   * Gets the current source string of this input
   * @return {@link String}
   */
  public String getInput(){
    return input;
  }

  /**
   * Sets the source string of this input.
   * @param input The new source string to parse from.
   */
  public void setInput(String input){
    this.input = input;
  }
}
