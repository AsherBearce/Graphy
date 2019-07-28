package io.github.asherbearce.graphy.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import io.github.asherbearce.graphy.R;
import io.github.asherbearce.graphy.model.CalculatorInput;
import io.github.asherbearce.graphy.model.Graph;
import io.github.asherbearce.graphy.view.GraphAdapter;
import io.github.asherbearce.graphy.viewmodel.GraphEditorViewModel;
import java.util.List;
import java.util.LinkedList;

public class MainMenuFragment extends Fragment {
  private ImageButton openGraphButton;
  private ImageButton newGraphButton;
  private LinearLayout graphListContainer;
  private ListView graphList;
  private List<Graph> graphs;
  private boolean fileViewerOpen = false;
  private GraphAdapter listAdapter;
  private LinearLayout newGraphOption;
  private MainActivity controller;
  //TODO set the controller field to the main activity.
  public MainMenuFragment(){
    graphs = new LinkedList<>();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View fragment = inflater.inflate(R.layout.file_navigator_fragment, container, false);

    newGraphButton = fragment.findViewById(R.id.new_graph_button);
    openGraphButton = fragment.findViewById(R.id.open_graph_button);
    graphListContainer = fragment.findViewById(R.id.list_container);
    graphList = fragment.findViewById(R.id.graphs_list);
    newGraphOption = fragment.findViewById(R.id.new_graph_option);

    setupButtons();
    setupAdapter();
    listAdapter.notifyDataSetChanged();

    return fragment;
  }

  /**
   * Closes the list that displays all the graphs from the database
   */
  public void closeFileViewer(){
    fileViewerOpen = false;
    graphListContainer.setVisibility(View.GONE);
    newGraphOption.setVisibility(View.VISIBLE);
  }

  /**
   * Opens the list that displays all the graphs from the database
   */
  public void openFileViewer(){
    fileViewerOpen = true;
    graphListContainer.setVisibility(View.VISIBLE);
    newGraphOption.setVisibility(View.GONE);
  }

  /**
   * Adds a graph to the list of items to display under the file viewer
   * @param toAdd The graph object to be added to the display
   */
  public void addGraph(Graph toAdd){
    graphs.add(toAdd);

    if (listAdapter != null){
      listAdapter.notifyDataSetChanged();
    }
  }

  private void setupButtons(){
    openGraphButton.setOnClickListener((v -> {
      if (!fileViewerOpen){
        openFileViewer();
      } else{
        closeFileViewer();
      }
    }));
    newGraphButton.setOnClickListener((v) -> {
      controller.openEditorFragment(0);
    });
  }

  private void setupAdapter(){
    listAdapter = new GraphAdapter(getContext().getApplicationContext(), graphs,
        ViewModelProviders.of(this).get(GraphEditorViewModel.class));
    listAdapter.setController(controller);
    graphList.setAdapter(listAdapter);
  }

  /**
   * Sets the main activity controller for this app.
   * @param controller The main activity controller
   */
  public void setController(MainActivity controller){
    this.controller = controller;
  }
}
