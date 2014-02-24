package org.es.minigames.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import org.es.engine.gamemechanic.DrawingThread;
import org.es.engine.gamemechanic.UserEvent;
import org.es.minigames.towerdefense.process.GameMgr;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class TowerDefenseThread extends DrawingThread {

    private final GameMgr mGameMgr;

    public TowerDefenseThread(SurfaceHolder surfaceHolder, Context context) {
        super(surfaceHolder, context);
        mGameMgr = new GameMgr(context);
    }

    @Override
    protected void updateSurfaceSize(int surfaceWidth, int surfaceHeight) {
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
    protected void processEvent(UserEvent event) {

        final int keyCode = event.getKeyCode();
        final int action = event.getAction();

        if (keyCode == UserEvent.KEYCODE_TOUCH && action == UserEvent.ACTION_DOWN) {
            // Toggle pause
            if (mGameMgr.isPaused()) {
                mGameMgr.resume();
            } else {
                mGameMgr.pause();
            }

        }
    }

    @Override
    protected void doDraw(Canvas canvas) {
        mGameMgr.draw(canvas);
    }
}
