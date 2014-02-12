package org.es.minigames.towerdefense.battleground;

import org.es.minigames.towerdefense.unit.AbstractUnit;

/**
 * Created by Cyril Leroux on 12/02/14.
 */
public class BattlegroundHelper {

    /**
     * Initialize the Tile grid.
     * @param columnCount The number of columns in the grid.
     * @param rowCount The number of rows in the grid.
     * @return The initialized grid.
     */
    public static Tile[][] initTiles(final int columnCount, final int rowCount) {

        Tile[][] grid = new Tile[rowCount][columnCount];

        // For each line
        for (int y = 0; y < rowCount; y++) {
            // For each element in the line
            for (int x = 0; x < columnCount; x++) {
                grid[y][x] = new Tile();
            }
        }
        return grid;
    }

    /**
     * Get the size of a tile.
     * The size is adjusted depending on the surface on which to draw.
     *
     * @param surfaceWidth The width of the surface on which to draw.
     * @param surfaceHeight The height of the surface on which to draw.
     * @param columnCount The number of columns in the grid.
     * @param rowCount The number of rows in the grid.
     * @return The size of a Tile.
     */
    public static float getTileSize(final float surfaceWidth, final float surfaceHeight,
                                    final float columnCount, final float rowCount) {
        final float surfaceRatio = surfaceWidth / surfaceHeight;
        final float grid = columnCount / rowCount;

        // Adjust the size to the surface
        return (grid > surfaceRatio) ?
                surfaceWidth / columnCount :
                surfaceHeight / rowCount;
    }

    /**
     * Update the position of all the grid.
     * The positions are relative to the grid origin.
     * @param tiles The grid.
     * @param tileSize The new Tile size.
     */
    public static void updateTileSizes(final Tile[][] tiles, final float tileSize) {

        int currentX = 0;
        int currentY = 0;

        // For each line
        for (final Tile[] row : tiles) {
            // For each element in the line
            for (final Tile tile : row) {
                tile.setRect(currentX, currentY, currentX + tileSize, currentY + tileSize);
                if (!tile.isEmpty()) {
                    AbstractUnit unit = tile.getBindedUnit();
                    unit.getAnimation().setBounds(tileSize, tileSize);
                    float posX = tile.centerX() - tileSize / 2f;
                    float posY = tile.centerY() - tileSize / 2f;
                    unit.setPosition(posX, posY);
                }

                currentX += tileSize;
            }
            // End of the line : reset x, update y
            currentX = 0;
            currentY += tileSize;
        }
    }
}
