package org.es.engine.graphics.sprite;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * s
 *
 * @author Cyril Leroux
 *         Created on 09/02/14.
 */
public class SpriteSheet {

    private final Bitmap mSpriteSheet;
    private final Rect[][] mFrameGrid;

    public SpriteSheet(Bitmap spriteSheet, int columnCount, int rowCount) {
        mSpriteSheet = spriteSheet;
        mFrameGrid = getRectGrid(spriteSheet, columnCount, rowCount);
    }

    /**
     * Constructor that will load a bitmap from the resources.
     *
     * @param resources  Context resources used to load sprite sheet bitmap.
     * @param resourceId The id of the sprite sheet resource.
     */
    public SpriteSheet(Resources resources, int resourceId, int columnCount, int rowCount) {
        this(BitmapFactory.decodeResource(resources, resourceId), columnCount, rowCount);
    }

    private static Rect[][] getRectGrid(final Bitmap spriteSheet, int columnCount, int rowCount) {

        Rect[][] grid = new Rect[rowCount][columnCount];
        final int frameWidth = spriteSheet.getWidth() / columnCount;
        final int frameHeight = spriteSheet.getHeight() / rowCount;

        int currentX = 0;
        int currentY = 0;

        // For each line
        for (int y = 0; y < rowCount; y++) {
            // For each element in the line
            for (int x = 0; x < columnCount; x++) {
                grid[y][x] = new Rect(currentX, currentY, currentX + frameWidth, currentY + frameHeight);
                currentX += frameWidth;
            }
            // End of the line : reset x, update y
            currentX = 0;
            currentY += frameHeight;
        }
        return grid;
    }

    public Bitmap getBitmap() { return mSpriteSheet; }

    public Rect getRect(int y, int x) { return mFrameGrid[y][x]; }
}
