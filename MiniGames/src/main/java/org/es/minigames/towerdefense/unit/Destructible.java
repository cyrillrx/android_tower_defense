package org.es.minigames.towerdefense.unit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

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
    /** Health points of the unit. */
    protected int mHealth;

    private boolean mDead;

    public Destructible(Sprite<AnimationId> sprite, float width, float height, int weight, int health) {
        mSprite = sprite;
        mWidth  = width;
        mHeight = height;
        mWeight = weight;
        mHealth = health;
        mDead = false;

        mPosition = new PointF();
        mDrawingParam = new DrawingParam();
    }

    //
    // New functions
    //

    public void receiveDamages(Offensive attacker) {
        mHealth -= attacker.mDamage;
        mDead = mHealth < 0;
    }

    public boolean isDead() { return mDead; }

    // TODO comment
    // 
    public boolean isOutOfPlay() {
        return isDead();
    }

    // TODO comment
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

    /** Draw the debug Head-up display. */
    public void drawDebugHUD(Canvas canvas, Paint paint) {

        // Save and change paint color.
        int initialColor = paint.getColor();
        paint.setColor(Color.RED);

        // Text to display the remaining life of the element.
        final String hpText = String.valueOf(mHealth) + " HP";
        // Get text bounds
        final Rect textBounds = new Rect();
        paint.getTextBounds(hpText, 0, hpText.length(), textBounds);

        // Draw the text centered above the element.
        canvas.drawText(hpText,
                getCenterX() * getCoef() - textBounds.width() / 2f,
                getPosY() * getCoef() - textBounds.height(),
                paint);

        // restore paint color.
        paint.setColor(initialColor);
    }

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

    /**
     * Set the current animation id.<br />
     * Does nothing if the passed animation id is the same as the current one.
     * @param animationId The new animation Id
     */
    @Override
    public void setAnimationId(AnimationId animationId) {
        if (animationId == mSprite.getAnimationId()) {
            return;
        }

        mSprite.setAnimationId(animationId);
        updateSpritePosition();
        updateSpriteDimensions();
    }

    @Override
    public Animation getAnimation() { return mSprite.getAnimation(); }

    @Override
    public Animation getAnimation(AnimationId animationId) { return mSprite.getAnimation(animationId); }
}
