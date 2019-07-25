package io.github.asherbearce.graphy.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.github.asherbearce.graphy.model.Graph;
import java.util.List;

@Dao
public interface GraphDao {
  @Query(
      "SELECT *"
      + "FROM graphs")
  LiveData<List<Graph>> getAll();

  @Query("SELECT * FROM graphs WHERE id = :id")
  LiveData<Graph> findById(Long id);

  @Insert
  Long addGraph(Graph graph);

  @Delete
  int delete(Graph... graphs);
}
