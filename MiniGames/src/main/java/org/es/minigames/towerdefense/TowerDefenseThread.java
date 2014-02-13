package org.es.minigames.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import org.es.gameengine.DrawingThread;
import org.es.gameengine.UserEvent;
import org.es.gameengine.drawable.Background;
import org.es.minigames.R;
import org.es.minigames.towerdefense.battleground.Battleground;
import org.es.minigames.towerdefense.battleground.Tile;
import org.es.minigames.towerdefense.unit.Enemy;
import org.es.minigames.towerdefense.unit.EnemyFactory;
import org.es.minigames.towerdefense.unit.Tower;
import org.es.minigames.towerdefense.unit.TowerFactory;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class TowerDefenseThread extends DrawingThread {

    Battleground mBackground;
    Enemy[] mEnemies = new Enemy[1];

    public TowerDefenseThread(SurfaceHolder surfaceHolder, Context context) {
        super(surfaceHolder, context);

        mBackground = new Battleground(21, 11);
        mBackground.addTower(TowerFactory.createTower(Tower.Type.BASIC, mResources), 10, 5);

        mEnemies[0] = EnemyFactory.createEnemy(Enemy.Type.CRAWLING, mResources);
        mEnemies[0].startAnimation();

    }

    @Override
    protected void updateSurfaceSize(int surfaceWidth, int surfaceHeight) {

        mBackground.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);

        for (Enemy enemy : mEnemies) {
            enemy.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
        }

    }

    @Override
    protected boolean update() {
        mEnemies[0].moveX(1);;
        mEnemies[0].updateAnimation();
        return false;
    }

    @Override
    protected void processEvent(UserEvent event) {

    }

    @Override
    protected void doDraw(Canvas canvas) {

        mBackground.draw(canvas);

        for (Enemy enemy : mEnemies) {
            enemy.draw(canvas);
        }
    }
}
