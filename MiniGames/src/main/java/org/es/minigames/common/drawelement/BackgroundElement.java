package org.es.minigames.common.drawelement;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by Cyril on 24/09/13.
 */
public class BackgroundElement implements GameElement {

    private static final int STATE_SCROLLING = 0;
    private static final int STATE_STOPPED = 1;

    private Point[] mPositions = new Point[2];
    private Bitmap mBitmap;
    private int mScrollValue = 0;

    private long mDuration = 0;
    /** Time of the last object update. */
    private long mLastUpdate;

    public BackgroundElement(Resources resources, int resId) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mBitmap = BitmapFactory.decodeResource(resources, resId, options);

        mPositions[0] = new Point(0, 0);
        mPositions[1] = new Point();
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        // Resize the background image
        // Image is larger than the screen => adapt height
        final float coefficient = (float) surfaceHeight / (float) mBitmap.getHeight();
        final float newWidth = mBitmap.getWidth() * coefficient;

        mBitmap = Bitmap.createScaledBitmap(mBitmap, (int) newWidth, surfaceHeight, true);
        mPositions[1].x = mPositions[0].x + mBitmap.getWidth();
    }

    @Override
    public void draw(Canvas canvas) {

        for (Point position : mPositions) {
            if (position.x > canvas.getWidth() || position.x + mBitmap.getWidth() < 0) {
                continue;
            }
            canvas.drawBitmap(mBitmap, position.x, position.y, null);
        }

        updateScrollX();
    }

    private void updateScrollX() {

        if (mScrollValue == 0) {
            mLastUpdate = System.currentTimeMillis();
            return;
        }

        final long elapsedTime = System.currentTimeMillis() - mLastUpdate;
        final long value = mScrollValue * elapsedTime / mDuration;

        mPositions[0].x += value;
        int drawingRectRight = mPositions[0].x + mBitmap.getWidth();

        // if we have scrolled all the way, reset to start
        if (drawingRectRight < 0) {
            mPositions[0].x = 0;
        }

        mPositions[1].x =  mPositions[0].x + mBitmap.getWidth();
        mLastUpdate = System.currentTimeMillis();
    }

    public void setScrollSpeed(int scrollValue, long duration) {
        mScrollValue = scrollValue;
        mDuration = duration;
    }
}
