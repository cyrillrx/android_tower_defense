package org.es.gameengine.drawable;

import android.graphics.Canvas;
import android.graphics.Point;

import org.es.gameengine.AnimationCallback;

/**
 * Simple animated element.
 * Plays a bitmap list in loop.
 * <p/>
 * Created by Cyril on 25/09/13.
 */
public class AnimatedElement implements GameElement, AnimationCallback {

    protected Animation mAnimation;
    protected Point mPosition;

    public AnimatedElement(Animation animation) {
        mAnimation = animation;
        mPosition = new Point();
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        mPosition.x = surfaceWidth / 2 - getWidth() / 2;
        mPosition.y = surfaceHeight - getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        if (mAnimation != null) {
            mAnimation.draw(canvas, mPosition);
        }
    }

    public void startAnimation() {
        mAnimation.start();
    }

    public void stopAnimation() {
        mAnimation.stop();
    }

    @Override
    public void onAnimationStopped() {

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
