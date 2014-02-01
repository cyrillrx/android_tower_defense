package org.es.minigames.towerdefense.drawable;

import org.es.gameengine.drawable.GameElement;

/**
 * This class represents a fighting unit.
 *
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public abstract class AbstractUnit implements GameElement {

    /** Health points of the unit. */
    private int mHealth;
    /** Damage caused by each attack of the unit. */
    private int mDamage;
    /** Attack range of the unit. */
    private int mAttackRange;
    /** Delay in milliseconds between two attacks. */
    private int mAttackDelay;
    /** Timestamp of the last attack in milliseconds. */
    private int mLastAttack;
    /** Weighting of the unit. Used to calculate the unit cost. */
    private int mWeight;

}
