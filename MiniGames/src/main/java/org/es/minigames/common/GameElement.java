package org.es.minigames.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Cyril Leroux on 24/09/13.
 */
public class GameElement {

    private Point mPosition;
    private Bitmap mBitmap;

    public GameElement(Bitmap bitmap) {
        mBitmap = bitmap;
        mPosition = new Point(0, 0);
    }

    public GameElement(Bitmap bitmap, int positionX, int positionY) {
        mBitmap = bitmap;
        mPosition = new Point(positionX, positionY);
    }

    public int getLeft() { return mPosition.x; }

    public int getTop() { return mPosition.y; }

    public int getRight() { return mPosition.x + mBitmap.getWidth(); }

    public int getBottom() { return mPosition.y + mBitmap.getHeight(); }

    public int getWidth() { return mBitmap.getWidth(); }

    public int getHeight() { return mBitmap.getHeight(); }

    public void moveX(int value) { mPosition.x += value; }

//    public void moveY(int value) { mPosition.y += value; }

    public void setLeft(int left) { mPosition.x = left; }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mPosition.x, mPosition.y, null);
    }
}
