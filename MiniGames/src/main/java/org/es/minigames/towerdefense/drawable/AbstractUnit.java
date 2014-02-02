package org.es.minigames.towerdefense.drawable;

import android.graphics.PointF;

import org.es.gameengine.drawable.DrawableElement;

/**
 * This class represents a fighting unit.
 *
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public abstract class AbstractUnit implements DrawableElement {

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

    protected final PointF mPosition;

    protected AbstractUnit(int health, int damage, int attackRange, int attackDelay, int weight) {
        this.mHealth = health;
        this.mDamage = damage;
        this.mAttackRange = attackRange;
        this.mAttackDelay = attackDelay;
        this.mWeight = weight;

        mPosition = new PointF();
    }
}
