package io.github.asherbearce.graphy.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.github.asherbearce.graphy.model.CalculatorGraphJoin;
import io.github.asherbearce.graphy.model.CalculatorInput;
import java.util.List;

@Dao
public interface CalculatorGraphJoinDao {
  @Insert
  void insert(CalculatorGraphJoin calculatorGraphJoinDao);

  @Query("SELECT * "
      + "FROM functions "
      + "INNER JOIN calculatorgraphjoin ON "
      + "functions.function_id = calculatorgraphjoin.functionId WHERE "
      + "calculatorgraphjoin.graphId = :graphId")
  LiveData<List<CalculatorInput>> getInputsFromGraph(long graphId);
}
