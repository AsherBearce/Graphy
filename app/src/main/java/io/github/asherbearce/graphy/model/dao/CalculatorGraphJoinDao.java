package io.github.asherbearce.graphy.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.github.asherbearce.graphy.model.CalculatorGraphJoin;
import io.github.asherbearce.graphy.model.CalculatorInput;
import java.util.List;

/**
 * The Data Access Object corresponding to the CalculatorGraphJoin table
 */
@Dao
public interface CalculatorGraphJoinDao {

  /**
   * Inserts a new entry in the table
   * @param calculatorGraphJoinDao
   */
  @Insert
  void insert(CalculatorGraphJoin calculatorGraphJoinDao);

  /**
   * Queries the database for all {@link CalculatorInput} associated with a certain graph ID
   * @param graphId The ID of the graph
   * @return {@link LiveData}
   */
  @Query("SELECT * "
      + "FROM functions "
      + "INNER JOIN calculatorgraphjoin ON "
      + "functions.function_id = calculatorgraphjoin.functionId WHERE "
      + "calculatorgraphjoin.graphId = :graphId")
  LiveData<List<CalculatorInput>> getInputsFromGraph(long graphId);
}
