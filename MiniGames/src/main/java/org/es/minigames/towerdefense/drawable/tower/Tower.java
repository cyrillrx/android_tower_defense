package org.es.minigames.towerdefense.drawable.tower;

import android.graphics.Canvas;

import org.es.minigames.towerdefense.drawable.AbstractUnit;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Tower extends AbstractUnit {

    // Tower states
    private static final int STATE_STATIC   = 0;
    private static final int STATE_MOVING   = 1;

    private static final int DIRECTION_LEFT     = 0;
    private static final int DIRECTION_RIGHT    = 1;
    private static final int DIRECTION_UP       = 2;
    private static final int DIRECTION_DOWN     = 3;

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {

    }

    @Override
    public void draw(Canvas canvas) {

    }
}
