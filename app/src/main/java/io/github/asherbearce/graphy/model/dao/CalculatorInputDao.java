package io.github.asherbearce.graphy.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
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
  @Insert
  Long addCalculatorInput(CalculatorInput input);

  /**
   * Deletes entries from the database
   * @param functions The inputs to be deleted
   * @return int
   */
  @Delete
  int delete(CalculatorInput... functions);
}
