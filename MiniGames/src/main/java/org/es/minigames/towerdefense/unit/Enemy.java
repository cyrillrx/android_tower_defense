package org.es.minigames.towerdefense.unit;

import org.es.engine.graphics.animation.AnimationCallback;
import org.es.engine.graphics.sprite.Sprite;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Enemy extends AbstractUnit<Enemy.AnimationId> implements AnimationCallback {

    public static enum AnimationId {
        LEFT, RIGHT, UP, DOWN
    }

    public static enum Type {
        CRAWLING, FLYING, BIG
    }

    protected Enemy(Sprite<AnimationId> sprite, int health, int damage, int attackRange, int attackDelay, int weight) {
        super(sprite, health, damage, attackRange, attackDelay, weight);
    }

    @Override
    public void onAnimationStopped() {  }

    @Override
    public void stopAnimation() { mSprite.stopAnimation(); }
}
