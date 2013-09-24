package org.es.minigames.platform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import org.es.minigames.R;
import org.es.minigames.common.DrawingThread;
import org.es.minigames.common.GameElement;
import org.es.minigames.utils.PositionUtils;

/**
 * Created by Cyril on 22/09/13.
 */
public class PlatformThread extends DrawingThread {

    private static final String TAG = "PlatformThread";

    /** The near background scrolls {@link #FAR_NEAR_BG_COEF} times faster than the far one. */
    private final static int FAR_NEAR_BG_COEF = 4;
    // Background bitmaps
    Bitmap mFarBackgroundBmp;
    Bitmap mNearBackgroundBmp;

    // Background elements
    private GameElement[] mFarBackground = new GameElement[2];
    private GameElement[] mNearBackground = new GameElement[2];

    public PlatformThread(SurfaceHolder surfaceHolder, Context context) {
        super(surfaceHolder, context);

        // two background since we want them moving at different speeds
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inSampleSize = 2;
        mFarBackgroundBmp = BitmapFactory.decodeResource(mResources, R.drawable.background_far, bmpOptions);
        mNearBackgroundBmp = BitmapFactory.decodeResource(mResources, R.drawable.background_near, bmpOptions);

        mFarBackground[0] = new GameElement(mFarBackgroundBmp);
        mFarBackground[1] = new GameElement(mFarBackgroundBmp, mFarBackground[0].getRight(), 0);

        mNearBackground[0] = new GameElement(mNearBackgroundBmp);
        mNearBackground[1] = new GameElement(mNearBackgroundBmp, mNearBackground[0].getRight(), 0);
    }

    @Override
    protected void updateSurfaceSize() {

        // Resize the background image
        // Image is larger than the screen => adapt height
        float farBgCoef = (float) mCanvasRect.height() / (float) mFarBackgroundBmp.getHeight();
        float nearBgCoef = (float) mCanvasRect.height() / (float) mNearBackgroundBmp.getHeight();
        float newFarBgWidth = mFarBackgroundBmp.getWidth() * farBgCoef;
        float newNearBgWidth = mNearBackgroundBmp.getWidth() * nearBgCoef;

        mFarBackgroundBmp = Bitmap.createScaledBitmap(mFarBackgroundBmp, (int) newFarBgWidth, mCanvasRect.height(), true);
        mNearBackgroundBmp = Bitmap.createScaledBitmap(mNearBackgroundBmp, (int) newNearBgWidth, mCanvasRect.height(), true);
    }

    @Override
    protected void update() {

        while(!mEventQueue.isEmpty()) {
            processEvent(mEventQueue.poll());
        }
    }

    @Override
    protected void processEvent(MotionEvent event) {

        final int action = event.getActionMasked();
        // final int actionIndex = event.getActionIndex();
        Log.d(TAG, "Action : " + action);

        // TODO onKeyDown => start scrolling
        // TODO onKeyUP => stop scrolling
        if (action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_MOVE) {

            // if click on screen
            if (mCanvasRect.contains((int)event.getX(0), (int)event.getY(0))) {
                scrollBackgrounds(-1, 0);
            }
        }
    }

//    private boolean isGoLeft(MotionEvent event) {
//        return mCanvasRect.contains(event.getX(0), event.getY(0));
//    }
//
//    private boolean isGoRight(MotionEvent event) {
//        return mCanvasRect.contains((int)event.getX(0), (int)event.getY(0));
//    }

    private void scrollBackgrounds(int offSetX, int offSetY) { // decrement the far and near backgrounds
        mFarBackground[0].moveX(offSetX);
        mFarBackground[1].setLeft(mFarBackground[0].getRight());
//        mFarBackground[1].moveX(offSetX);
        mNearBackground[0].moveX(offSetX * FAR_NEAR_BG_COEF);
        mNearBackground[1].setLeft(mNearBackground[0].getRight());
//        mNearBackground[1].moveX(offSetX * FAR_NEAR_BG_COEF);

        // if we have scrolled all the way, reset to start
        if (mFarBackground[0].getRight() <= 0) {
            mFarBackground[0].setLeft(0);
        }
        if (mNearBackground[0].getRight() <= 0) {
            mNearBackground[0].setLeft(0);
        }
    }

    @Override
    protected void doDraw(Canvas canvas) {

        mFarBackground[0].draw(canvas);
        if (mFarBackground[1].getLeft() <= mCanvasRect.width()) {
            mFarBackground[1].draw(canvas);
        }

        mNearBackground[0].draw(canvas);
        if (mNearBackground[1].getLeft() <= mCanvasRect.width()) {
            mNearBackground[1].draw(canvas);
        }

//        if (mFarBg2Left <= mCanvasRect.width()) { // TODO isOnScreen(bg)
            // Draw second background only if necessary
//            canvas.drawBitmap(mFarBackground, mFarBg2Left, 0, null);
//        }

//        canvas.drawBitmap(mNearBackground, mNearBg1Left, 0, null);
//        if (mNearBg2Left <= mCanvasRect.width()) { // TODO isOnScreen(bg)
//            // Draw second only if necessary
//            canvas.drawBitmap(mNearBackground, mNearBg2Left, 0, null);
//        }
    }
}
