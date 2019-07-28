package io.github.asherbearce.graphy.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The entity which models the Graphs table
 */
@Entity(tableName = "graphs")
public class Graph {

  /**
   * The id of this graph
   */
  @PrimaryKey(autoGenerate = true)
  public Long id;

  /**
   * The name of this graph
   */
  public String name;
}
