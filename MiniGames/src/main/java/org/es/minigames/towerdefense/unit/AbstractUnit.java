package org.es.minigames.towerdefense.unit;

import org.es.engine.graphics.sprite.Sprite;

/**
 * This class represents a fighting unit.
 *
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public abstract class AbstractUnit<AnimationId extends Enum<AnimationId>> extends Destructible<AnimationId> {

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

    /** The rotation angle of the unit in degrees. */
    protected double mRotationAngle;

    protected AbstractUnit(Sprite<AnimationId> sprite, float width, float height, int weight,
                           int health, int damage, int attackRange, int attackDelay) {
        super(sprite, width, height, weight);
        mHealth = health;
        mDamage = damage;
        mAttackRange = attackRange;
        mAttackDelay = attackDelay;
    }
}
