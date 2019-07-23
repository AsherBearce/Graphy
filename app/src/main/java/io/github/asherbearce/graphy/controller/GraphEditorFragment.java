package io.github.asherbearce.graphy.controller;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import io.github.asherbearce.graphy.R;
import io.github.asherbearce.graphy.exception.UnknownTokenException;
import io.github.asherbearce.graphy.exception.ParseException;
import io.github.asherbearce.graphy.model.CalculatorInput;
import io.github.asherbearce.graphy.parsing.Parser;
import io.github.asherbearce.graphy.parsing.Tokenizer;
import io.github.asherbearce.graphy.token.Token;
import io.github.asherbearce.graphy.view.GraphViewWindow;
import io.github.asherbearce.graphy.view.InputAdapter;
import io.github.asherbearce.graphy.vm.ComputeEnvironment;
import io.github.asherbearce.graphy.vm.Function;
import java.util.LinkedList;
import java.util.List;

/**
 * The class responsible for handling all graph drawing.
 */
public class GraphEditorFragment extends Fragment {
  private View frag;
  private List<CalculatorInput> functions;
  private LinearLayout graphContainer;
  private LinearLayout inputContainer;
  private ListView inputView;
  private InputAdapter adapter;
  private ImageView graphDisplay;
  private ImageButton addInputButton;
  private ImageButton collapseDownButton;
  private GraphViewWindow graphImage;
  private boolean updatingInputs;
  private boolean listCollapsed;
  private GestureDetectorCompat dragListener;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    frag = inflater.inflate(R.layout.graph_view_fragment, container, false);
    graphContainer = frag.findViewById(R.id.graph_container);
    inputContainer = frag.findViewById(R.id.list_view_container);
    inputView = frag.findViewById(R.id.input_list);
    functions = new LinkedList<>();
    graphDisplay = frag.findViewById(R.id.graph_display);
    graphImage = new GraphViewWindow();
    addInputButton = frag.findViewById(R.id.add_input_button);
    collapseDownButton = frag.findViewById(R.id.collapse_input_button);
    dragListener = new GestureDetectorCompat(getContext().getApplicationContext(), new DragListener());

    graphDisplay.getViewTreeObserver().addOnGlobalLayoutListener(
        () -> {
          graphImage.setWidth(graphDisplay.getWidth());
          graphImage.setHeight(graphDisplay.getHeight());
        }
    );

    graphDisplay.setOnTouchListener((v, event) -> dragListener.onTouchEvent(event));
    graphDisplay.setImageDrawable(graphImage);
    setupAdapter(getContext().getApplicationContext());
    setupButtons(getContext().getApplicationContext());

    return frag;
  }

  private void setupAdapter(Context context){
    adapter = new InputAdapter(context, functions);
    adapter.setOnChangeListener((List<CalculatorInput> inputs) -> {
      if (!updatingInputs){
        updatingInputs = true;
        ComputeEnvironment.getInstance().clearContent();
        LinkedList<Function> funcs = new LinkedList<>();

        for (CalculatorInput input : functions){
          if (input.getInput() != null && !input.getInput().isEmpty()){
            try {
              LinkedList<Token> tokens = new Tokenizer(input.getInput()).Tokenize();
              Function parsedFunction = new Parser(tokens).parseFunction();
              ComputeEnvironment.getInstance().putFunction(parsedFunction);
              if (parsedFunction.getNumArgs() == 1){
                funcs.addLast(parsedFunction);
              }
            } catch (ParseException | UnknownTokenException e){
              //Eventually let the user know that there was a parsing error
            }
          }
        }
        graphImage.setToDraw(funcs);
        graphImage.invalidateSelf();

        updatingInputs = false;
      }
    });

    inputView.setAdapter(adapter);
  }

  private void setupButtons(Context context){
    addInputButton.setOnClickListener(v -> {
      functions.add(new CalculatorInput());
      adapter.notifyDataSetChanged();
    });

    collapseDownButton.setOnClickListener(v -> {
      if (listCollapsed){
        listCollapsed = false;
        addInputButton.setVisibility(View.VISIBLE);
        inputContainer.setVisibility(View.VISIBLE);
        collapseDownButton.setImageDrawable(context.getDrawable(R.drawable.collapse_down_icon));
      } else {
        listCollapsed = true;
        addInputButton.setVisibility(View.GONE);
        inputContainer.setVisibility(View.GONE);
        collapseDownButton.setImageDrawable(context.getDrawable(R.drawable.expand_up_icon));
      }
    });
  }

  private class DragListener extends GestureDetector.SimpleOnGestureListener{
    private float offsetX;
    private float offsetY;

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
      graphImage.setOffsetX(offsetX + e1.getX() - e2.getX());
      graphImage.setOffsetY(offsetY + e1.getY() - e2.getY());
      graphImage.invalidateSelf();

      return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
      offsetX = graphImage.getOffsetX();
      offsetY = graphImage.getOffsetY();

      return true;
    }
  }
}