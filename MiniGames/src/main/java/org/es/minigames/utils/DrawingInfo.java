package org.es.minigames.utils;

import android.graphics.PointF;

/**
 * Created by Cyril Leroux on 16/02/14.
 */
public class DrawingInfo {

    private float mTileSize;
    private final PointF mOffset;

    public DrawingInfo() {
        mTileSize = 1f;
        mOffset = new PointF();
    }

    public float getTileSize() { return mTileSize; }

    public void setTileSize(float tileSize) { mTileSize = tileSize; }

    public PointF getOffset() { return mOffset; }

    public void setOffset(float x, float y) { mOffset.set(x, y); }
}
