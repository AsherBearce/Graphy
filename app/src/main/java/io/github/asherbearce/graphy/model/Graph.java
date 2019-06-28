package io.github.asherbearce.graphy.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "graphs")
public class Graph {
  @PrimaryKey
  public Long id;

}
