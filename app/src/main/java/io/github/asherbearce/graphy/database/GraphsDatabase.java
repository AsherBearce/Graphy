package io.github.asherbearce.graphy.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import io.github.asherbearce.graphy.model.CalculatorInput;
import io.github.asherbearce.graphy.model.*;

@Database(entities = {Graph.class}, version = 1)
public abstract class GraphsDatabase extends RoomDatabase {

}
