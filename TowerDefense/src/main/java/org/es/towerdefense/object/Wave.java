package org.es.towerdefense.object;

import org.es.towerdefense.unit.Enemy;

import java.util.Map;

/**
 * Class that represents a wave of attackers.
 *
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Wave {

    /** Number of attackers of each type to be spawn during the wave. */
    private final Map<Enemy.Type, Integer> mAttackers;
    /** The wave duration in milliseconds. */
    private final long mDuration;
    /** Weight of the wave. Helps calculate score, money and spawn delay. */
    private final int mWeight;

    public Wave(Map<Enemy.Type, Integer> attackers, int duration) {
        mAttackers = attackers;
        mDuration = duration;

        // Calculate spawn time.
        mWeight = calculateWaveWeight(attackers);
    }

    private int calculateWaveWeight(Map<Enemy.Type, Integer> attackers) {

        int weight = 0;
        for (Map.Entry<Enemy.Type, Integer> entry : attackers.entrySet()) {
            weight += getWeight(entry.getKey()) * entry.getValue();
        }
        return weight;
    }

    // TODO update
    private int getWeight(Enemy.Type type) {
        return 30;
    }
}
