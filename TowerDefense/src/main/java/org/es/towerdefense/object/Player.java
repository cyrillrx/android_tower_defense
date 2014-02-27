package org.es.towerdefense.object;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Player {

    private int mHealth;
    private int mMoney;
    private int mScore;

    public Player(int healthPoint, int money) {
        mHealth = healthPoint;
        mMoney = money;
        mScore = 0;
    }

    public int getHealth() { return mHealth; }

    public void decrementHealth(int value) { mHealth -= value; }

    public int getScore() { return mScore; }

    public void incrementScore(int value) { mScore += value; }

    public int getMoney() { return mMoney; }

    public void incrementMoney(int value) { mMoney += value; }

    public void decrementMoney(int value) { mMoney -= value; }


}
