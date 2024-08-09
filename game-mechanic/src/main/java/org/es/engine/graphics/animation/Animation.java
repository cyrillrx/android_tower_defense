package org.es.engine.graphics.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Animation class.<br />
 * An animation is an autonomous object that will display a list of images at a predefine rate.
 *
 * @author Cyril Leroux
 *         Created on 26/09/13.
 */
public abstract class Animation {

    protected static enum State {
        STATE_RUNNING,
        STATE_STOPPING,
        STATE_STOPPED
    }

    private final AnimationCallback mCallback;
    /** True if the animation is supposed to play loop. */
    private final boolean mIsLoop;
    /** The time during which a bitmap is on the screen before proceeding to the next one. */
    private final float mFrameDuration;

    protected int mCurrentFrameId;
    /** The current state of the animation. It can be either one of RUNNING, STOPPING or STOPPED. */
    private State mState;
    /** Time of the last bitmap update. */
    private long mLastUpdate;
    private long mStartTime;

    /**
     * @param frameDuration Frame duration in milliseconds.
     * @param isLoop        True if the animation is supposed to play loop.
     * @param callback      The object that will be called when the animation ends.
     */
    public Animation(float frameDuration, boolean isLoop, AnimationCallback callback) {

        mCallback = callback;
        mIsLoop = isLoop;
        mFrameDuration = frameDuration;

        mState = State.STATE_RUNNING;
        mStartTime = -1;
    }

    public abstract void draw(Canvas canvas, RectF boundingRect);

    public abstract Bitmap getBitmap(int frameId);

    protected abstract float getWidth();

    protected abstract float getHeight();

    /** @return The number of frames in the animation. */
    protected abstract int getFrameCount();

    public void start() {

        if (mState != State.STATE_RUNNING) {
            mCurrentFrameId = 0;
            mStartTime = System.currentTimeMillis();
            mLastUpdate = mStartTime;
            mState = State.STATE_RUNNING;
        }
    }

    public void stop() {

        if (mState == State.STATE_RUNNING) {
            mState = State.STATE_STOPPING;
        }
    }

    /** Update the current frame id if necessary. */
    public boolean updateFrame() {

        if (mState == State.STATE_STOPPED) {
            return false;
        }

        final float elapsedTime = System.currentTimeMillis() - mLastUpdate;
        final int step = (int) (elapsedTime / mFrameDuration);
        if (step >= 1) {
            incrementImageId(step);
            return true;
        }
        return false;
    }

    private void incrementImageId(int step) {

        if (mState == State.STATE_STOPPED) {
            return;
        }

        // update bitmap id for the next draw
        mCurrentFrameId += step;

        if (mIsLoop && mState != State.STATE_STOPPING) {
            // if current id is greater than max value then wrap
            mCurrentFrameId %= getFrameCount();

        } else if (mCurrentFrameId >= getFrameCount() - 1) {
            // Animation stops when it reaches the last bitmap
            mCurrentFrameId = getFrameCount() - 1;
            mState = State.STATE_STOPPED;
            mStartTime = -1;
            if (mCallback != null) {
                mCallback.onAnimationStopped();
            }
        }

        mLastUpdate = System.currentTimeMillis();
    }

    public boolean isRunning() {
        return (mState == State.STATE_RUNNING) || (mState == State.STATE_STOPPING);
    }

    public long getStartTime() { return mStartTime; }
}