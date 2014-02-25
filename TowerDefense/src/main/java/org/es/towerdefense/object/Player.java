package org.es.towerdefense.object;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public abstract class Player implements com.google.android.gms.games.Player {

    private int mHealth;
    private int mMoney;

    public Player(int mHealthPoint, int mMoney) {
        this.mHealth = mHealthPoint;
        this.mMoney = mMoney;
    }

    public int getHealth() { return mHealth; }

    public void decrementHealth(int value) { mHealth -= value; }

    public int getMoney() { return mMoney; }

    public void incrementMoney(int value) { mMoney += value; }

    public void decrementMoney(int value) { mMoney -= value; }


}
