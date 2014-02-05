package org.es.minigames.towerdefense.unit;

import android.graphics.Canvas;

import org.es.minigames.towerdefense.unit.AbstractUnit;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Enemy extends AbstractUnit {

    protected Enemy(int health, int damage, int attackRange, int attackDelay, int weight) {
        super(health, damage, attackRange, attackDelay, weight);
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    public static enum Type {
        SMALL,
        MEDIUM,
        BIG
    }

    private Type mType;
}
