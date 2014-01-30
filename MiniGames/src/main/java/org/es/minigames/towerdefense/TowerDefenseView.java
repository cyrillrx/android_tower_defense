package org.es.minigames.towerdefense;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import org.es.gameengine.DrawingThread;
import org.es.gameengine.DrawingView;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class TowerDefenseView extends DrawingView {

    public TowerDefenseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected DrawingThread createDrawingThread(SurfaceHolder holder, Context context) {
        return new TowerDefenseThread(holder, context);
    }
}
