package org.es.minigames.common.drawelement;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Simple loop animated element.
 * Plays a bitmap list in loop.
 *
 * Created by Cyril on 25/09/13.
 */
public class AnimatedElement implements GameElement {

    Animation mAnimation;

    private Point mPosition;

    /**
     * @param animationDuration Animation duration in milliseconds.
     */
    public AnimatedElement(Resources resources, int[] resIds, double animationDuration) {

        mAnimation = new Animation(resources, resIds, animationDuration);
        mPosition = new Point();
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        mPosition.x = surfaceWidth / 2 - getWidth() / 2;
        mPosition.y = surfaceHeight - getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        mAnimation.draw(canvas, mPosition);
    }

    public void startAnimation() {
        mAnimation.start();
    }

    public void stopAnimation() {
        mAnimation.stop();
    }

    public int getWidth() { return mAnimation.getWidth(); }

    public int getHeight() { return mAnimation.getHeight(); }

    public int getLeft() { return mPosition.x; }

    public int getTop() { return mPosition.y; }

    public int getRight() { return mPosition.x + mAnimation.getWidth(); }

    public int getBottom() { return mPosition.y + mAnimation.getHeight(); }

    public void moveX(int value) { mPosition.x += value; }

    public void moveY(int value) { mPosition.y += value; }
}
