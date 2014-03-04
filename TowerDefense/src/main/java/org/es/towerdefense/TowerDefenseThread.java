package org.es.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
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
        NONE, DRAG, ZOOM
    }

    private final Player mPlayer;
    private final HUD mMainHud;
    private final GameMgr mGameMgr;

    private TouchMode mTouchMode;

    public TowerDefenseThread(SurfaceHolder surfaceHolder, Context context) {
        super(surfaceHolder, context);

        mPlayer = new Player(20, 1000);
        mGameMgr = new GameMgr(mPlayer, context);
        mMainHud = new HUD(mPlayer, mGameMgr, context.getResources());
        mTouchMode = TouchMode.NONE;
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

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                if (mMainHud.consumeEvent(event)) {
                    return;
                }

                // Switch Mode to Drag
                mTouchMode = TouchMode.DRAG;
                break;

            case MotionEvent.ACTION_MOVE:
                // if drag_mode => Do drag
                // if zoom => do zoom
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                // Switch Mode to Zoom
                mTouchMode = TouchMode.ZOOM;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                // TODO if (zoom) => switch to drag
                break;

            case MotionEvent.ACTION_UP:
                // Switch Mode to None
                mTouchMode = TouchMode.NONE;
                break;
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
}
