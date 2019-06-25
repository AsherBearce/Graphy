package io.github.asherbearce.graphy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.View;
import androidx.core.view.GestureDetectorCompat;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import io.github.asherbearce.graphy.view.InputAdapter;
import java.util.LinkedList;
import java.util.List;
import io.github.asherbearce.graphy.view.GraphViewWindow;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {
  List<String> inputs;
  LinearLayout graphContainer;
  ListView inputView;
  ImageView graphDisplay;
  ImageButton addInputButton;
  GraphViewWindow graphImage;
  GestureDetectorCompat scrollDetect;
  InputAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    graphContainer = findViewById(R.id.graph_container);
    inputs = new LinkedList<>();
    inputView = findViewById(R.id.input_list);
    graphDisplay = findViewById(R.id.graph_display);
    addInputButton = findViewById(R.id.add_input_button);
    graphImage = new GraphViewWindow();
    scrollDetect = new GestureDetectorCompat(this, new ScrollListener());
    adapter = new InputAdapter(this, inputs);

    graphDisplay.getViewTreeObserver().addOnGlobalLayoutListener(
        new OnGlobalLayoutListener() {
          @Override
          public void onGlobalLayout() {
            graphImage.setWidth(graphDisplay.getWidth());
            graphImage.setHeight(graphDisplay.getHeight());
          }
        }
    );

    graphDisplay.setImageDrawable(graphImage);
    inputView.setAdapter(adapter);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    addInputButton.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v){
        //TODO Instead of string, use an object. The issue comes from the string not being unique
        inputs.add("Hello, world!");
        //inputView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
      }
    });


  }

  public void updateInputList(){
    InputAdapter adapter = new InputAdapter(this, inputs);
    inputView.setAdapter(adapter);
  }

  /*public boolean onTouch(View view, MotionEvent event){
    return scrollDetect.onTouchEvent(event);
  }*/

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

  private class ScrollListener extends GestureDetector.SimpleOnGestureListener{

    public boolean onDown(MotionEvent evt){
      graphImage.setOffsetX((int)evt.getX());
      graphImage.setOffsetY((int)evt.getY());
      //graphImage.invalidateSelf();

      return true;
    }
  }
}
