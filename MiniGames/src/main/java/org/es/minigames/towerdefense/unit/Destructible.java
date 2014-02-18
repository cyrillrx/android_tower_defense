package org.es.minigames.towerdefense.unit;

import android.graphics.Canvas;
import android.graphics.PointF;

import org.es.engine.graphics.animation.Animation;
import org.es.engine.graphics.sprite.Sprite;
import org.es.minigames.utils.DrawingParam;

/**
 * Parent class for all destructible elements.
 *
 * @author Cyril Leroux
 *         Created on 17/02/14.
 */
public class Destructible<AnimationId extends Enum<AnimationId>> implements Sprite<AnimationId> {

    /**
     * The sprite is the element drawn on the screen.<br />
     * Its position is not the same as the holder for it depends on the drawing parameters (coefficient and offset).
     */
    private final Sprite<AnimationId> mSprite;
    /** The parameters used to draw the elements on the screen. */
    private final DrawingParam mDrawingParam;
    /** Position on the battleground. Expressed in tiles. */
    private final PointF mPosition;
    /** Width of the destructible element. Expressed in tiles. */
    private final float mWidth;
    /** Height of the destructible element. Expressed in tiles. */
    private final float mHeight;
    /** Weighting of the unit. Used to calculate the unit cost or reward. */
    protected final int mWeight;

    public Destructible(Sprite<AnimationId> sprite, float width, float height, int weight) {
        mSprite = sprite;
        mWidth  = width;
        mHeight = height;
        mWeight = weight;

        mPosition = new PointF();
        mDrawingParam = new DrawingParam();
    }

    //
    // New functions
    //

    public float getCoef() { return mDrawingParam.getCoef(); }

    // TODO add comment
    public void setCoef(float coef) {
        mDrawingParam.setCoef(coef);
        // handle zoom and offset
        updateSpritePosition();
        updateSpriteDimensions();
    }

    public void setOffset(float offsetX, float offsetY) {
        mDrawingParam.setOffset(offsetX, offsetY);
    }

    /** Draw the Head-up display. */
    public void drawHUD(Canvas canvas) { }

    /** @return The centerX position on the grid. */
    public float getCenterX() { return getPosX() + getWidth() / 2f; }

    /** @return The centerY position on the grid. */
    public float getCenterY() { return getPosY() + getHeight() / 2f; }

    /** Update zoom and position of the sprite. */
    private void updateSpritePosition() {
        // handle zoom and offset
        mSprite.setPosition(
                mPosition.x * mDrawingParam.getCoef() + mDrawingParam.getOffset().x,
                mPosition.y * mDrawingParam.getCoef() + mDrawingParam.getOffset().y
        );
    }

    /** Update sprite dimensions. */
    private void updateSpriteDimensions() {
        mSprite.getAnimation().setBounds(mWidth * mDrawingParam.getCoef(), mHeight * mDrawingParam.getCoef());
    }

    //
    // Drawable functions
    //

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        mSprite.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
    }

    @Override
    public void draw(Canvas canvas) { mSprite.draw(canvas); }

    @Override
    public float getPosX() { return mPosition.x; }

    @Override
    public float getPosY() { return mPosition.y; }

    @Override
    public void setPosition(float x, float y) {
        mPosition.set(x, y);
        updateSpritePosition();
    }

    @Override
    public float getWidth() { return mWidth; }

    @Override
    public float getHeight() { return mHeight; }

    //
    // Sprite functions
    //

    @Override
    public void startAnimation() { mSprite.startAnimation(); }

    @Override
    public void stopAnimation() { mSprite.stopAnimation(); }

    @Override
    public void updateAnimation() { mSprite.updateAnimation(); }

    @Override
    public AnimationId getAnimationId() { return mSprite.getAnimationId(); }

    @Override
    public void setAnimationId(AnimationId animationId) {
        mSprite.setAnimationId(animationId);
        updateSpritePosition();
        updateSpriteDimensions();
    }

    @Override
    public Animation getAnimation() { return mSprite.getAnimation(); }

    @Override
    public Animation getAnimation(AnimationId animationId) { return mSprite.getAnimation(animationId); }
}
