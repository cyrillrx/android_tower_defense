package org.es.minigames.towerdefense;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import org.es.engine.gamemechanic.DrawingThread;
import org.es.engine.gamemechanic.DrawingView;
import org.es.engine.gamemechanic.UserEvent;

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

        final int action = event.getActionMasked();
        switch (action) {

            case MotionEvent.ACTION_DOWN:
                mThread.addUserEvent(new UserEvent(UserEvent.ACTION_DOWN, event.getY(), event.getY()));

            case MotionEvent.ACTION_MOVE:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
