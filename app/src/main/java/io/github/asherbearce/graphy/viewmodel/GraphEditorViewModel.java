package io.github.asherbearce.graphy.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.github.asherbearce.graphy.database.GraphsDatabase;
import io.github.asherbearce.graphy.model.CalculatorInput;
import io.github.asherbearce.graphy.model.Graph;
import java.util.List;

/**
 * The viewmodel responsible for loading calculator inputs
 */
public class GraphEditorViewModel extends AndroidViewModel {
  //This viewModel should only be concerned with inputs to the calculator
  private LiveData<List<CalculatorInput>> functions;
  private GraphsDatabase db;

  /**
   * Construct a new GraphEditorViewModel
   * @param application The current application
   */
  public GraphEditorViewModel(@NonNull Application application){
    super(application);
    db = GraphsDatabase.getInstance(application);
    getGraphInputs(0);
  }

  /**
   * Adds a new graph to the database
   * @param graph The graph to be added
   */
  public void addGraph(final Graph graph){
    new Thread(() -> {
      GraphsDatabase db = GraphsDatabase.getInstance(getApplication());
      db.graphDao().addGraph(graph);
    }).start();
  }

  /**
   * Retrieves the functions currently loaded by the database queries
   * @return
   */
  public LiveData<List<CalculatorInput>> getFunctions(){
    return functions;
  }

  /**
   * Gets all the {@link CalculatorInput} associated with a graph id
   * @param graphId The graph id to be queried for
   * @return {@link LiveData}
   */
  public LiveData<List<CalculatorInput>> getGraphInputs(final long graphId){
    functions = db.calculatorGraphJoinDao().getInputsFromGraph(graphId);
    return functions;
  }
}
