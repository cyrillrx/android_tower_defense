package org.es.engine.hud;

import android.graphics.Canvas;
import android.view.MotionEvent;

import org.es.engine.graphics.drawable.DrawableElement;
import org.es.engine.graphics.utils.DrawingParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril Leroux
 *         Created 02/03/14.
 */
public class HUD implements DrawableElement {

    private final List<Control> mControls;

    public HUD() {
        mControls = new ArrayList<>();
    }

    public void addControl(Control control) {
        mControls.add(control);
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) { }

    @Override
    public void draw(Canvas canvas, DrawingParam param) {

        for (Control control : mControls) {
            control.draw(canvas);
        }
    }

    /**
     * Intercepts event sent by the drawing thread.
     * Returns true if the event is consumed. and false otherwise.
     *
     * @param event the event to intercepts.
     * @return true if the event is consumed. False otherwise.
     */
    public boolean consumeEvent(MotionEvent event) {

        for (Control control : mControls) {
            if (control.consumeEvent(event)) {
                return true;
            }
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
