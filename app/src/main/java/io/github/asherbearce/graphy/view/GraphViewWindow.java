package io.github.asherbearce.graphy.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import io.github.asherbearce.graphy.parser.exception.ParseException;
import io.github.asherbearce.graphy.parser.math.Real;
import io.github.asherbearce.graphy.parser.parsing.ComputeEnvironment;
import io.github.asherbearce.graphy.parser.parsing.Function;
import java.util.LinkedList;


public class GraphViewWindow extends Drawable {

  private int height;
  private int width;
  private int offsetX;
  private int offsetY;
  ComputeEnvironment env;
  private LinkedList<Function> toDraw;

  public GraphViewWindow() {
    env = new ComputeEnvironment();
    toDraw = new LinkedList<>();
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setOffsetX(int offsetX) {
    this.offsetX = offsetX;
  }

  public void setOffsetY(int offsetY) {
    this.offsetY = offsetY;
  }

  public LinkedList<Function> getToDraw() {
    return toDraw;
  }

  public void setToDraw(LinkedList<Function> toDraw) {
    this.toDraw = toDraw;
  }

  private void drawBlankGraph(Canvas canvas, Paint paint) {
    paint.setStrokeWidth(4);
    paint.setColor(Color.BLACK);
    int centerX = width / 2;
    int centerY = height / 2;
    int originX = centerX - offsetX;
    int originY = centerY - offsetY;

    if (originX >= centerX - centerX / 2 && originX <= centerX + centerX / 2) {
      canvas.drawLine(originX, 0, originX, height, paint);
    }

    if (originY >= centerY - centerY / 2 && originY <= centerY + centerY / 2) {
      canvas.drawLine(0, originY, width, originY, paint);
    }
  }

  //This should generally be called on a different thread.
  private void drawFunction(Function func, Canvas canvas, Paint paint) {
    int centerX = width / 2;
    int centerY = height / 2;
    float scale = 100;
    paint.setColor(Color.RED);
    paint.setStrokeWidth(5);

    try {
      for (int x = -width / 2; x < width / 2; x++) {
        float xPos = x / scale;
        float xPosNext = (x + 1) / scale;
        float functionOutput = (float)func.invoke(new Real(xPos)).real().getValue();
        float functionOutputNext = (float)func.invoke(new Real(xPosNext)).real().getValue();
        float diffX = xPosNext - xPos;
        float diffY = functionOutput - functionOutputNext;

        if (Math.abs(diffY / diffX) < 1000){
          canvas.drawLine(
              x + centerX, -functionOutput * scale + centerY,
              x + 1 + centerX, -functionOutputNext * scale + centerY,
              paint);
        }
      }
    } catch (ParseException e) {
      //Do nothing
    }
    catch (Exception e){
      //Do nothing
    }
  }

  @Override
  public void draw(Canvas canvas) {
    //For debugging purposes, I'll just draw a simple sine wave.
    //TODO take in drawing instructions, given a view offset and view scale.
    Paint paint = new Paint();
    drawBlankGraph(canvas, paint);
    for (Function func : toDraw){
      if (func != null) {
        drawFunction(func, canvas, paint);
      }
    }
  }

  @Override
  public void setAlpha(int alpha) {

  }

  @Override
  public void setColorFilter(@Nullable ColorFilter colorFilter) {

  }

  @Override
  public int getOpacity() {
    return PixelFormat.OPAQUE;
  }
}