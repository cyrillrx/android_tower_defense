package org.es.minigames.towerdefense.process;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

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
        mBattleground.addTower(tower, 5, 2);

        Tower tower2 = TowerFactory.createTower(Tower.Type.BASIC, resources);
        mTowers.add(tower2);
        mBattleground.addTower(tower2, 9, 4);

        Enemy enemy = EnemyFactory.createEnemy(Enemy.Type.CRAWLING, resources);
        enemy.startAnimation();
        mEnemies.add(enemy);
        mBattleground.spawnEnemy(enemy, 0, 3);
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
            enemy.moveX(0.05f);
            enemy.updateAnimation();
            // Loop
            if (enemy.getPosX() > mBattleground.getWidth()) {
                mBattleground.spawnEnemy(enemy, 0, 3);
            }
        }
    }

    public void draw(Canvas canvas) {

        // Draw background
        mBattleground.draw(canvas);

        // Draw the elements
        // It is important to draw towers first if there are flying enemies.
        for (Tower tower : mTowers) {
            tower.draw(canvas);
        }
        for (Enemy enemy : mEnemies) {
            enemy.draw(canvas);
        }

        // Draw animations (such as missiles).
        // TODO Draw the animations

        // Draw the main HUD
        drawHUD(canvas);
    }

    /**
     * Draw the main Head-up display.<br />
     * Scores, GUI, ...
     */
    protected void drawHUD(Canvas canvas) {

        Paint debugPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        debugPaint.setAntiAlias(true);
        debugPaint.setStrokeWidth(1f);
        debugPaint.setStyle(Paint.Style.STROKE);

        // Draw the elements
        // It is important to draw towers first if there are flying enemies.
        for (Tower tower : mTowers) {
            tower.drawHUD(canvas);
            tower.drawDebugHUD(canvas, debugPaint);
        }
        for (Enemy enemy : mEnemies) {
            enemy.drawHUD(canvas);
            enemy.drawDebugHUD(canvas, debugPaint);
        }
        // TODO Draw the main HUD
    }
}
