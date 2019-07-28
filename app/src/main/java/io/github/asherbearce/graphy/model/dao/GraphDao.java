package io.github.asherbearce.graphy.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.github.asherbearce.graphy.model.Graph;
import java.util.List;

/**
 * The Data Access Object corresponding to the Graph table
 */
@Dao
public interface GraphDao {

  /**
   * Queries the database to retrieve all graphs
   * @return {@link LiveData}
   */
  @Query(
      "SELECT *"
      + "FROM graphs")
  LiveData<List<Graph>> getAll();

  /**
   * Queries the database to retrieve a single graph from the output
   * @param id The id to query
   * @return {@link Graph}
   */
  @Query("SELECT * FROM graphs WHERE id = :id")
  LiveData<Graph> findById(Long id);

  /**
   * Inserts a new graph in the database
   * @param graph The graph to be added to the database
   * @return {@link Long}
   */
  @Insert
  Long addGraph(Graph graph);

  /**
   * Deletes entries from the graph database.
   * @param graphs The graphs to remove from the database
   * @return int
   */
  @Delete
  int delete(Graph... graphs);

  @Query("UPDATE graphs "
      + "SET name = :newName "
      + "WHERE id = :id")
  void updateGraphName(String newName, long id);
}
