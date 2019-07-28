package io.github.asherbearce.graphy.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import io.github.asherbearce.graphy.R;
import io.github.asherbearce.graphy.controller.MainActivity;
import io.github.asherbearce.graphy.model.Graph;
import io.github.asherbearce.graphy.viewmodel.GraphEditorViewModel;
import java.util.List;

/**
 * Handles displaying all data involving graphs
 */
public class GraphAdapter extends ArrayAdapter<Graph> {
  private List<Graph> contents;
  private GraphEditorViewModel viewModel;
  private MainActivity controller;

  /**
   * Constructs a new GraphAdapter given a context, and a list of graphs to display
   * @param context The current application context
   * @param contents The contents of this list.
   */
  public GraphAdapter(@NonNull Context context, @NonNull List<Graph> contents,
      GraphEditorViewModel viewModel){
    super(context, R.layout.graph_list_item , contents);
    this.contents = contents;
    this.viewModel = viewModel;
  }

  /**
   * Sets the current main activity controller for this app
   * @param controller The main activity controller
   */
  public void setController(MainActivity controller){
    this.controller = controller;
  }

  @NonNull
  @Override
  public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View view = convertView == null ? LayoutInflater.from(getContext()).inflate(
        R.layout.graph_list_item, parent, false) : convertView;

    Graph graph = contents.get(position);

    TextView text = view.findViewById(R.id.graph_text_view);
    ImageButton loadGraphButton = view.findViewById(R.id.select_graph_button);
    text.setText(contents.get(position).name);

    loadGraphButton.setOnClickListener(v -> {
      //viewModel.getGraphInputs(getItem(position).id);
      controller.openEditorFragment(graph.id);//.loadFunctions(graph.id);
    });

    return view;
  }
}
