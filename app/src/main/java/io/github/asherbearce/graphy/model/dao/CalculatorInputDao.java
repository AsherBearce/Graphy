package io.github.asherbearce.graphy.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.github.asherbearce.graphy.model.CalculatorInput;
import java.util.List;

/**
 * The Data Access Object corresponding to the CalculatorInput table
 */
@Dao
public interface CalculatorInputDao {

  /**
   * Queries the database to get all {@link CalculatorInput} objects.
   * @return
   */
  @Query(
      "SELECT *"
      + "FROM functions")
  LiveData<List<CalculatorInput>> getAll();

  /**
   * Adds a new {@link CalculatorInput} to the database
   * @param input The input to be added to the database
   * @return {@link Long}
   */
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  Long addCalculatorInput(CalculatorInput input);

  /**
   * Deletes entries from the database
   * @param functions The inputs to be deleted
   * @return int
   */
  @Delete
  int delete(CalculatorInput... functions);

  /**
   * Returns all the functions associated with a graph id
   * @param graphId The id of the graph
   * @return LiveData
   */
  @Query("SELECT * FROM functions WHERE graph_id = :graphId")
  LiveData<List<CalculatorInput>> getFromGraphId(long graphId);

  /**
   * Deletes all functions associated with a graphid
   * @param graphId The id of the graph
   */
  @Query("DELETE FROM functions WHERE graph_id = :graphId")
  void deleteInputsOf(long graphId);
}
