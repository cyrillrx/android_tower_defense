package org.es.gameengine.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

/**
 * Created by Cyril on 24/09/13.
 */
public class Background implements GameElement {

    private static final String TAG = "Background";

    private static final int STATE_SCROLLING = 0;
    private static final int STATE_STOPPED = 1;

    private Point[] mPositions = new Point[2];
    private Bitmap mBitmap;
    private float mScrollValue = 0;

    private float mDuration = 0;
    /** Time of the last object update. */
    private long mLastUpdate;

    public Background(Resources resources, int resId) {

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

        updateScrollX(canvas);
    }

    private void updateScrollX(Canvas canvas) {

        if (mScrollValue == 0) {
            mLastUpdate = System.currentTimeMillis();
            return;
        }

        final float elapsedTime = System.currentTimeMillis() - mLastUpdate;
        final float value = mScrollValue * elapsedTime / mDuration;

        // Update positions
        for (Point position : mPositions) {
            position.x += value;
        }

        // if we have scrolled all the way, reset to start
        boolean bg0OutOfScreenLeft  = (mPositions[0].x + mBitmap.getWidth() < 0);
        boolean bg0OutOfScreenRight = (mPositions[0].x > canvas.getWidth());
        boolean bg1OutOfScreenLeft  = (mPositions[1].x + mBitmap.getWidth() < 0);
        boolean bg1OutOfScreenRight = (mPositions[1].x > canvas.getWidth());

        if (bg0OutOfScreenLeft) {
            // redraw bg0 to the right
            mPositions[0].x = mPositions[1].x + mBitmap.getWidth();

        } else if (bg0OutOfScreenRight) {
            // redraw bg0 to the left
            mPositions[0].x = mPositions[1].x - mBitmap.getWidth();

        } else if (bg1OutOfScreenLeft) {
            // redraw bg1 to the right
            mPositions[1].x = mPositions[0].x + mBitmap.getWidth();

        } else if (bg1OutOfScreenRight) {
            // redraw bg1 to the left
            mPositions[1].x = mPositions[0].x - mBitmap.getWidth();
        }

        mLastUpdate = System.currentTimeMillis();
        Log.d(TAG, "Pos1 (" + mPositions[0].x + ":" + mPositions[0].y + ") Pos2 (" + mPositions[1].x + ":" + mPositions[1].y + ")");
    }

    public void setScrollSpeed(float scrollValue, float duration) {
        mScrollValue = scrollValue;
        mDuration = duration;
    }
}
