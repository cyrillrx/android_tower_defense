package org.es.minigames.towerdefense.battleground;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import org.es.engine.graphics.drawable.DrawableElement;
import org.es.minigames.towerdefense.unit.Offensive;

/**
 * The Tile class represents a piece of battleground.
 *
 * @author Cyril Leroux
 *         Created on 11/02/14.
 */
public class Tile implements DrawableElement {

    public static enum Type {
        GRASS, METAL
    }

    private final Bitmap mBackground;
    private final int mPosX;
    private final int mPosY;
    private final RectF mBoundingRect;
    private Offensive mBoundUnit = null;

    private boolean mBuildable;
    private boolean mWalkable;


    public Tile(int columnId, int rowId, Bitmap background) {
        mPosX = columnId;
        mPosY = rowId;
        mBackground = background;
        mBoundingRect = new RectF();

        mBuildable = true;
        mWalkable = true;
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) { }

    @Override
    public void draw(Canvas canvas) {

        // Draw the background
        canvas.drawBitmap(mBackground, null, getRect(), null);

        // Draw the grid
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0.3f);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(getRect(), paint);
    }

    //
    // Grid positions
    //

    /** @return The position on the grid. */
    @Override
    public float getPosX() { return mPosX; }

    /** @return The position on the grid. */
    @Override
    public float getPosY() { return mPosY; }

    /** Do nothing. The position of the tile is immutable. */
    @Override
    public void setPosition(float x, float y) { }

    @Override
    public float getWidth() { return 1f; }

    @Override
    public float getHeight() { return 1f; }

    /** @return The centerX position on the grid. */
    public float getCenterX() { return mPosX + getWidth() / 2f; }

    /** @return The centerY position on the grid. */
    public float getCenterY() { return mPosY + getHeight() / 2f; }

    //
    // position on screen
    //

    /** @return The rect on screen. */
    public RectF getRect() { return mBoundingRect; }

    /** Set the rect on screen. */
    public void setRect(float left, float top, float right, float bottom) {
        mBoundingRect.set(left, top, right, bottom);
    }

    //
    // Other
    //

    public boolean isEmpty() { return mBoundUnit == null; }

    /** @return True if the tile is available for building. */
    public boolean isBuildable() { return mBuildable && isEmpty(); }

    /** @return True if the tile is available for walking. */
    public boolean isWalkable() { return mWalkable && isEmpty(); }

    public Offensive getBoundUnit() { return mBoundUnit; }

    public void bindUnit(Offensive unit) { mBoundUnit = unit; }
}