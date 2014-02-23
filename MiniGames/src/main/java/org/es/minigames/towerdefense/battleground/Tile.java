package org.es.minigames.towerdefense.battleground;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import org.es.engine.graphics.drawable.DrawableElement;
import org.es.engine.graphics.utils.DrawingParam;
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
    private Offensive mBoundUnit = null;

    private boolean mBuildable;
    private boolean mWalkable;


    public Tile(int columnId, int rowId, Bitmap background) {
        mPosX = columnId;
        mPosY = rowId;
        mBackground = background;

        mBuildable = true;
        mWalkable = true;
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) { }

    @Override
    public void draw(Canvas canvas, DrawingParam param) {

        RectF boundingRect = getRectOnScreen(param);

        // Draw the background
        canvas.drawBitmap(mBackground, null, boundingRect, null);

        // Draw the grid
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0.3f);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(boundingRect, paint);
    }

    /** @return The rect on screen. */
    private RectF getRectOnScreen(DrawingParam param) {

        float left = mPosX * param.coef() + param.offsetX();
        float top = mPosY * param.coef() + param.offsetY();
        float right = left + param.coef();
        float bottom = top + param.coef();

        return new RectF(left, top, right, bottom);
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