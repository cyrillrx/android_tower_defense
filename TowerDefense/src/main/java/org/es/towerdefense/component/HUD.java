package org.es.towerdefense.component;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import org.es.engine.graphics.drawable.DrawableElement;
import org.es.engine.graphics.utils.DrawingParam;
import org.es.towerdefense.R;
import org.es.towerdefense.object.Player;
import org.es.towerdefense.process.GameMgr;
import org.es.utils.DrawTextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril Leroux
 *         Created 02/03/14.
 */
public class HUD implements DrawableElement {

    private final GameMgr mGameMgr;

    private final HudButton mPause;
    private final HudButton mPlay;

    private final List<Control> mControls;

    // TODO init with a resource manager
    public HUD(GameMgr gameMgr, Resources resources) {

        mControls = new ArrayList<>();
        mGameMgr = gameMgr;

        mPlay = new HudButton(resources, R.drawable.ic_play, R.drawable.ic_play, new RectF());
        mPause = new HudButton(resources, R.drawable.ic_pause, R.drawable.ic_pause, new RectF());

    }

    public void addControl(Control control) {
        mControls.add(control);
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {

        final float margin = surfaceHeight * 0.02f;

        RectF playPauseBounds = new RectF(0, 0, surfaceHeight * 0.1f, surfaceHeight * 0.1f);
        playPauseBounds.offset(surfaceWidth - playPauseBounds.width() - margin, margin);
        mPlay.setBounds(playPauseBounds);
        mPause.setBounds(playPauseBounds);
    }

    @Override
    public void draw(Canvas canvas, DrawingParam param) {

        if (mGameMgr.isPaused()) {
            mPlay.draw(canvas);
        } else {
            mPause.draw(canvas);
        }

        for (Control control : mControls) {
            control.draw(canvas);
        }
    }

    /**
     * Intercepts event sent by the drawing thread.
     * Returns true if the event is consumed. and false otherwise.
     * @param event the event to intercepts.
     * @return true if the event is consumed. False otherwise.
     */
    public boolean consumeEvent(MotionEvent event) {

        if (mPause.getBounds().contains(event.getX(), event.getY())) {
            // Toggle pause
            if (mGameMgr.isPaused()) {
                mGameMgr.resume();
            } else {
                mGameMgr.pause();
            }
            return true;
        }
        return false;
    }

    @Override
    public float getPosX() { return 0; }

    @Override
    public float getPosY() { return 0; }

    @Override
    public void setPosition(float x, float y) { }

    @Override
    public void offsetPosition(float dx, float dy) { }

    @Override
    public float getWidth() { return 0; }

    @Override
    public float getHeight() { return 0; }
}
