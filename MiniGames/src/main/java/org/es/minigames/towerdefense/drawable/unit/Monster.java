package org.es.minigames.towerdefense.drawable.unit;

import android.graphics.Canvas;

import org.es.minigames.towerdefense.drawable.AbstractUnit;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Monster extends AbstractUnit {

    protected Monster(int health, int damage, int attackRange, int attackDelay, int weight) {
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
