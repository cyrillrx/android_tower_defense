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

    /** The delay between two spawns in milliseconds. */
    private static final long SPAWN_DELAY = 1000;
    /** The delay between two waves in milliseconds. */
    private static final long WAVE_DELAY = 5000;

    /** The elapsed time since last spawn in milliseconds. */
    private long mLastSpawn;

    /** The current wave */
    private Wave mCurrentWave;

    private final Queue<Wave> mWaves;
    private final Battleground mBattleground;
    private final Set<Enemy> mEnemies;

    private Resources mResources;

    public WaveManager(Queue<Wave> waves, Battleground battleground, Set<Enemy> enemies, Resources resources) {
        mWaves = waves;
        mBattleground = battleground;
        mEnemies = enemies;
        mResources = resources;
        mLastSpawn = 0;

        mCurrentWave = mWaves.remove();
    }

    private Enemy spawnEnemy(Enemy.Type type) {
        Enemy enemy = EnemyFactory.createEnemy(type, mResources);
        mEnemies.add(enemy);
        mBattleground.spawnEnemy(enemy, 0);
        mLastSpawn = System.currentTimeMillis();
        return enemy;
    }

    public void update() {

        if (mCurrentWave.isOver()) {
            if (mWaves.isEmpty()) {
                // TODO end the game
                return;
            }
            mCurrentWave = mWaves.remove();
        }

        final long elapsedTime = System.currentTimeMillis() - mLastSpawn;
        if (!mCurrentWave.isOver() && elapsedTime > SPAWN_DELAY) {
            spawnEnemy(mCurrentWave.nextAttackerType());
        }
    }

    @Override
    public void onResume(long elapsedTimeMs) {
        mLastSpawn += elapsedTimeMs;
    }
}
