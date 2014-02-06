package org.es.minigames.towerdefense.unit;

import org.es.gameengine.drawable.Sprite;

/**
 * A factory to build Enemy units.
 * Created by Cyril Leroux on 06/02/14.
 */
public class EnemyFactory {

    protected static Enemy createEnemy(Sprite<Enemy.AnimationId> sprite, int health, int damage, int attackRange, int attackDelay, int weight) {
        return new Enemy(sprite, health, damage, attackRange, attackDelay, weight);
    }
}
