package org.es.towerdefense.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import org.es.engine.game_mechanic.DrawingThread;
import org.es.engine.game_mechanic.DrawingView;

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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        mThread.addInputEvent(event);
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        mThread.addInputEvent(event);
        return true;
    }
}
