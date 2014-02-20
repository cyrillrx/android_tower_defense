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
        mGameMgr.update();
        return false;
    }

    @Override
    protected void processEvent(UserEvent event) {

    }

    @Override
    protected void doDraw(Canvas canvas) {
        mGameMgr.draw(canvas);
    }
}
