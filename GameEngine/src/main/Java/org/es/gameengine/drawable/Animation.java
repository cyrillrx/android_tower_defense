package org.es.gameengine.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

import org.es.gameengine.AnimationCallback;

/**
 * Animation class.<br />
 * An animation is an autonomous object that will display a list of images at a predefine rate.<br />
 * The animation can be created by giving either one uncut sprite sheet or a bitmap array.<br />
 * <br />
 * Created by Cyril Leroux on 26/09/13.
 */
public class Animation {

    protected static enum State {
        STATE_RUNNING,
        STATE_STOPPING,
        STATE_STOPPED
    }

    private final Bitmap[] mBitmaps;
    private final AnimationCallback mCallback;
    /** True if the animation is supposed to play loop. */
    private final boolean mIsLoop;
    /** The time during which a bitmap is on the screen before proceeding to the next one. */
    private final float mFrameDuration;

    private int mCurrentFrameId;
    /** The current state of the animation. It can be either one of RUNNING, STOPPING or STOPPED. */
    private State mState;
    /** Time of the last bitmap update. */
    private long mLastUpdate;
    private long mStartTime;

    /**
     * Constructor taking a bitmap array.
     *
     * @param bitmaps The animation bitmaps.
     * @param animationDuration Animation duration in milliseconds.
     * @param isLoop True if the animation is supposed to play loop.
     */
    public Animation(Bitmap[] bitmaps, float animationDuration, boolean isLoop, AnimationCallback callback) {

        mBitmaps = bitmaps;
        mCallback = callback;
        mIsLoop = isLoop;
        mFrameDuration = animationDuration / (float) getFrameCount();

        mState = State.STATE_STOPPED;
        mStartTime = -1;
    }

    /**
     * Constructor that will load a bitmap array from the resources.
     *
     * @param resources Context resources Used to instantiate animation bitmaps.
     * @param resourceIds The resource ids used to instantiate animation bitmaps.
     * @param animationDuration Animation duration in milliseconds.
     * @param isLoop True if the animation is supposed to play loop.
     */
    public Animation(Resources resources, int[] resourceIds, float animationDuration, boolean isLoop, AnimationCallback callback) {
        this(getBitmapsFromResources(resources, resourceIds), animationDuration, isLoop, callback);
    }

    /**
     * Loads an array of bitmaps from the Resources.
     *
     * @param resources The Resources from which to load the bitmaps.
     * @param resourceIds The ids of the resources to load.
     * @return The array of loaded bitmaps.
     */
    private static Bitmap[] getBitmapsFromResources(Resources resources, int[] resourceIds) {
        final int bitmapCount = resourceIds.length;
        Bitmap[] bitmaps = new Bitmap[bitmapCount];

        for (int i = 0; i < bitmapCount; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(resources, resourceIds[i]);
        }
        return bitmaps;
    }

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

    public void draw(Canvas canvas, PointF position) {
        canvas.drawBitmap(getCurrentFrame(), position.x, position.y, null);
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

    /** @return The number of frames in the animation. */
    protected int getFrameCount() {
        return mBitmaps.length;
    }

    /** @return The current frame. */
    protected Bitmap getCurrentFrame() {
        return mBitmaps[mCurrentFrameId];
    }

    public boolean isRunning() {
        return (mState == State.STATE_RUNNING) || (mState == State.STATE_STOPPING);
    }

    public long getStartTime() { return mStartTime; }

    public int getWidth() { return getCurrentFrame().getWidth(); }

    public int getHeight() { return getCurrentFrame().getHeight(); }
}