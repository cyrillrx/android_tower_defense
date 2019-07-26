package org.es.towerdefense.object;

import org.es.towerdefense.unit.Enemy;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

/**
 * Class that represents a wave of attackers.
 *
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Wave {

    /** Number of attackers of each type to be spawn during the wave. */
    private final Map<Enemy.Type, Integer> mAttackerMap;
    /** The wave duration in milliseconds. */
    private final long mDuration;
    /** Weight of the wave. Helps calculate score, money and spawn delay. */
    private final int mWeight;

    private final Queue<Enemy.Type> mAttackerTypes;

    private boolean mOver;

    public Wave(Map<Enemy.Type, Integer> attackerMap, int duration) {
        mAttackerMap = attackerMap;
        mDuration = duration;

        // Calculate spawn time.
        mWeight = calculateWaveWeight(attackerMap);
        // TODO : init enemy queue

        mAttackerTypes = initTypeList(attackerMap);
    }

    private Queue<Enemy.Type> initTypeList(Map<Enemy.Type, Integer> attackers) {
        Queue<Enemy.Type> queue = new ArrayDeque<>();

        for (Map.Entry<Enemy.Type, Integer> entry : attackers.entrySet()) {
            final int count = entry.getValue();
            for (int i = 0; i < count; i++) {
                queue.add(entry.getKey());
            }
        }

        return queue;
    }

    private int calculateWaveWeight(Map<Enemy.Type, Integer> attackers) {

        int weight = 0;
        for (Map.Entry<Enemy.Type, Integer> entry : attackers.entrySet()) {
            weight += getWeight(entry.getKey()) * entry.getValue();
        }
        return weight;
    }

    public Enemy.Type nextAttackerType() {
        return mAttackerTypes.remove();
    }

    // TODO update
    private static int getWeight(Enemy.Type type) {
        return 30;
    }

    public boolean isOver() {
        return mAttackerTypes.isEmpty();
    }
}
