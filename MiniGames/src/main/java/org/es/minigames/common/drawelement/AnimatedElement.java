package org.es.minigames.common.drawelement;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Simple loop animated element.
 * Plays a bitmap list in loop.
 *
 * Created by Cyril on 25/09/13.
 */
public class AnimatedElement implements GameElement {

    private static final int STATE_RUNNING = 0;
    private static final int STATE_STOPPED = 1;

    private Point mPosition;
    private Bitmap[] mBitmaps;
    private int mCurrentBitmapId = 0;
    private int mState;

    /** The time during which a sprite is on the screen before proceeding to the next one. */
    private double mDuration;
    /** Time of the last bitmap update. */
    private long mLastUpdate;

    /**
     * @param animationDuration Animation duration in milliseconds.
     */
    public AnimatedElement(Resources resources, int[] resIds, double animationDuration) {

        final int resIdCount = resIds.length;
        mBitmaps = new Bitmap[resIdCount];

        for (int i = 0; i < resIdCount; i++) {
            mBitmaps[i]  = BitmapFactory.decodeResource(resources, resIds[i]);
        }

        mPosition = new Point(0, 0);
        mDuration = animationDuration / (double)mBitmaps.length;
        mLastUpdate = System.currentTimeMillis();
        mState = STATE_STOPPED;
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        mPosition.x = surfaceWidth / 2 - getWidth() / 2;
        mPosition.y = surfaceHeight - getHeight();
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawBitmap(mBitmaps[mCurrentBitmapId], mPosition.x, mPosition.y, null);
        updateBitmapId();
    }

    private void updateBitmapId() {

        if (mState == STATE_STOPPED) {
            mLastUpdate = System.currentTimeMillis();
            return;
        }

        final double elapsedTime = System.currentTimeMillis() - mLastUpdate;
        final int step = (int) (elapsedTime / mDuration);
        if (step >= 1) {
            // update bitmap id for the next draw
            // if current id is greater than max value then wrap
            mCurrentBitmapId = (mCurrentBitmapId + step) % mBitmaps.length;
            mLastUpdate = System.currentTimeMillis();
        }
    }

    public void startAnimation() { mState = STATE_RUNNING; }

    public void stopAnimation() { mState = STATE_STOPPED; }

    public int getLeft() { return mPosition.x; }

    public int getTop() { return mPosition.y; }

    public int getRight() { return mPosition.x + mBitmaps[mCurrentBitmapId].getWidth(); }

    public int getBottom() { return mPosition.y + mBitmaps[mCurrentBitmapId].getHeight(); }

    public int getWidth() { return mBitmaps[mCurrentBitmapId].getWidth(); }

    public int getHeight() { return mBitmaps[mCurrentBitmapId].getHeight(); }

    public void moveX(int value) { mPosition.x += value; }

    public void moveY(int value) { mPosition.y += value; }
}
