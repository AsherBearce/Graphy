package io.github.asherbearce.graphy.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.github.asherbearce.graphy.R;
import io.github.asherbearce.graphy.model.Graph;
import java.util.List;
import java.util.LinkedList;

public class MainMenuFragment extends Fragment {
  private ImageButton openGraphButton;
  private ImageButton newGraphButton;
  private LinearLayout graphListContainer;
  private ListView graphList;
  private List<Graph> graphs;
  private boolean fileViewerOpen = false;
  private ArrayAdapter<Graph> listAdapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View fragment = inflater.inflate(R.layout.file_navigator_fragment, container, false);

    newGraphButton = fragment.findViewById(R.id.new_graph_button);
    openGraphButton = fragment.findViewById(R.id.open_graph_button);
    graphListContainer = fragment.findViewById(R.id.list_container);
    graphList = fragment.findViewById(R.id.graphs_list);
    graphs = new LinkedList<>();

    setupButtons();

    return fragment;
  }

  /**
   * Closes the list that displays all the graphs from the database
   */
  public void closeFileViewer(){
    fileViewerOpen = false;
    graphListContainer.setVisibility(View.GONE);
  }

  /**
   * Opens the list that displays all the graphs from the database
   */
  public void openFileViewer(){
    fileViewerOpen = true;
    graphListContainer.setVisibility(View.VISIBLE);
  }

  public void setGraphs(List<Graph> graphs){
    this.graphs = graphs;
  }

  private void setupButtons(){
    //TODO setup the open graph button. Get database info from main activity.
    openGraphButton.setOnClickListener((v -> {
      if (!fileViewerOpen){
        openFileViewer();
      } else{
        closeFileViewer();
      }
    }));

    newGraphButton.setOnClickListener((v -> {
      //TODO tell the main activity to switch fragments

    }));
  }
}
