package org.es.gameengine.drawable;

import android.graphics.Canvas;
import android.graphics.PointF;

import java.util.EnumMap;

/**
 * A sprite is a simple animated element.<br />
 * Plays a bitmap list in loop.<br />
 * <br />
 * Created by Cyril on 25/09/13.
 */
public class GenericSprite<AnimationId extends Enum<AnimationId>> implements Sprite<AnimationId> {

    protected EnumMap<AnimationId, Animation> mAnimations;
    /** The current animation state. */
    protected AnimationId mAnimationId = null;
    protected PointF mPosition;

    public GenericSprite(EnumMap<AnimationId, Animation> animations, AnimationId startAnimationId) {
        mAnimations = animations;
        mAnimationId = startAnimationId;
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
    public AnimationId getAnimationId() { return mAnimationId; }

    @Override
    public void setAnimationId(AnimationId animationId) { mAnimationId = animationId; }

    @Override
    public Animation getAnimation() { return getAnimation(mAnimationId); }

    @Override
    public Animation getAnimation(AnimationId animationId) { return mAnimations.get(animationId); }

    public int getWidth() { return getAnimation().getWidth(); }

    public int getHeight() { return getAnimation().getHeight(); }

    public float getLeft() { return mPosition.x; }

    public float getTop() { return mPosition.y; }

    public float getRight() { return mPosition.x + getWidth(); }

    public float getBottom() { return mPosition.y + getHeight(); }

    public void moveX(int value) { mPosition.x += value; }

    public void moveY(int value) { mPosition.y += value; }
}
