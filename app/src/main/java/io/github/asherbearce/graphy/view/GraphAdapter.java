package io.github.asherbearce.graphy.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.github.asherbearce.graphy.R;
import io.github.asherbearce.graphy.model.Graph;
import java.util.List;

/**
 * Handles displaying all data involving graphs
 */
public class GraphAdapter extends ArrayAdapter<Graph> {

  /**
   * Constructs a new GraphAdapter given a context, and a list of graphs to display
   * @param context The current application context
   * @param contents The contents of this list.
   */
  public GraphAdapter(@NonNull Context context, @NonNull List<Graph> contents){
    super(context, R.layout.graph_list_item , contents);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View view = convertView == null ? LayoutInflater.from(getContext()).inflate(
        R.layout.graph_list_item, parent, false) : convertView;

    

    return view;
  }
}
