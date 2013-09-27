package org.es.minigames.common.drawelement;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

/**
 * Created by Cyril Leroux on 26/09/13.
 */
public class Animation {

    private static final String TAG = "Animation";

    public static final int STATE_RUNNING = 0;
    public static final int STATE_STOPPING = 1;
    public static final int STATE_STOPPED = 2;

    private Bitmap[] mBitmaps;
    private int mCurrentBitmapId;
    private int mState;
    /** True if the animation is supposed to play loop. */
    private boolean mIsLoop;

    /** The time during which a bitmap is on the screen before proceeding to the next one. */
    private double mFrameDuration;
    /** Time of the last bitmap update. */
    private long mLastUpdate;

    public Animation(Resources resources, int[] resourceIds, double animationDuration, boolean isLoop) {

        final int bitmapCount = resourceIds.length;
        mBitmaps = new Bitmap[bitmapCount];

        for (int i = 0; i < bitmapCount; i++) {
            mBitmaps[i]  = BitmapFactory.decodeResource(resources, resourceIds[i]);
        }

        mState = STATE_STOPPED;
        mIsLoop = isLoop;
        mFrameDuration = animationDuration / (double)mBitmaps.length;
    }

    public void start() {

        if (mState != STATE_RUNNING) {
            mCurrentBitmapId = 0;
            mLastUpdate = System.currentTimeMillis();
            mState = STATE_RUNNING;
        }
    }

    public void stop() {

        if (mState == STATE_RUNNING) {
            mState = STATE_STOPPING;
        }
    }

    public void draw(Canvas canvas, Point position) {
        canvas.drawBitmap(mBitmaps[mCurrentBitmapId], position.x, position.y, null);
        updateBitmap();
    }

    /** Update the current bitmap id if necessary. */
    private void updateBitmap() {

        if (mState == STATE_STOPPED) {
            return;
        }

        final double elapsedTime = System.currentTimeMillis() - mLastUpdate;
        final int step = (int) (elapsedTime / mFrameDuration);
        if (step >= 1) {
            incrementBitmapId(step);
        }
    }

    private void incrementBitmapId(int step) {

        if (mState == STATE_STOPPED) {
            return;
        }

        // update bitmap id for the next draw
        mCurrentBitmapId += step;

        if (mIsLoop && mState != STATE_STOPPING) {
            // if current id is greater than max value then wrap
            mCurrentBitmapId %= mBitmaps.length;

        } else if (mCurrentBitmapId >= mBitmaps.length -1) {
            // Animation stops when it reaches the last bitmap
            mCurrentBitmapId = mBitmaps.length -1;
            mState = STATE_STOPPED;

        }

        mLastUpdate = System.currentTimeMillis();
    }

    public int getWidth() { return mBitmaps[mCurrentBitmapId].getWidth(); }

    public int getHeight() { return mBitmaps[mCurrentBitmapId].getHeight(); }
}