package org.es.engine.graphics.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * A SpriteSheetAnimation is an Animation created from a sprite sheet and a rectangle array.<br />
 *
 * @author Cyril Leroux
 *         Created on 03/02/14.
 */
public class SpriteSheetAnimation extends Animation {

    private final Bitmap mSpriteSheet;
    private final Rect[] mFrames;

    /**
     * Constructor taking a sprite sheet.
     *
     * @param spriteSheet   The Bitmap of the sprite sheet.
     * @param frames        The rectangles to cut the sheet to create the animation.
     * @param frameDuration Frame duration in milliseconds.
     * @param isLoop        True if the animation is supposed to play loop.
     * @param callback      The object that will be called when the animation ends.
     */
    public SpriteSheetAnimation(Bitmap spriteSheet, Rect[] frames, float frameDuration, boolean isLoop, AnimationCallback callback) {
        super(frameDuration, isLoop, callback);
        mSpriteSheet = spriteSheet;
        mFrames = frames;
    }

    @Override
    public void draw(Canvas canvas, RectF boundingRect) {
        final Rect src = mFrames[mCurrentFrameId];
        canvas.drawBitmap(mSpriteSheet, src, boundingRect, null);
    }

    @Override
    public Bitmap getBitmap(int frameId) {
        // Prevent out of bounds exceptions.
        if (frameId < 0 || frameId >= mFrames.length) {
            return null;
        }
        final Rect src = mFrames[frameId];
        return Bitmap.createBitmap(mSpriteSheet, src.left, src.top, src.width(), src.height());
    }

    protected int getFrameCount() { return mFrames.length; }

    @Override
    protected float getWidth() { return mFrames[mCurrentFrameId].width(); }

    @Override
    protected float getHeight() { return mFrames[mCurrentFrameId].height(); }
}