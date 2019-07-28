package io.github.asherbearce.graphy.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import io.github.asherbearce.graphy.model.CalculatorInput;
import io.github.asherbearce.graphy.model.*;
import io.github.asherbearce.graphy.model.dao.CalculatorInputDao;
import io.github.asherbearce.graphy.model.dao.GraphDao;

/**
 * A singleton class which uses Room to execute database queries
 */
@Database(entities = {Graph.class, CalculatorInput.class}, version = 1)
public abstract class GraphsDatabase extends RoomDatabase {
  private static GraphsDatabase INSTANCE;

  /**
   * The Dao corresponding to calculator inputs.
   * @return {@link CalculatorInputDao}
   */
  public abstract CalculatorInputDao inputDao();

  /**
   * The Dao corresponding to the graph inputs.
   * @return {@link GraphDao}
   */
  public abstract GraphDao graphDao();

  /**
   * Gets the one instance of a database service
   * @param context The current application context
   * @return {@link GraphsDatabase}
   */
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
      graph.id = 1L;
      graph.name = "Sample";
      db.graphDao().addGraph(graph);

      CalculatorInput input = new CalculatorInput();
      input.setGraphId(1L);
      input.setInput("f(x) = sin(x)");
      db.inputDao().addCalculatorInput(input);

      input = new CalculatorInput();
      input.setGraphId(1L);
      input.setInput("g(x) = cos(x)");
      db.inputDao().addCalculatorInput(input);

      input = new CalculatorInput();
      input.setGraphId(1L);
      input.setInput("h(x) = f(x) + g(x)");
      db.inputDao().addCalculatorInput(input);

      return null;
    }
  }
}
