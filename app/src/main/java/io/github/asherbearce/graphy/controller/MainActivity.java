package io.github.asherbearce.graphy.controller;

import static android.view.View.GONE;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.google.android.material.navigation.NavigationView;
import io.github.asherbearce.graphy.R;
import io.github.asherbearce.graphy.database.GraphsDatabase;
import io.github.asherbearce.graphy.model.CalculatorInput;
import io.github.asherbearce.graphy.view.GraphViewWindow;
import io.github.asherbearce.graphy.view.InputAdapter;
import io.github.asherbearce.graphy.vm.ComputeEnvironment;
import io.github.asherbearce.graphy.parsing.*;
import io.github.asherbearce.graphy.token.*;
import io.github.asherbearce.graphy.exception.*;
import io.github.asherbearce.graphy.vm.Function;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {
  private List<CalculatorInput> inputs;
  private LinearLayout graphContainer;
  private LinearLayout inputContainer;
  private ListView inputView;
  private ImageView graphDisplay;
  private ImageButton addInputButton;
  private ImageButton collapseDownButton;
  private GraphViewWindow graphImage;
  private InputAdapter adapter;
  private ComputeEnvironment environment;
  private boolean updatingInputs;
  private GraphsDatabase database;
  private GestureDetectorCompat dragListener;
  boolean listCollapsed;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    graphContainer = findViewById(R.id.graph_container);
    inputContainer = findViewById(R.id.list_view_container);
    inputs = new LinkedList<>();
    inputView = findViewById(R.id.input_list);
    graphDisplay = findViewById(R.id.graph_display);
    addInputButton = findViewById(R.id.add_input_button);
    collapseDownButton = findViewById(R.id.collapse_input_button);
    graphImage = new GraphViewWindow();
    adapter = new InputAdapter(this, inputs);
    dragListener = new GestureDetectorCompat(this, new DragListener());
    environment = ComputeEnvironment.getInstance();

    database = GraphsDatabase.getInstance(getApplication());

    graphDisplay.getViewTreeObserver().addOnGlobalLayoutListener(
        () -> {
          graphImage.setWidth(graphDisplay.getWidth());
          graphImage.setHeight(graphDisplay.getHeight());
        }
    );

    graphDisplay.setOnTouchListener((v, event) -> dragListener.onTouchEvent(event));

    adapter.setOnChangeListener((List<CalculatorInput> inputs) -> {
      if (!updatingInputs){
        updatingInputs = true;
        environment.clearContent();
        LinkedList<Function> functions = new LinkedList<>();

        for (CalculatorInput input : inputs) {
          if (input.getInput() != null && !input.getInput().isEmpty()) {
            try {
              LinkedList<Token> tokens = new Tokenizer(input.getInput()).Tokenize();
              Function newFunc = new Parser(tokens).parseFunction();
              environment.putFunction(newFunc);
              if (newFunc.getNumArgs() == 1) {
                functions.addLast(newFunc);
              }
            } catch (ParseException | UnknownTokenException e) {
              //Do nothing for now
            }
          }
        }
        graphImage.setToDraw(functions);
        graphImage.invalidateSelf();

        updatingInputs = false;
      }
    });

    graphDisplay.setImageDrawable(graphImage);
    inputView.setAdapter(adapter);

    LiveData<List<CalculatorInput>> savedState = database.inputDao().getAll();

    savedState.observe(this, new Observer<List<CalculatorInput>>() {
      @Override
      public void onChanged(List<CalculatorInput> calculatorInputs) {
        for (CalculatorInput input : calculatorInputs){
          inputs.add(input);
        }
        adapter.notifyDataSetChanged();
        adapter.notifyChange();
      }
    });

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    addInputButton.setOnClickListener(v -> {
      //TODO Instead of string, use an object. The issue comes from the string not being unique
      inputs.add(new CalculatorInput());
      adapter.notifyDataSetChanged();
    });

    collapseDownButton.setOnClickListener(v -> {
      if (listCollapsed){
        listCollapsed = false;
        addInputButton.setVisibility(View.VISIBLE);
        inputContainer.setVisibility(View.VISIBLE);
        collapseDownButton.setImageDrawable(getDrawable(R.drawable.collapse_down_icon));
      }
      else{
        listCollapsed = true;
        addInputButton.setVisibility(View.GONE);
        inputContainer.setVisibility(View.GONE);
        collapseDownButton.setImageDrawable(getDrawable(R.drawable.expand_up_icon));
      }
    });
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

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  private class DragListener extends GestureDetector.SimpleOnGestureListener{
    float offsetX;
    float offsetY;

    @Override
    public boolean onDown(MotionEvent e) {
      offsetX = graphImage.getOffsetX();
      offsetY = graphImage.getOffsetY();

      return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
      graphImage.setOffsetX(offsetX + e1.getX() - e2.getX());
      graphImage.setOffsetY(offsetY + e1.getY() - e2.getY());
      graphImage.invalidateSelf();

      return true;
    }
  }
}