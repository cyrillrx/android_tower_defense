package org.es.minigames.towerdefense.unit;

import org.es.engine.graphics.animation.AnimationCallback;
import org.es.engine.graphics.sprite.Sprite;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Tower extends AbstractUnit<Tower.AnimationId> implements AnimationCallback {

    public static enum Type {
        BASIC
    }

    public static enum AnimationId {
        DOWN,
        DOWN_LEFT,
        LEFT,
        LEFT_UP,
        UP,
        UP_RIGHT,
        RIGHT,
        RIGHT_DOWN
    }

    public Tower(Sprite<AnimationId> sprite, int health, int damage, int attackRange, int attackDelay, int weight) {
        super(sprite, health, damage, attackRange, attackDelay, weight);
    }

    @Override
    public void onAnimationStopped() { }

    @Override
    public void stopAnimation() { mSprite.stopAnimation(); }
}
