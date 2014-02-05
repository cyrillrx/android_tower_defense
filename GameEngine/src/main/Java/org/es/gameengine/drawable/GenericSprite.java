package org.es.gameengine.drawable;

import android.graphics.Canvas;
import android.graphics.PointF;

import java.util.Map;

/**
 * A sprite is a simple animated element.<br />
 * Plays a bitmap list in loop.<br />
 * <br />
 * Created by Cyril on 25/09/13.
 */
public class GenericSprite implements Sprite {

    protected Map<Integer, Animation> mAnimations;
    /** The current animation id. */
    protected int mCurrentAnimationId = 0;
    protected PointF mPosition;

    public GenericSprite(Map<Integer, Animation> animations) {
        mAnimations = animations;
        mPosition = new PointF();
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        mPosition.x = surfaceWidth / 2 - getWidth() / 2;
        mPosition.y = surfaceHeight - getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        getAnimation().draw(canvas, mPosition);
    }

    @Override
    public void startAnimation() {
        getAnimation().start();
    }

    @Override
    public void stopAnimation() {
        getAnimation().stop();
    }

    @Override
    public int getAnimationId() { return mCurrentAnimationId; }

    @Override
    public void setAnimationId(final int id) { mCurrentAnimationId = id; }

    @Override
    public Animation getAnimation() { return mAnimations.get(mCurrentAnimationId); }

    public int getWidth() { return getAnimation().getWidth(); }

    public int getHeight() { return getAnimation().getHeight(); }

    public float getLeft() { return mPosition.x; }

    public float getTop() { return mPosition.y; }

    public float getRight() { return mPosition.x + getWidth(); }

    public float getBottom() { return mPosition.y + getHeight(); }

    public void moveX(int value) { mPosition.x += value; }

    public void moveY(int value) { mPosition.y += value; }
}
