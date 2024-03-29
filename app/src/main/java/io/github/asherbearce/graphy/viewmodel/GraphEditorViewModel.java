package io.github.asherbearce.graphy.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
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
  private LiveData<Graph> graph;
  private MutableLiveData<Long> graphId = new MutableLiveData<>();
  private GraphsDatabase db;

  /**
   * Construct a new GraphEditorViewModel
   * @param application The current application
   */
  public GraphEditorViewModel(@NonNull Application application){
    super(application);
    db = GraphsDatabase.getInstance(application);
    functions = Transformations.switchMap(graphId,
        id -> db.inputDao().getFromGraphId(id));
    graph = Transformations.switchMap(graphId,
        id -> db.graphDao().findById(id));
  }

  public void setGraphId(long graphId) {
    this.graphId.postValue(graphId);
  }

  /**
   * Adds a new graph to the database
   * @param graph The graph to be added
   */
  public void addGraph(final Graph graph){
    new Thread(() -> db.graphDao().addGraph(graph)).start();
  }

  /**
   * Adds a new function to the database
   * @param input The function to be added
   */
  public void addFunction(final CalculatorInput input){
    new Thread(() -> db.inputDao().addCalculatorInput(input)).start();
  }

  /**
   * Adds a graph as well as it's associated functions to the database
   * @param graph The graph to be added
   * @param inputs The functions to be added
   */
  public void addGraphWithFunctions(final Graph graph, final List<CalculatorInput> inputs){
    new Thread(() -> {

      Long id = db.graphDao().addGraph(graph);
      for (CalculatorInput input : inputs){
        input.setGraphId(id);
        db.inputDao().addCalculatorInput(input);
      }
    }).start();
  }

  /**
   * Deletes all functions and graph with a certain graph id from the database
   * @param graph The graph to be deleted
   */
  public void deleteGraph(Graph graph){
    new Thread(() -> {
      Long id = graph.id;
      db.inputDao().deleteInputsOf(id);
      db.graphDao().delete(graph);
    }).start();
  }

  /**
   * Retrieves the functions currently loaded by the database queries
   * @return
   */
  public LiveData<List<CalculatorInput>> getFunctions(){
    return functions;
  }

  public LiveData<Graph> getGraph(){
    return graph;
  }
}
