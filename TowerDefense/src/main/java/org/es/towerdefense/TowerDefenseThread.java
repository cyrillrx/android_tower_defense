package org.es.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;

import org.es.engine.gamemechanic.DrawingThread;
import org.es.towerdefense.component.HUD;
import org.es.towerdefense.component.HudHelper;
import org.es.towerdefense.component.HudText;
import org.es.towerdefense.object.Player;
import org.es.towerdefense.process.GameMgr;
import org.es.utils.DrawTextUtils;

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

    private float mLastTouchX = Float.MIN_VALUE;
    private float mLastTouchY = Float.MIN_VALUE;

    private float mScaleFactor = 1.f;

    private TouchMode mTouchMode;
    private String mTouchAction = "Touch the screen";

    public TowerDefenseThread(SurfaceHolder surfaceHolder, Context context) {
        super(surfaceHolder, context);

        mPlayer = new Player(20, 1000);
        mGameMgr = new GameMgr(mPlayer, context);
        mMainHud = new HUD(mGameMgr, context.getResources());
        HudHelper.initMainHud(mMainHud, mPlayer, mGameMgr, context.getResources());

        ////////////////////////////////////////////////////////
        // TODO get this bloc of code out of here
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(30f);

        HudText touchState = new HudText("", 0.98f, 0.98f,
                DrawTextUtils.HorizontalAlign.LEFT, DrawTextUtils.VerticalAlign.TOP, paint) {
            @Override
            public String getText() {
                return mTouchMode.name();
            }
        };
        mMainHud.addControl(touchState);

        HudText touchAction = new HudText("", 0.5f, 0.98f,
                DrawTextUtils.HorizontalAlign.CENTER, DrawTextUtils.VerticalAlign.TOP, paint) {
            @Override
            public String getText() {
                return mTouchAction;
            }
        };
        mMainHud.addControl(touchAction);
        ////////////////////////////////////////////////////////

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

        final int action = event.getActionMasked();
        final int id = event.getActionIndex();
        mTouchAction = "processEvent => id:" + id + " act:" + action;

        if (BuildConfig.DEBUG) {
            Log.d("TowerDefenseThread", "processEvent => id:" + id + " act:" + action);
        }

        switch (action) {

            // First pointer down
            case MotionEvent.ACTION_DOWN:
                // Intercepts touch events
                if (mMainHud.consumeEvent(event)) {
                    return;
                }
                // Switch Mode to SCROLL
                onScrollBegin(event.getX(), event.getY());
                break;

            // Last pointer up
            case MotionEvent.ACTION_UP:
                // Switch Mode to None
                onScrollEnd();
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                // Switch Mode to Zoom
                mTouchMode = TouchMode.ZOOM;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                // TODO if (zoom) => switch to drag
                if (mTouchMode.equals(TouchMode.ZOOM) && event.getPointerCount() == 2) {
                    onScrollBegin(event.getX(), event.getY());
                }
                break;

            case MotionEvent.ACTION_MOVE:
                // TODO Do the job here !
                if (mTouchMode.equals(TouchMode.SCROLL)){
                    onScroll(event);
                }
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

        final int action = event.getAction();
        if (BuildConfig.DEBUG) {
            Log.d("TowerDefenseThread", "processEvent(KeyEvent) : " + action);
        }
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

            //            mGameMgr.updateOffset(distanceX * -1f, distanceY * -1f);
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

    private void onScrollBegin(float x, float y) {
        mTouchMode = TouchMode.SCROLL;
        mLastTouchX = x;
        mLastTouchY = y;
    }

    private void onScroll(MotionEvent  event) {

        if (mLastTouchX != Float.MIN_VALUE && mLastTouchY != Float.MIN_VALUE) {
            float dx = event.getX() - mLastTouchX;
            float dy = event.getY() - mLastTouchY;
            mGameMgr.updateOffset(dx, dy);
        }
        mLastTouchX = event.getX();
        mLastTouchY = event.getY();

    }

    private void onScrollEnd() {
        mTouchMode = TouchMode.NONE;
        mLastTouchX = Float.MIN_VALUE;
        mLastTouchY = Float.MIN_VALUE;
    }
}