package org.es.minigames.towerdefense.unit;

import org.es.engine.graphics.sprite.Sprite;
import org.es.minigames.utils.PositionUtils;

/**
 * This class represents an element with offensive capabilities.
 *
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public abstract class Offensive<AnimationId extends Enum<AnimationId>> extends Destructible<AnimationId> {

    /** Damage caused by each attack of the unit. */
    protected int mDamage;
    /** Attack range of the unit. Expressed in tiles. */
    protected float mAttackRange;
    /** Delay in milliseconds between two attacks. */
    protected long mAttackDelay;
    /** Timestamp of the last attack in milliseconds. */
    protected long mLastAttack;

    /** The rotation angle of the unit in degrees. */
    protected int mRotationAngle;

    /**
     * The angle of the last update.<br />
     * Used to optimize the update of rotation animation.
     */
    private double mLastAngle;

    protected Offensive(Sprite<AnimationId> sprite, float width, float height, int weight, int health,
                        int damage, float attackRange, long attackDelay) {
        super(sprite, width, height, weight, health);
        mDamage = damage;
        mAttackRange = attackRange;
        mAttackDelay = attackDelay;
    }

    /**
     * Turn towards the point in parameter.
     *
     * @param posX The abscissa of the point towards which to turn to.
     * @param posY The ordinate of the point towards which to turn to.
     */
    protected void turnTowards(float posX, float posY) {
        mRotationAngle = (int) PositionUtils.angleInDegrees(getCenterX(), getCenterY(), posX, posY, true);
    }

    @Override
    public void updateAnimation() {
        updateRotationAnimation();
        super.updateAnimation();
    }

    /**
     * Check if the rotation angle has changed.
     * If so, try to update the animation.
     */
    private void updateRotationAnimation() {
        if (mRotationAngle == mLastAngle) { return; }
        doUpdateRotationAnimation();
        mLastAngle = mRotationAngle;
    }

    /** Changes the animation after a rotation. */
    protected abstract void doUpdateRotationAnimation();


    /**
     * Indicates whether the current rotation angle is in a range.
     *
     * @param bisectorAngle The bisector of the range.
     * @param range the range in degrees.
     * @return True if the angle is in the specified range. False otherwise.
     */
    protected boolean angleInRange(double bisectorAngle, double range) {
        return PositionUtils.angleInRange(mRotationAngle, bisectorAngle, range);
    }
}
