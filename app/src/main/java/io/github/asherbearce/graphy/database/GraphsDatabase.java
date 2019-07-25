package io.github.asherbearce.graphy.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import io.github.asherbearce.graphy.model.CalculatorInput;
import io.github.asherbearce.graphy.model.*;
import io.github.asherbearce.graphy.model.dao.CalculatorGraphJoinDao;
import io.github.asherbearce.graphy.model.dao.CalculatorInputDao;
import io.github.asherbearce.graphy.model.dao.GraphDao;

@Database(entities = {Graph.class, CalculatorInput.class, CalculatorGraphJoin.class}, version = 1)
public abstract class GraphsDatabase extends RoomDatabase {
  private static GraphsDatabase INSTANCE;

  public abstract CalculatorInputDao inputDao();

  public abstract GraphDao graphDao();

  public abstract CalculatorGraphJoinDao calculatorGraphJoinDao();

  public static GraphsDatabase getInstance(Context context){
    if (INSTANCE == null){
      synchronized (GraphsDatabase.class){
        if (INSTANCE == null){
          INSTANCE =
              Room.databaseBuilder(context.getApplicationContext(), GraphsDatabase.class, "graphs")
                  .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                      super.onCreate(db);
                      new PopulateDbTask(INSTANCE).execute();

                    }
                  })
                  .build();
        }
      }
    }

    return INSTANCE;
  }

  private static class PopulateDbTask extends AsyncTask<Void, Void, Void>{
    private final GraphsDatabase db;

    PopulateDbTask(GraphsDatabase db){
      this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids){
      Graph graph = new Graph();
      graph.id = 0L;
      graph.name = "Sample";
      db.graphDao().addGraph(graph);

      CalculatorInput input = new CalculatorInput();
      input.setFunctionId(1L);
      input.setGraphId(0L);
      input.setInput("f(x) = sin(x)");
      db.inputDao().addCalculatorInput(input);

      input = new CalculatorInput();
      input.setFunctionId(2L);
      input.setGraphId(0L);
      input.setInput("g(x) = cos(x)");
      db.inputDao().addCalculatorInput(input);

      input = new CalculatorInput();
      input.setFunctionId(3L);
      input.setGraphId(0L);
      input.setInput("h(x) = f(x) + g(x)");
      db.inputDao().addCalculatorInput(input);

      return null;
    }
  }
}
