package io.github.asherbearce.graphy.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.github.asherbearce.graphy.model.CalculatorInput;
import java.util.List;

@Dao
public interface CalculatorInputDao {

  @Query(
      "SELECT *"
      + "FROM functions")
  LiveData<List<CalculatorInput>> getAll();

  @Insert
  Long addCalculatorInput(CalculatorInput input);
}
