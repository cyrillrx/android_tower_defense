package org.es.minigames.towerdefense.unit;

import android.graphics.Canvas;

import org.es.gameengine.drawable.Animation;
import org.es.gameengine.drawable.DrawableElement;
import org.es.gameengine.drawable.Sprite;

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
    /** Attack range of the unit. */
    protected int mAttackRange;
    /** Delay in milliseconds between two attacks. */
    protected int mAttackDelay;
    /** Timestamp of the last attack in milliseconds. */
    protected int mLastAttack;
    /** Weighting of the unit. Used to calculate the unit cost. */
    protected int mWeight;

    protected AbstractUnit(Sprite<AnimationId> sprite,  int health, int damage, int attackRange, int attackDelay, int weight) {
        mSprite = sprite;
        mHealth = health;
        mDamage = damage;
        mAttackRange = attackRange;
        mAttackDelay = attackDelay;
        mWeight = weight;
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
    public void setPosition(float x, float y) { mSprite.setPosition(x, y); }

    @Override
    public void moveX(int value) { mSprite.moveX(value); }

    @Override
    public void moveY(int value) { mSprite.moveY(value); }
}
