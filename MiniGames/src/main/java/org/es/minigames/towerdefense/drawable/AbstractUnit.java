package org.es.minigames.towerdefense.drawable;

/**
 * This class represents a fighting unit.
 *
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public abstract class AbstractUnit {

    private int mHealthPoints;
    private int mDamage;
    private int mAttackRange;
    private int mAttackRate;
    private int mElapsedTimeSinceLastAttack;

    public int getHealthPoints() {
        return mHealthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.mHealthPoints = healthPoints;
    }

    public int getDamage() {
        return mDamage;
    }

    public void setDamage(int damage) {
        this.mDamage = damage;
    }

    public int getAttackRange() {
        return mAttackRange;
    }

    public void setAttackRange(int attackRange) {
        this.mAttackRange = attackRange;
    }

    public int getAttackRate() {
        return mAttackRate;
    }

    public void setAttackRate(int attackRate) {
        this.mAttackRate = attackRate;
    }

    public int getElapsedTimeSinceLastAttack() {
        return mElapsedTimeSinceLastAttack;
    }

    public void setElapsedTimeSinceLastAttack(int elapsedTimeSinceLastAttack) {
        this.mElapsedTimeSinceLastAttack = elapsedTimeSinceLastAttack;
    }
}
