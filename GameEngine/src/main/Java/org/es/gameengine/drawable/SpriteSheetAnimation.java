package org.es.gameengine.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import org.es.gameengine.AnimationCallback;

/**
 * A SpriteSheetAnimation is an Animation created from a sprite sheet and a rectangle array.<br />
 * Created by Cyril Leroux on 03/02/14.
 */
public class SpriteSheetAnimation extends Animation {

    private final Bitmap mSpriteSheet;
    private final Rect[] mFrameRects;

    /**
     * Constructor taking a sprite sheet.
     *
     * @param spriteSheet The Bitmap of the sprite sheet.
     * @param frameRects The rectangles to cut the sheet to create the animation.
     * @param frameDuration Frame duration in milliseconds.
     * @param isLoop True if the animation is supposed to play loop.
     * @param callback The object that will be called when the animation ends.
     */
    public SpriteSheetAnimation(Bitmap spriteSheet, Rect[] frameRects, float frameDuration, boolean isLoop, AnimationCallback callback) {
        super(frameDuration, isLoop, callback);
        mSpriteSheet = spriteSheet;
        mFrameRects = frameRects;
    }

    /**
     * Constructor that will load a bitmap array from the resources.
     *
     * @param resources Context resources used to load sprite sheet bitmap.
     * @param resourceId The id of the sprite sheet resource.
     * @param frameDuration Frame duration in milliseconds.
     * @param isLoop True if the animation is supposed to play loop.
     * @param callback The object that will be called when the animation ends.
     */
    public SpriteSheetAnimation(Resources resources, int resourceId, Rect[] frameRects, float frameDuration, boolean isLoop, AnimationCallback callback) {
        this(BitmapFactory.decodeResource(resources, resourceId), frameRects, frameDuration, isLoop, callback);
    }

    @Override
    public void draw(Canvas canvas, PointF position) {
        Rect src = mFrameRects[mCurrentFrameId];
        RectF dest = new RectF(
                position.x,
                position.y,
                position.x + src.width(),
                position.y + src.height());
        canvas.drawBitmap(mSpriteSheet, src, dest, null);
    }


    protected int getFrameCount() {
        return mFrameRects.length;
    }

    /** @return The current frame. */
    protected Bitmap getCurrentFrame() {
        return Bitmap.createBitmap(mSpriteSheet,
                mFrameRects[mCurrentFrameId].left,
                mFrameRects[mCurrentFrameId].top,
                mFrameRects[mCurrentFrameId].width(),
                mFrameRects[mCurrentFrameId].height());
    }

}