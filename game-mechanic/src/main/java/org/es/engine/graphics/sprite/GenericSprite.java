package org.es.engine.graphics.sprite;

import android.graphics.Canvas;
import android.graphics.RectF;

import org.es.engine.graphics.animation.Animation;
import org.es.engine.graphics.utils.DrawingParam;

import java.util.EnumMap;

/**
 * A sprite is a simple animated element.<br />
 * Plays a bitmap list in loop.
 *
 * @author Cyril Leroux
 *         Created on 25/09/13.
 */
public class GenericSprite<AnimationId extends Enum<AnimationId>> implements Sprite<AnimationId> {

    protected final EnumMap<AnimationId, Animation> mAnimations;
    /** The current animation state. */
    protected AnimationId mAnimationId = null;
    protected RectF mBoundingRect;

    private int mOldSurfaceW = 0;
    private int mOldSurfaceH = 0;

    /**
     * Sprite constructor.
     *
     * @param animations
     * @param startAnimationId
     */
    public GenericSprite(EnumMap<AnimationId, Animation> animations, AnimationId startAnimationId) {
        mAnimations = animations;
        mAnimationId = startAnimationId;
        mBoundingRect = new RectF();
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {
//
//        if (mOldSurfaceW != 0 && mOldSurfaceH != 0) {
//            // Get old position
//            final float oldCenterX = mPosition.x + getWidth() / 2;
//            final float oldCenterY = mPosition.y + getHeight() / 2;
//
//            // Set new position
//            mPosition.x = oldCenterX / mOldSurfaceW * surfaceWidth  - getWidth()  / 2;
//            mPosition.y = oldCenterY / mOldSurfaceH * surfaceHeight - getHeight() / 2;
//        }
//
//        mOldSurfaceW = surfaceWidth;
//        mOldSurfaceH = surfaceHeight;
    }

    /**
     * The sprite is already resized. No need to apply drawingParam.
     *
     * @param canvas
     * @param drawingParam
     */
    @Override
    public void draw(Canvas canvas, DrawingParam drawingParam) {
        getAnimation().draw(canvas, mBoundingRect);
    }

    /** Set the destination size of the bitmap to draw. */
    public void setDimensions(float left, float top, float right, float bottom) {
        mBoundingRect.set(left, top, right, bottom);
    }

    @Override
    public float getPosX() { return mBoundingRect.left; }

    @Override
    public float getPosY() { return mBoundingRect.top; }

    @Override
    public void setPosition(float x, float y) {
        mBoundingRect.set(x, y, x + mBoundingRect.width(), y + mBoundingRect.height());
    }

    @Override
    public void offsetPosition(float dx, float dy) {
        mBoundingRect.offset(dx, dy);
    }

    @Override
    public float getWidth() { return mBoundingRect.width(); }

    @Override
    public float getHeight() { return mBoundingRect.height(); }

    @Override
    public void startAnimation() { getAnimation().start(); }

    @Override
    public void stopAnimation() { getAnimation().stop(); }

    @Override
    public boolean updateAnimationFrame() { return getAnimation().updateFrame(); }

    @Override
    public AnimationId getAnimationId() { return mAnimationId; }

    @Override
    public void setAnimationId(AnimationId animationId) { mAnimationId = animationId; }

    @Override
    public Animation getAnimation() { return getAnimation(mAnimationId); }

    @Override
    public Animation getAnimation(AnimationId animationId) { return mAnimations.get(animationId); }
}
