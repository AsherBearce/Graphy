package io.github.asherbearce.graphy.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.github.asherbearce.graphy.database.GraphsDatabase;
import io.github.asherbearce.graphy.model.CalculatorInput;
import java.util.List;

public class CalculatorInputViewModel extends AndroidViewModel {
  private LiveData<List<CalculatorInput>> functions;

  public CalculatorInputViewModel(@NonNull Application application) {
    super(application);
  }

  public LiveData<List<CalculatorInput>> getFunctions(final long graphId){
    functions = GraphsDatabase.getInstance(getApplication()).calculatorGraphJoinDao()
        .getInputsFromGraph(graphId);
    return functions;
  }
}
