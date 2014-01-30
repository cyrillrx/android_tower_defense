package org.es.minigames.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import org.es.gameengine.DrawingThread;
import org.es.gameengine.UserEvent;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class TowerDefenseThread extends DrawingThread {

    public TowerDefenseThread(SurfaceHolder surfaceHolder, Context context) {
        super(surfaceHolder, context);
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

    }
}
