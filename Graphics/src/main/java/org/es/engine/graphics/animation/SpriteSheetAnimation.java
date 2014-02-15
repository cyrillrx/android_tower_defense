package org.es.engine.graphics.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
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
     * @param spriteSheet The Bitmap of the sprite sheet.
     * @param frames The rectangles to cut the sheet to create the animation.
     * @param frameDuration Frame duration in milliseconds.
     * @param isLoop True if the animation is supposed to play loop.
     * @param callback The object that will be called when the animation ends.
     */
    public SpriteSheetAnimation(Bitmap spriteSheet, Rect[] frames, float frameDuration, boolean isLoop, AnimationCallback callback) {
        super(frameDuration, isLoop, callback);
        mSpriteSheet = spriteSheet;
        mFrames = frames;
        setBounds(frames[0].width(), frames[0].height());
    }

    @Override
    public void draw(Canvas canvas, PointF position) {
        Rect src = mFrames[mCurrentFrameId];
        RectF dest = new RectF(
                position.x,
                position.y,
                position.x + mBoundsX,
                position.y + mBoundsY);
        canvas.drawBitmap(mSpriteSheet, src, dest, null);
    }

    protected int getFrameCount() { return mFrames.length; }

    /** @return The current frame. */
    protected Bitmap getCurrentFrame() {
        return Bitmap.createBitmap(mSpriteSheet,
                mFrames[mCurrentFrameId].left,
                mFrames[mCurrentFrameId].top,
                mFrames[mCurrentFrameId].width(),
                mFrames[mCurrentFrameId].height());
    }
}