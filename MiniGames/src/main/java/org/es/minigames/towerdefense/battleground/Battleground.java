package org.es.minigames.towerdefense.battleground;

import android.graphics.Canvas;

import org.es.gameengine.drawable.DrawableElement;

/**
 * Class that represents the battlefield.
 *
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Battleground implements DrawableElement {

    private final int mColumnCount;
    private final int mRowCount;
    private final Tile[][] mTiles;

    public Battleground(int columnCount, int rowCount) {
        mColumnCount = columnCount;
        mRowCount = rowCount;
        mTiles = new Tile[rowCount][columnCount];
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {

        for (int y = 0; y < mRowCount; y++) {
            for (int x = 0; x < mColumnCount; x++) {
                //mTiles[y][x] =
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
