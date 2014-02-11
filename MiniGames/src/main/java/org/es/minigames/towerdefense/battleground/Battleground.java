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

        final float screenRatio = (float) surfaceWidth / (float) surfaceHeight;
        final float battlegroundRatio = (float) mColumnCount / (float) mRowCount;

        final float tileSize = (battlegroundRatio < screenRatio) ?
                (float) surfaceWidth / (float) mColumnCount :
                (float) surfaceHeight / (float) mRowCount;

        int currentX = 0;
        int currentY = 0;

        // For each line
        for (int y = 0; y < mRowCount; y++) {
            // For each element in the line
            for (int x = 0; x < mColumnCount; x++) {
                mTiles[y][x] = new Tile(currentX, currentY, currentX + tileSize, currentY + tileSize);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
