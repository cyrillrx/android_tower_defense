package org.es.towerdefense.object;

import android.content.res.Resources;
import android.graphics.Point;

import org.es.engine.graphics.utils.DrawingParam;
import org.es.towerdefense.battleground.Battleground;
import org.es.towerdefense.unit.Enemy;
import org.es.towerdefense.unit.Tower;
import org.es.towerdefense.unit.TowerFactory;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Class used to load waves from a file.
 *
 * @author Cyril Leroux
 *         Created 12/03/14.
 */
public class WaveDAO {

    // TODO This is a temporary function that shall be replaced by a load from file function.

    /**
     * Init a debug wave queue
     * This function shall be replaced by a classic text loaded
     *
     * @return The wave queue.
     */
    public static Queue<Wave> loadDebugWaves() {

        // Initialize attacker map
        Map<Enemy.Type, Integer> attackers = new HashMap<>();
        attackers.put(Enemy.Type.CRAWLING, 10);

        // Create a wave
        Queue<Wave> waves = new ArrayDeque<>();
        waves.add(new Wave(attackers, 40000));

        return waves;
    }
}
