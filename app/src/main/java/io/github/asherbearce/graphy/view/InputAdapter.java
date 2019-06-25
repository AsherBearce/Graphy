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
import java.util.List;

public class InputAdapter extends ArrayAdapter<String> {
  public InputAdapter(@NonNull Context context, @NonNull List<String> objects){
    super(context, R.layout.input_list_item, objects);
    //TODO inflate this
  }

  @NonNull
  @Override
  public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
    //TODO implement this
    View layout = convertView;

    if (layout == null){
      layout = LayoutInflater.from(getContext()).inflate(R.layout.input_list_item, parent, false);

      EditText input = layout.findViewById(R.id.text_input);
      ImageButton removeButton = layout.findViewById(R.id.remove_item_button);

      removeButton.setOnClickListener(
          new OnClickListener() {
            @Override
            public void onClick(View v) {

              Log.d("Trace", String.format("%d, %s", position, getItem(position)));
              notifyDataSetChanged();
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

            }
          }
      );
    }

    return layout;
  }
}
