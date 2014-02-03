package org.es.gameengine.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;

import org.es.gameengine.AnimationCallback;

/**
 * A spriteSheetAnimation is an Animation created from a sprite sheet and a rectangle array.<br />
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
     * @param animationDuration Animation duration in milliseconds.
     * @param isLoop True if the animation is supposed to play loop.
     * @param callback The object that will be called when the animation ends.
     */
    public SpriteSheetAnimation(Bitmap spriteSheet, Rect[] frameRects, float animationDuration, boolean isLoop, AnimationCallback callback) {
        super(animationDuration, isLoop, callback);
        mSpriteSheet = spriteSheet;
        mFrameRects = frameRects;
    }

    /**
     * Constructor that will load a bitmap array from the resources.
     *
     * @param resources Context resources used to load sprite sheet bitmap.
     * @param resourceId The id of the sprite sheet resource.
     * @param animationDuration Animation duration in milliseconds.
     * @param isLoop True if the animation is supposed to play loop.
     * @param callback The object that will be called when the animation ends.
     */
    public SpriteSheetAnimation(Resources resources, int resourceId, Rect[] frameRects, float animationDuration, boolean isLoop, AnimationCallback callback) {
        this(BitmapFactory.decodeResource(resources, resourceId), frameRects, animationDuration, isLoop, callback);
    }

    @Override
    public void draw(Canvas canvas, PointF position) {
        Rect src = mFrameRects[mCurrentFrameId];
        Rect dest = new Rect(src);
        dest.left = (int) position.x;
        dest.top = (int) position.y;
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