package io.github.asherbearce.graphy.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
    //TODO implement this
    return null;
  }
}
