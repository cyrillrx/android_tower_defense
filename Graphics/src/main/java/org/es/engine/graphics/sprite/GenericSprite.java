package org.es.engine.graphics.sprite;

import android.graphics.Canvas;
import android.graphics.PointF;

import org.es.engine.graphics.animation.Animation;

import java.util.EnumMap;

/**
 * A sprite is a simple animated element.<br />
 * Plays a bitmap list in loop.
 *
 * @author Cyril Leroux
 *         Created on 25/09/13.
 */
public class GenericSprite<AnimationId extends Enum<AnimationId>> implements Sprite<AnimationId> {

    protected EnumMap<AnimationId, Animation> mAnimations;
    /** The current animation state. */
    protected AnimationId mAnimationId = null;
    protected PointF mPosition;

    private int mOldSurfaceW = 0;
    private int mOldSurfaceH = 0;

    public GenericSprite(EnumMap<AnimationId, Animation> animations, AnimationId startAnimationId) {
        mAnimations = animations;
        mAnimationId = startAnimationId;
        mPosition = new PointF();
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {

        if (mOldSurfaceW != 0 && mOldSurfaceH != 0) {
            // Get old position
            final float oldCenterX = mPosition.x + getWidth() / 2;
            final float oldCenterY = mPosition.y + getHeight() / 2;

            // Set new position
            mPosition.x = oldCenterX / mOldSurfaceW * surfaceWidth  - getWidth()  / 2;
            mPosition.y = oldCenterY / mOldSurfaceH * surfaceHeight - getHeight() / 2;
        }

        mOldSurfaceW = surfaceWidth;
        mOldSurfaceH = surfaceHeight;
    }

    @Override
    public void draw(Canvas canvas) { getAnimation().draw(canvas, mPosition); }

    @Override
    public float getPosX() { return mPosition.x; }

    @Override
    public float getPosY() { return mPosition.y; }

    @Override
    public void setPosition(float x, float y) { mPosition.set(x, y); }

    public float getWidth() { return getAnimation().getWidth(); }

    public float getHeight() { return getAnimation().getHeight(); }

    @Override
    public void startAnimation() { getAnimation().start(); }

    @Override
    public void stopAnimation() { getAnimation().stop(); }

    @Override
    public void updateAnimation() { getAnimation().updateFrame(); }

    @Override
    public AnimationId getAnimationId() { return mAnimationId; }

    @Override
    public void setAnimationId(AnimationId animationId) { mAnimationId = animationId; }

    @Override
    public Animation getAnimation() { return getAnimation(mAnimationId); }

    @Override
    public Animation getAnimation(AnimationId animationId) { return mAnimations.get(animationId); }
}
