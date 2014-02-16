package org.es.minigames.towerdefense.unit;

import android.graphics.Canvas;
import android.graphics.PointF;

import org.es.engine.graphics.animation.Animation;
import org.es.engine.graphics.drawable.DrawableElement;
import org.es.engine.graphics.sprite.Sprite;
import org.es.minigames.utils.DrawingInfo;

/**
 * This class represents a fighting unit.
 *
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public abstract class AbstractUnit<AnimationId extends Enum<AnimationId>> implements Sprite<AnimationId>, DrawableElement {

    protected final Sprite<AnimationId> mSprite;

    /** Health points of the unit. */
    protected int mHealth;
    /** Damage caused by each attack of the unit. */
    protected int mDamage;
    /** Attack range of the unit. Expressed in tiles. */
    protected int mAttackRange;
    /** Delay in milliseconds between two attacks. */
    protected int mAttackDelay;
    /** Timestamp of the last attack in milliseconds. */
    protected int mLastAttack;
    /** Weighting of the unit. Used to calculate the unit cost. */
    protected int mWeight;

    /** Position on the battleground. Expressed in tiles. */
    protected final PointF mPosition;
    /** The rotation angle of the unit in degrees. */
    protected double mRotationAngle;

    protected final DrawingInfo mDrawInfo;

    protected AbstractUnit(Sprite<AnimationId> sprite,  int health, int damage, int attackRange, int attackDelay, int weight) {
        mSprite = sprite;
        mHealth = health;
        mDamage = damage;
        mAttackRange = attackRange;
        mAttackDelay = attackDelay;
        mWeight = weight;
        mPosition = new PointF();
        mDrawInfo = new DrawingInfo();
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        mSprite.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
    }

    @Override
    public void draw(Canvas canvas) { mSprite.draw(canvas); }

    @Override
    public void startAnimation() { mSprite.startAnimation(); }

    @Override
    public void stopAnimation() { mSprite.stopAnimation(); }

    @Override
    public void updateAnimation() { mSprite.updateAnimation(); }

    @Override
    public AnimationId getAnimationId() { return mSprite.getAnimationId(); }

    @Override
    public void setAnimationId(AnimationId animationId) { mSprite.setAnimationId(animationId); }

    @Override
    public Animation getAnimation() { return mSprite.getAnimation(); }

    @Override
    public Animation getAnimation(AnimationId animationId) { return mSprite.getAnimation(animationId); }

    @Override
    public PointF getPosition() {
        // TODO handle zoom and offset
        return mPosition;
    }

    @Override
    public void setPosition(float x, float y) {
        mPosition.set(x, y);
        // TODO handle zoom and offset
        mSprite.setPosition(mPosition.x * mDrawInfo.getTileSize(), mPosition.y * mDrawInfo.getTileSize());
    }

    /** Add value to the abscissa of the sprite. */
    public void moveX(float value, float coef) {
        mPosition.x += value;
        // TODO handle zoom and offset
        mSprite.setPosition(mPosition.x * coef, mPosition.y * coef);
    }

    /** Add value to the ordinate of the sprite. */
    public void moveY(float value, float coef) {
        mPosition.y += value;
        // TODO handle zoom and offset
        mSprite.setPosition(mPosition.x * coef, mPosition.y * coef);
    }

    // TODO work on Drawing data
    public void setCoef(float coef) {
        mDrawInfo.setTileSize(coef);
    }
}
