package io.github.asherbearce.graphy.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.github.asherbearce.graphy.R;
import io.github.asherbearce.graphy.model.CalculatorInput;
import java.util.List;

public class InputAdapter extends ArrayAdapter<CalculatorInput> {
  private List<CalculatorInput> contents;
  private OnInputChangedListener listener;

  public InputAdapter(@NonNull Context context, @NonNull List<CalculatorInput> objects) {
    super(context, R.layout.input_list_item, objects);
    contents = objects;
    //TODO inflate this
  }

  private void notifyChange(){
    if (listener != null){
      listener.onChange(contents);
    }
  }

  public void setOnChangeListener(OnInputChangedListener listener){
    this.listener = listener;
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
        v -> {
          contents.remove(item);
          InputAdapter.this.notifyDataSetInvalidated();
          InputAdapter.this.notifyChange();
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
            InputAdapter.this.notifyChange();
          }
        }
    );

    return layout;
  }

  public interface OnInputChangedListener{
    void onChange(List<CalculatorInput> contents);
  }
}
