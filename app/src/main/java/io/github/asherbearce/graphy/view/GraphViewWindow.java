package io.github.asherbearce.graphy.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import io.github.asherbearce.graphy.vm.Function;
import java.util.LinkedList;
import io.github.asherbearce.graphy.math.*;
import io.github.asherbearce.graphy.exception.*;

/**
 * The class responsible for drawing the inputs to the screen
 */
public class GraphViewWindow extends Drawable {

  private int height;
  private int width;
  private float offsetX;
  private float offsetY;
  private GestureDetectorCompat dragListener;
  private LinkedList<Function> toDraw;
  private float scale = 100;
  private boolean drawing = false;

  private static final int GRAPH_RESOLUTION = 10;

  /**
   * Constructs a new GraphViewWindow.
   */
  public GraphViewWindow() {
    toDraw = new LinkedList<>();
  }

  /**
   * Sets the height of this viewport in pixels
   * @param height The new height of this viewport
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Sets the widht of this viewport in pixels
   * @param width The new width of this viewport
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Sets the size of the viewport in screenspace. Does not effect the size at which this window is
   * drawn.
   * @param scale The scale of the window
   */
  public void setScale(float scale) {
    this.scale = scale;
  }

  /**
   * Returns the height of the viewport in pixels
   * @return int
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns the width of the viewport in pixels
   * @return int
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets the x coordinate of the origin relative to the upper left corner of the screen
   * @return float
   */
  public float getOriginX() {
    return width / 2 - offsetX;
  }

  /**
   * Gets the y coordinate of the origin relative to the upper left corner of the screen
   * @return float
   */
  public float getOriginY() {
    return height / 2 - offsetY;
  }

  /**
   * Sets the x coordinate of the offset of the center of the screen from the origin
   * @param offsetX The new x coordinate of the offset
   */
  public void setOffsetX(float offsetX) {
    this.offsetX = offsetX;
  }

  /**
   * Sets the y coordinate of the offset of the center of the screen from the origin
   * @param offsetY The new y coordinate of the offset
   */
  public void setOffsetY(float offsetY) {
    this.offsetY = offsetY;
  }

  /**
   * Returns the x coordinate of the offset of the center of the screen from the origin
   * @return float
   */
  public float getOffsetX() {
    return offsetX;
  }

  /**
   * Returns the y coordinate of the offset of the center of the screen from the origin
   * @return float
   */
  public float getOffsetY() {
    return offsetY;
  }

  /**
   * Returns the list of Function objects to be drawn by the viewport
   * @return {@link LinkedList}
   */
  public LinkedList<Function> getToDraw() {
    return toDraw;
  }

  /**
   * Sets the Function objects that should be drawn by the viewport
   * @param toDraw The list of {@link Function} objects to be drawn
   */
  public void setToDraw(LinkedList<Function> toDraw) {
    this.toDraw = toDraw;
  }

  private void drawBlankGraph(Canvas canvas, Paint paint) {
    int centerX = width / 2;
    int centerY = height / 2;
    int originX = centerX - (int) offsetX;
    int originY = centerY - (int) offsetY;
    float camWidth = width / scale;
    float camHeight = height / scale;
    float upperLeftX = offsetX / scale - camWidth / 2;
    float upperLeftY = offsetY / scale + camHeight / 2;
    float lowerRightX = offsetX / scale + camWidth / 2;
    float lowerRightY = offsetY / scale - camHeight / 2;

    paint.setAntiAlias(true);
    paint.setStrokeWidth(10);
    paint.setColor(Color.BLACK);
    canvas.drawLine(originX, 0, originX, height, paint);
    canvas.drawLine(0, originY, width, originY, paint);
    paint.setStrokeWidth(3);
    paint.setColor(Color.GRAY);

    for (int x = (int) Math.floor(upperLeftX); x < Math.ceil(lowerRightX); x++) {
      canvas.drawLine(originX + x * scale, 0, originX + x * scale, height, paint);
    }

    for (int y = (int) Math.floor(lowerRightY); y < Math.ceil(upperLeftY); y++) {
      canvas.drawLine(0, originY + y * scale, width, originY + y * scale, paint);
    }
  }

  //This should generally be called on a different thread.
  private void drawFunction(Function func, Canvas canvas, Paint paint) {
    int centerX = width / 2;
    int centerY = height / 2;
    paint.setColor(Color.RED);
    paint.setStrokeWidth(5);

    try {//TODO Set up a class for holding the function data so that a thread may draw it, and then once it's done, set it to the new drawing for that function.
      for (int x = -width / 2; x < width / 2; x += GRAPH_RESOLUTION) {
        float xPos = (x + offsetX) / scale;
        float xPosNext = (x + offsetX + GRAPH_RESOLUTION) / scale;
        float functionOutput = (float) func.invoke(new Real(xPos)).real().getValue();
        float functionOutputNext = (float) func.invoke(new Real(xPosNext)).real().getValue();
        float diffX = xPosNext - xPos;
        float diffY = functionOutput - functionOutputNext;

        if (Math.abs(diffY / diffX) < 1000) {

          canvas.drawLine(
              x + centerX, -functionOutput * scale + centerY - offsetY,
              x + GRAPH_RESOLUTION + centerX, -functionOutputNext * scale + centerY - offsetY,
              paint);
        }
      }
    } catch (ParseException e) {
      //Do nothing
    } catch (Exception e) {
      //Do nothing
    }
  }

  @Override
  public void draw(Canvas canvas) {
    Paint paint = new Paint();
    drawBlankGraph(canvas, paint);

    for (Function func : toDraw) {
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