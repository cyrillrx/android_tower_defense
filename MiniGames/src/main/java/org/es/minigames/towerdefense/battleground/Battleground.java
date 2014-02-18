package org.es.minigames.towerdefense.battleground;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import org.es.engine.graphics.drawable.DrawableElement;
import org.es.minigames.towerdefense.unit.Enemy;
import org.es.minigames.towerdefense.unit.Tower;
import org.es.minigames.utils.DrawingParam;

/**
 * Class that represents the battlefield.
 *
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Battleground implements DrawableElement {

    private final float mColumnCount;
    private final float mRowCount;
    private final PointF mPosition;
    private final DrawingParam mDrawingParam;

    private final Tile[][] mTiles;

    public Battleground(int columnCount, int rowCount, Resources resources) {
        mColumnCount = columnCount;
        mRowCount = rowCount;
        mTiles = BattlegroundHelper.initTiles(columnCount, rowCount, resources);
        mPosition = new PointF(0, 0);
        mDrawingParam = new DrawingParam();
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        float tileSize = BattlegroundHelper.getTileSize(surfaceWidth, surfaceHeight, mColumnCount, mRowCount);
        mDrawingParam.setCoef(tileSize);
        BattlegroundHelper.updateTileSizes(mTiles, tileSize);
    }

    @Override
    public void draw(Canvas canvas) {

        // TODO to delete
        // Draw the background
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        canvas.drawRect(canvas.getClipBounds(), paint);

        for (Tile[] row : mTiles) {
            for (Tile tile : row) {
                tile.draw(canvas);
            }
        }
    }

    @Override
    public float getPosX() { return mPosition.x; }

    @Override
    public float getPosY() { return mPosition.y; }

    @Override
    public void setPosition(float x, float y) { mPosition.set(x, y); }

    @Override
    public float getWidth() { return mColumnCount; }

    @Override
    public float getHeight() { return mRowCount; }

    public float getCoef() { return mDrawingParam.getCoef(); }

    public void update() { }

    /** Add a tower to the center of the selected tile. */
    public void addTower(Tower tower, int columnId, int rowId) {
        Tile tile = mTiles[rowId][columnId];
        tile.bindUnit(tower);
        final float posX = tile.getCenterX() - tower.getWidth() / 2f;
        final float posY = tile.getCenterY() - tower.getHeight() / 2f;
        tower.setPosition(posX, posY);
    }

    /** Spawn the enemy in the center of the selected tile. */
    // TODO The spawnEnemy should have a other parameter to specify from where the unit is suppose to appear.
    public void spawnEnemy(Enemy enemy, int columnId, int rowId) {
        Tile tile = mTiles[rowId][columnId];
        final float posX = tile.getCenterX() - enemy.getWidth() / 2f;
        final float posY = tile.getCenterY() - enemy.getHeight() / 2f;
        enemy.setPosition(posX, posY);
    }
}
