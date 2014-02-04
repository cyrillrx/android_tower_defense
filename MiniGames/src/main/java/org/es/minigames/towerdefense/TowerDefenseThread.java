package org.es.minigames.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import org.es.gameengine.DrawingThread;
import org.es.gameengine.UserEvent;
import org.es.minigames.towerdefense.drawable.tower.Tower;
import org.es.minigames.towerdefense.drawable.tower.TowerFactory;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class TowerDefenseThread extends DrawingThread {

    Tower[] mTowers = new Tower[1];

    public TowerDefenseThread(SurfaceHolder surfaceHolder, Context context) {
        super(surfaceHolder, context);

        mTowers[0] = TowerFactory.createBasicTower(context.getResources());

    }

    @Override
    protected void updateSurfaceSize(int surfaceWidth, int surfaceHeight) {

    }

    @Override
    protected boolean update() {
        return false;
    }

    @Override
    protected void processEvent(UserEvent event) {

    }

    @Override
    protected void doDraw(Canvas canvas) {

        for (Tower tower : mTowers) {
            tower.draw(canvas);
        }
    }
}
