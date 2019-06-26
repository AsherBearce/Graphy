package io.github.asherbearce.graphy.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.github.asherbearce.graphy.R;
import io.github.asherbearce.graphy.model.CalculatorInput;
import io.github.asherbearce.graphy.parser.exception.ParseException;
import io.github.asherbearce.graphy.parser.parsing.Tokenizer;
import io.github.asherbearce.graphy.parser.token.Token;
import java.util.LinkedList;
import java.util.List;

public class InputAdapter extends ArrayAdapter<CalculatorInput> {

  private List<CalculatorInput> contents;

  public InputAdapter(@NonNull Context context, @NonNull List<CalculatorInput> objects) {
    super(context, R.layout.input_list_item, objects);
    contents = objects;
    //TODO inflate this
  }

  @NonNull
  @Override
  public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    //TODO implement this
    View layout = convertView == null ?
        LayoutInflater.from(getContext()).inflate(R.layout.input_list_item, parent, false)
        : convertView;
    CalculatorInput item = getItem(position);

    final EditText input = layout.findViewById(R.id.text_input);
    ImageButton removeButton = layout.findViewById(R.id.remove_item_button);

    input.setText(item.getInputString());

    removeButton.setOnClickListener(
        new OnClickListener() {
          @Override
          public void onClick(View v) {
            //Log.d("Trace", item.getInputString());
            contents.remove(item);
            InputAdapter.this.notifyDataSetInvalidated();
          }
        }
    );

    input.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s) {
            item.setInputString(s.toString());
            //TODO ask for help on this. Not sure what's happening.
            if (s.length() > 0) {
              String identifier;
              LinkedList<Token> tokens = new Tokenizer(s.toString()).Tokenize();
              //TODO delete old and unused methods.
              try{
                item.getEnvironment().parseStatement(tokens);
                Log.d("Trace", "Parsed!");
                for (Token t : tokens){
                  Log.d("Trace", t.getTokenType().toString());
                }

              }
              catch(ParseException e){
                Log.d("Trace", "Not parsed!");
                for (Token t : tokens){
                  Log.d("Trace", t.getTokenType().toString());
                }
                Log.d("Trace", e.getMessage());
              }
            }
          }
        }
    );

    return layout;
  }
}
