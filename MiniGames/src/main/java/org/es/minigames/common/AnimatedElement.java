package org.es.minigames.common;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.TimerTask;

/**
 * Simple loop animated element.
 *
 * Created by Cyril on 25/09/13.
 */
public class AnimatedElement implements GameElement {

    private Point mPosition;
    private Bitmap[] mBitmaps;
    private int mCurrentBitmapId = 0;

    /** Animation duration in milliseconds */
    private long mDuration;
    /** Time of the last bitmap update. */
    private long mLastUpdate;

    public AnimatedElement(Resources resources, int[] resIds, long durationMs) {

        final int resIdCount = resIds.length;
        mBitmaps = new Bitmap[resIdCount];

        for (int i = 0; i < resIdCount; i++) {
            mBitmaps[i]  = BitmapFactory.decodeResource(resources, resIds[i]);
        }
        mPosition = new Point(0, 0);
        mDuration = durationMs;
        mLastUpdate = System.currentTimeMillis();

    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawBitmap(mBitmaps[mCurrentBitmapId], mPosition.x, mPosition.y, null);
        updateBitmapId();

    }

    private void updateBitmapId() {

        final long elapsedTime = System.currentTimeMillis() - mLastUpdate;
        if (elapsedTime >= (float) mDuration/(float)mBitmaps.length) {
            // update bitmap id for the next draw
            mCurrentBitmapId++;
            // if current id is greater than max value then wrap
            if (mCurrentBitmapId >= mBitmaps.length) {
                mCurrentBitmapId = 0;
            }
            mLastUpdate = System.currentTimeMillis();
        }
    }

    public int getLeft() { return mPosition.x; }

    public int getTop() { return mPosition.y; }

    public int getRight() { return mPosition.x + mBitmaps[mCurrentBitmapId].getWidth(); }

    public int getBottom() { return mPosition.y + mBitmaps[mCurrentBitmapId].getHeight(); }

    public int getWidth() { return mBitmaps[mCurrentBitmapId].getWidth(); }

    public int getHeight() { return mBitmaps[mCurrentBitmapId].getHeight(); }

    public void moveX(int value) { mPosition.x += value; }

    public void moveY(int value) { mPosition.y += value; }

}
