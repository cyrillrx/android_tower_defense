package org.es.minigames.towerdefense.process;

import android.content.res.Resources;
import android.graphics.Canvas;

import org.es.minigames.towerdefense.battleground.Battleground;
import org.es.minigames.towerdefense.unit.Enemy;
import org.es.minigames.towerdefense.unit.EnemyFactory;
import org.es.minigames.towerdefense.unit.Tower;
import org.es.minigames.towerdefense.unit.TowerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril Leroux
 *         Created on 13/02/14.
 */
public class GameMgr {

    private final Battleground mBattleground;

    public GameMgr(Resources resources) {

        mBattleground = new Battleground(15, 7);
        mBattleground.addTower(TowerFactory.createTower(Tower.Type.BASIC, resources), 7, 3);

        Enemy enemy = EnemyFactory.createEnemy(Enemy.Type.CRAWLING, resources);
        enemy.startAnimation();
        mBattleground.spawnEnemy(enemy, 0, 2);
    }

    public void updateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        mBattleground.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
    }

    public void update() {
        mBattleground.update();
    }

    public void draw(Canvas canvas) {
        mBattleground.draw(canvas);
    }
}
