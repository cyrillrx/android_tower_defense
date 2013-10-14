package org.es.gameengine.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import org.es.gameengine.AnimationCallback;

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
    private float mFrameDuration;
    /** Time of the last bitmap update. */
    private long mLastUpdate;
    private long mStartTime;
    private AnimationCallback mCallback;

    /**
     * @param animationDuration Animation duration in milliseconds.
     */
    public Animation(Resources resources, int[] resourceIds, float animationDuration, boolean isLoop, AnimationCallback callback) {

        mCallback = callback;
        final int bitmapCount = resourceIds.length;
        mBitmaps = new Bitmap[bitmapCount];

        for (int i = 0; i < bitmapCount; i++) {
            mBitmaps[i]  = BitmapFactory.decodeResource(resources, resourceIds[i]);
        }

        mState = STATE_STOPPED;
        mStartTime = -1;
        mIsLoop = isLoop;
        mFrameDuration = animationDuration / (float)mBitmaps.length;
    }

    public void start() {

        if (mState != STATE_RUNNING) {
            mCurrentBitmapId = 0;
            mStartTime = System.currentTimeMillis();
            mLastUpdate = mStartTime;
            mState = STATE_RUNNING;
        }
    }

    public void stop() {

        if (mState == STATE_RUNNING) {
            mState = STATE_STOPPING;
        }
    }

    /** Update the current bitmap id if necessary. */
    public boolean updateBitmap() {

        if (mState == STATE_STOPPED) {
            return false;
        }

        final float elapsedTime = System.currentTimeMillis() - mLastUpdate;
        final int step = (int) (elapsedTime / mFrameDuration);
        if (step >= 1) {
            incrementBitmapId(step);
            return true;
        }
        return false;
    }

    public void draw(Canvas canvas, Point position) {
        canvas.drawBitmap(mBitmaps[mCurrentBitmapId], position.x, position.y, null);
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
            mStartTime = -1;
            mCallback.onAnimationStopped();
        }

        mLastUpdate = System.currentTimeMillis();
    }

    public boolean isRunning() {
        return (mState == STATE_RUNNING) || (mState == STATE_STOPPING);
    }

    public long getStartTime() { return mStartTime; }

    public int getWidth() { return mBitmaps[mCurrentBitmapId].getWidth(); }

    public int getHeight() { return mBitmaps[mCurrentBitmapId].getHeight(); }
}