package org.es.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;

import org.es.engine.gamemechanic.DrawingThread;
import org.es.towerdefense.component.HUD;
import org.es.towerdefense.object.Player;
import org.es.towerdefense.process.GameMgr;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class TowerDefenseThread extends DrawingThread {

    private enum TouchMode {
        NONE, SCROLL, ZOOM
    }

    private final Player mPlayer;
    private final HUD mMainHud;
    private final GameMgr mGameMgr;

    private GestureDetector mScrollDetector;
    private ScaleGestureDetector mScaleDetector;

    private float mScaleFactor = 1.f;

    private TouchMode mTouchMode;

    public TowerDefenseThread(SurfaceHolder surfaceHolder, Context context) {
        super(surfaceHolder, context);

        mPlayer = new Player(20, 1000);
        mGameMgr = new GameMgr(mPlayer, context);
        mMainHud = new HUD(mPlayer, mGameMgr, context.getResources());
        mTouchMode = TouchMode.NONE;

        mScrollDetector = new GestureDetector(context, new ScrollListener());
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    protected void updateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        mMainHud.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
        mGameMgr.updateSurfaceSize(surfaceWidth, surfaceHeight);
    }

    @Override
    protected boolean update() {
        while (!mEventQueue.isEmpty()) {
            processEvent(mEventQueue.poll());
        }
        mGameMgr.update();
        return false;
    }

    @Override
    protected void processEvent(MotionEvent event) {

        // TODO implementation

        final int action = event.getAction();

        switch (action) {

            // First pointer down
            case MotionEvent.ACTION_DOWN:
                // Intercepts touch events
                if (mMainHud.consumeEvent(event)) {
                    return;
                }
                // Switch Mode to SCROLL
                mTouchMode = TouchMode.SCROLL;
                break;

            // Last pointer up
            case MotionEvent.ACTION_UP:
                // Switch Mode to None
                mTouchMode = TouchMode.NONE;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                // Switch Mode to Zoom
                mTouchMode = TouchMode.ZOOM;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                // TODO if (zoom) => switch to drag
                if (mTouchMode.equals(TouchMode.ZOOM) && event.getPointerCount() == 1) {
                    mTouchMode = TouchMode.SCROLL;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                // TODO Do the job here !
                // if drag_mode => Do drag
                // if zoom => do zoom
                break;

            default:
        }

        // mScaleDetector.onTouchEvent may throw an exception (historyPos out of range)
        try {
            mScaleDetector.onTouchEvent(event);
        } catch (IllegalArgumentException e) {
            if (BuildConfig.DEBUG) {
                Log.e("TowerDefenseThread", "Crash while calling mScaleDetector.onTouchEvent(event)", e);
            }
        }

        if (mTouchMode.equals(TouchMode.SCROLL)) {
            mScrollDetector.onTouchEvent(event);
        }

    }

    @Override
    protected void processEvent(KeyEvent event) {
        // TODO handle keyboard
    }

    @Override
    protected void doDraw(Canvas canvas) {

        // Draw game elements: battleground, towers, enemies, ...
        mGameMgr.draw(canvas);

        // Draw the main Head-up display:Scores, GUI, ...
        mMainHud.draw(canvas, null);
    }

    private class ScrollListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent initialEvent, MotionEvent currentEvent, float distanceX, float distanceY) {

            mGameMgr.updateOffset(distanceX * -1f, distanceY * -1f);
            return true;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        private float lastSpanX;
        private float lastSpanY;

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            lastSpanX = detector.getCurrentSpanX();
            lastSpanY = detector.getCurrentSpanY();
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(1f, Math.min(mScaleFactor, 5.0f));
            mGameMgr.updateScaleFactor(mScaleFactor);
            return true;
        }
    }

}