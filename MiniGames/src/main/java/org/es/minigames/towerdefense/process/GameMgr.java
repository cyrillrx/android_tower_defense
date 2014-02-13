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
 * Created by Cyril Leroux on 13/02/14.
 */
public class GameMgr {

    private Battleground mBattleground;
    private List<Enemy> mEnemies = new ArrayList<>();

    public GameMgr(Resources resources) {

        mBattleground = new Battleground(15, 7);
        mBattleground.addTower(TowerFactory.createTower(Tower.Type.BASIC, resources), 7, 3);

        Enemy enemy = EnemyFactory.createEnemy(Enemy.Type.CRAWLING, resources);
        enemy.startAnimation();
        mEnemies.add(enemy);
    }

    public void updateSurfaceSize(int surfaceWidth, int surfaceHeight) {

        mBattleground.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);

        for (Enemy enemy : mEnemies) {
            enemy.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
        }
    }

    public boolean update() {

        for (Enemy enemy : mEnemies) {
            enemy.moveX(1);
            enemy.updateAnimation();
        }
        return false;
    }

    public void draw(Canvas canvas) {

        mBattleground.draw(canvas);
        for (Enemy enemy : mEnemies) {
            enemy.draw(canvas);
        }
    }
}
