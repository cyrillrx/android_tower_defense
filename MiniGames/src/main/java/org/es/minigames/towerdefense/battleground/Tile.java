package org.es.minigames.towerdefense.battleground;

import android.graphics.Canvas;
import android.graphics.RectF;

import org.es.gameengine.drawable.DrawableElement;
import org.es.minigames.towerdefense.unit.AbstractUnit;

/**
 * The Tile class represents a piece of battleground.<br />
 * <br />
 * Created by Cyril Leroux on 11/02/14.
 */
public class Tile implements DrawableElement {

    private AbstractUnit mBindedUnit = null;
    private final RectF mBoundingRect;

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) { }

    @Override
    public void draw(Canvas canvas) {

    }

    public Tile() {
        mBoundingRect = new RectF();
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
