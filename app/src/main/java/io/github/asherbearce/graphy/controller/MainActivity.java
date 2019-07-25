package io.github.asherbearce.graphy.controller;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.navigation.NavigationView;
import io.github.asherbearce.graphy.R;
import android.util.Log;
import io.github.asherbearce.graphy.database.GraphsDatabase;
import io.github.asherbearce.graphy.model.CalculatorInput;
import io.github.asherbearce.graphy.model.Graph;
import io.github.asherbearce.graphy.viewmodel.CalculatorInputViewModel;
import java.util.LinkedList;
import java.util.List;

/**
 * Responsible for handling all database operations.
 */
public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private boolean editorOpen = false;
  private GraphEditorFragment editor;
  private MainMenuFragment menuFragment;
  private GraphsDatabase database;
  private CalculatorInputViewModel inputViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    openEditorFragment();

    database = GraphsDatabase.getInstance(getApplication());
    inputViewModel = ViewModelProviders.of(this).get(CalculatorInputViewModel.class);

    /*LiveData<List<CalculatorInput>> savedState = database.inputDao().getAll();

    savedState.observe(this, new Observer<List<CalculatorInput>>() {
      @Override
      public void onChanged(List<CalculatorInput> calculatorInputs) {
        for (CalculatorInput input : calculatorInputs){
          inputs.add(input);
        }
        adapter.notifyDataSetChanged();
        adapter.notifyChange();
      }
    });*/

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  public void openEditorFragment(){
    if (editor == null){
      editor = new GraphEditorFragment();
    }
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.fragment_placeHolder, editor);
    ft.commit();
    editorOpen = true;
  }

  public void openMenuFragment(){
    if (menuFragment == null){
      menuFragment = new MainMenuFragment();
      menuFragment.setController(this);
    }
    LiveData<List<Graph>> graphs = database.graphDao().getAll();

    graphs.observe(this, new Observer<List<Graph>>() {
      @Override
      public void onChanged(List<Graph> graphs) {
        for (Graph graph : graphs){
          menuFragment.addGraph(graph);
        }
      }
    });

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.fragment_placeHolder, menuFragment);
    ft.commit();
    editorOpen = false;
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.new_graph_nav) {
      if (!editorOpen) {
        openEditorFragment();
        menuFragment.closeFileViewer();
      }
    } else if (id == R.id.save_graph_nav) {
      if (editorOpen){
        //TODO save this graph to the database.

      }
    } else if (id == R.id.nav_main_menu) {
      if (editorOpen) {
        openMenuFragment();
      }
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);

    return true;
  }
}