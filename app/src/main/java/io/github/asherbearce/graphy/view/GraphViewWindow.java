package io.github.asherbearce.graphy.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;

public class GraphViewWindow extends Drawable{
  private int height;
  private int width;
  private int offsetX;
  private int offsetY;

  public void setHeight(int height){
    this.height = height;
  }

  public void setWidth(int width){
    this.width = width;
  }

  public void setOffsetX(int offsetX) {
    this.offsetX = 100;
  }

  public void setOffsetY(int offsetY) {
    this.offsetY = offsetY;
  }

  private void drawBlankGraph(Canvas canvas, Paint paint){
    paint.setStrokeWidth(4);
    paint.setColor(Color.BLACK);
    int centerX = width / 2;
    int centerY = height / 2;
    int originX = centerX - offsetX;
    int originY = centerY - offsetY;

    if (originX >= centerX - centerX / 2 && originX <= centerX + centerX / 2){
      canvas.drawLine(originX, 0, originX, height, paint);
    }

    if (originY >= centerY - centerY / 2 && originY <= centerY + centerY / 2){
      canvas.drawLine(0, originY, width, originY, paint);
    }
  }

  @Override
  public void draw(Canvas canvas){
    //For debugging purposes, I'll just draw a simple sine wave.
    //TODO take in drawing instructions, given a view offset and view scale.
    Paint paint = new Paint();
    drawBlankGraph(canvas, paint);
  }

  @Override
  public void setAlpha(int alpha){

  }

  @Override
  public void setColorFilter(@Nullable ColorFilter colorFilter) {

  }

  @Override
  public int getOpacity() {
    return PixelFormat.OPAQUE;
  }
}