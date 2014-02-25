package org.es.towerdefense.process;

import android.content.res.Resources;

import org.es.towerdefense.battleground.Battleground;
import org.es.towerdefense.object.Wave;
import org.es.towerdefense.unit.Enemy;
import org.es.towerdefense.unit.EnemyFactory;
import org.es.utils.TimeAware;

import java.util.Queue;
import java.util.Set;

/**
 * @author Cyril Leroux
 *         Created on 05/02/14.
 */
public class WaveManager implements TimeAware {

    private final Queue<Wave> mWaves;
    private final Battleground mBattleground;
    private final Set<Enemy> mEnemies;

    /** The spawn frequency in milliseconds. */
    private long mSpawnFrequency;
    /** The elapsed time since last spawn in milliseconds. */
    private long mLastSpawn;

    private Resources mResources;

    public WaveManager(Queue<Wave> waves, Battleground battleground, Set<Enemy> enemies, Resources resources) {
        mWaves = waves;
        mBattleground = battleground;
        mEnemies = enemies;
        mResources = resources;
    }

    public void start() {
        // TODO
    }

    private void processWave(Wave wave) {

        // TODO
    }

    // TODO set to private
    public Enemy spawnEnemy() {
        Enemy enemy = EnemyFactory.createEnemy(Enemy.Type.CRAWLING, mResources);
        mEnemies.add(enemy);
        mBattleground.spawnEnemy(enemy, 0);
        return enemy;
    }

    public void update() {
        // TODO
//        if (mWaves.get(mWaveId).isOver() && ++mWaveId < mWaves.size()) {
//            mWaves.get(mWaveId).start();
//        }
    }

    @Override
    public void onResume(long elapsedTimeMs) {
        mLastSpawn += elapsedTimeMs;
    }
}
