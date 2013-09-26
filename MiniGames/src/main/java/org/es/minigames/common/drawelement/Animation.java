package org.es.minigames.common.drawelement;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by Cyril Leroux on 26/09/13.
 */
public class Animation {

    public static final int STATE_RUNNING = 0;
    public static final int STATE_STOPPED = 1;

    private Bitmap[] mBitmaps;
    private int mCurrentBitmapId = 0;
    private int mState;

    /** The time during which a bitmap is on the screen before proceeding to the next one. */
    private double mFrameDuration;
    /** Time of the last bitmap update. */
    private long mLastUpdate;

    public Animation(Resources resources, int[] resourceIds, double animationDuration) {

        final int bitmapCount = resourceIds.length;
        mBitmaps = new Bitmap[bitmapCount];

        for (int i = 0; i < bitmapCount; i++) {
            mBitmaps[i]  = BitmapFactory.decodeResource(resources, resourceIds[i]);
        }

        mFrameDuration = animationDuration / (double)mBitmaps.length;
        mLastUpdate = System.currentTimeMillis();
        mState = STATE_STOPPED;
    }

    public void draw(Canvas canvas, Point position) {
        canvas.drawBitmap(mBitmaps[mCurrentBitmapId], position.x, position.y, null);
        updateSprite();
    }

    /** Update the current sprite id if necessary. */
    private void updateSprite() {

        if (mState == STATE_STOPPED) {
            return;
        }

        final double elapsedTime = System.currentTimeMillis() - mLastUpdate;
        final int step = (int) (elapsedTime / mFrameDuration);
        if (step >= 1) {
            // update sprite id for the next draw
            mCurrentBitmapId += step;
            // if current id is greater than max value then wrap
            mCurrentBitmapId %= mBitmaps.length;
            mLastUpdate = System.currentTimeMillis();
        }
    }

    public void start() {

        if (mState != STATE_RUNNING) {
            mState = STATE_RUNNING;
            mCurrentBitmapId++;
            // if current id is greater than max value then wrap
            mCurrentBitmapId %= mBitmaps.length;
            mLastUpdate = System.currentTimeMillis();
        }
    }

    public void stop() {

        if (mState != STATE_STOPPED) {
            mState = STATE_STOPPED;
            mLastUpdate = System.currentTimeMillis();
        }
    }

    public int getWidth() { return mBitmaps[mCurrentBitmapId].getWidth(); }

    public int getHeight() { return mBitmaps[mCurrentBitmapId].getHeight(); }
}