package org.es.minigames.towerdefense.battleground;

import android.content.res.Resources;

/**
 * @author Cyril Leroux
 *         Created on 12/02/14.
 */
public class BattlegroundHelper {

    /**
     * Initialize the Tile grid.
     * @param columnCount The number of columns in the grid.
     * @param rowCount The number of rows in the grid.
     * @return The initialized grid.
     */
    public static Tile[][] initTiles(final int columnCount, final int rowCount, Resources resources) {

        Tile[][] grid = new Tile[rowCount][columnCount];

        // For each line
        for (int y = 0; y < rowCount; y++) {
            // For each element in the line
            for (int x = 0; x < columnCount; x++) {
                grid[y][x] = TileFactory.createTile(x, y, Tile.Type.METAL, resources);
            }
        }
        return grid;
    }

    /**
     * Get the minimum size of a tile for a given surface size.
     * The size is adjusted depending on the surface on which to draw.
     *
     * @param surfaceWidth The width of the surface on which to draw.
     * @param surfaceHeight The height of the surface on which to draw.
     * @param columnCount The number of columns in the grid.
     * @param rowCount The number of rows in the grid.
     * @return The size of a Tile.
     */
    public static float minTileSize(final float surfaceWidth, final float surfaceHeight,
                                    final float columnCount, final float rowCount) {
        final float surfaceRatio = surfaceWidth / surfaceHeight;
        final float grid = columnCount / rowCount;

        // Adjust the size to the surface
        return (grid > surfaceRatio) ?
                surfaceWidth / columnCount:
                surfaceHeight / rowCount;
    }
}
