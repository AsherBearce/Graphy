package io.github.asherbearce.graphy.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.github.asherbearce.graphy.database.GraphsDatabase;
import io.github.asherbearce.graphy.model.CalculatorInput;
import io.github.asherbearce.graphy.model.Graph;
import java.util.List;

public class GraphEditorViewModel extends AndroidViewModel {
  //This viewModel should only be concerned with inputs to the calculator
  private LiveData<List<CalculatorInput>> functions;
  private GraphsDatabase db;

  public GraphEditorViewModel(@NonNull Application application){
    super(application);
    db = GraphsDatabase.getInstance(application);
    getGraphInputs(0);
  }

  public void addGraph(final Graph graph){
    new Thread(() -> {
      GraphsDatabase db = GraphsDatabase.getInstance(getApplication());
      db.graphDao().addGraph(graph);
    }).start();
  }

  public LiveData<List<CalculatorInput>> getFunctions(){
    return functions;
  }

  public LiveData<List<CalculatorInput>> getGraphInputs(final long graphId){
    functions = db.calculatorGraphJoinDao().getInputsFromGraph(graphId);
    return functions;
  }
}
