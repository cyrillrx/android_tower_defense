package org.es.minigames.towerdefense.battleground;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import org.es.engine.graphics.drawable.DrawableElement;
import org.es.minigames.towerdefense.unit.AbstractUnit;

/**
 * The Tile class represents a piece of battleground.
 *
 * @author Cyril Leroux
 *         Created on 11/02/14.
 */
public class Tile implements DrawableElement {

    private final RectF mBoundingRect;
    private AbstractUnit mBindedUnit = null;

    public Tile() {
        mBoundingRect = new RectF();
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) { }

    @Override
    public void draw(Canvas canvas) {

        // Draw the grid
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0.3f);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(getRect(), paint);

        if (mBindedUnit != null) {
            mBindedUnit.draw(canvas);
        }
    }

    public RectF getRect() { return mBoundingRect; }

    public void setRect(float left, float top, float right, float bottom) {
        mBoundingRect.set(left, top, right, bottom);
    }

    public float centerX() { return mBoundingRect.centerX(); }

    public float centerY() { return mBoundingRect.centerY(); }

    public boolean isEmpty() { return mBindedUnit == null; }

    public boolean isBuildable() { return mBindedUnit == null; }

    public AbstractUnit getBindedUnit() { return mBindedUnit; }

    public void bindUnit(AbstractUnit unit) { mBindedUnit = unit; }
}
