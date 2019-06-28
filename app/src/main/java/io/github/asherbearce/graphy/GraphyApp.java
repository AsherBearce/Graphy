package io.github.asherbearce.graphy;

import android.app.Application;
import com.facebook.stetho.Stetho;
import io.github.asherbearce.graphy.database.GraphsDatabase;

public class GraphyApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    new Thread(
        ()->{
          GraphsDatabase.getInstance(this).graphDao().deleta();
        }
    ).start();
  }
}
