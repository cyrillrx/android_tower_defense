package org.es.minigames.towerdefense.process;

import android.content.res.Resources;
import android.graphics.Canvas;

import org.es.minigames.towerdefense.battleground.Battleground;
import org.es.minigames.towerdefense.unit.Enemy;
import org.es.minigames.towerdefense.unit.EnemyFactory;
import org.es.minigames.towerdefense.unit.Tower;
import org.es.minigames.towerdefense.unit.TowerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Cyril Leroux
 *         Created on 13/02/14.
 */
public class GameMgr {

    private final Battleground mBattleground;
    // TODO mDrawable should evolve to a list of towers or static elements (handle barricades)
    //private final Set<DrawableElement> mDrawables;
    private final Set<Enemy> mEnemies;
    private final Set<Tower> mTowers;

    public GameMgr(Resources resources) {

        mBattleground = new Battleground(15, 7, resources);
        mEnemies = new HashSet<>();
        mTowers = new HashSet<>();

        Tower tower = TowerFactory.createTower(Tower.Type.BASIC, resources);
        mTowers.add(tower);
        mBattleground.addTower(tower, 7, 3);

        Enemy enemy = EnemyFactory.createEnemy(Enemy.Type.CRAWLING, resources);
        enemy.startAnimation();
        mEnemies.add(enemy);
        mBattleground.spawnEnemy(enemy, 0, 2);
    }

    public void updateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        mBattleground.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);

        final float coef = mBattleground.getCoef();
        for (Enemy enemy : mEnemies) {
            enemy.setCoef(coef);
            enemy.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
        }
    }

    public void update() {
        mBattleground.update();

        for (Tower tower : mTowers) {
            tower.update(mEnemies);
        }

        for (Enemy enemy : mEnemies) {
            // TODO Enemy IA (like destination) should be move inside Enemy class
            enemy.moveX(0.03f);
            enemy.updateAnimation();
            // Loop
            if (enemy.getPosX() > mBattleground.getWidth()) {
                mBattleground.spawnEnemy(enemy, 0, 2);
            }
        }
    }

    public void draw(Canvas canvas) {

        // Draw background
        mBattleground.draw(canvas);

        // Draw the elements
        // Its important to draw towers first if there are flying enemies.
        for (Tower tower : mTowers) {
            tower.draw(canvas);
        }
        for (Enemy enemy : mEnemies) {
            enemy.draw(canvas);
        }

        // Draw animations (such as missiles).

        //
        drawHUD(canvas);
    }

    /** Draw the Head-up display. */
    protected void drawHUD(Canvas canvas) {

        // Draw the elements
        for (Tower tower : mTowers) {
            tower.drawHUD(canvas);
        }
        for (Enemy enemy : mEnemies) {
            enemy.drawHUD(canvas);
        }

        // Draw main HUD
        // TODO
    }
}
