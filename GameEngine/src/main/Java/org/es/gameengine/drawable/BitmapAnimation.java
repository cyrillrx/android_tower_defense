package org.es.gameengine.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;

import org.es.gameengine.AnimationCallback;

/**
 * A BitmapAnimation is an Animation created from a bitmap array.<br />
 * Created by Cyril Leroux on 03/02/14.
 */
public class BitmapAnimation extends Animation {

    private final Bitmap[] mFrames;

    /**
     * Constructor taking a bitmap array.
     *
     * @param bitmaps The animation bitmaps.
     * @param animationDuration Animation duration in milliseconds.
     * @param isLoop True if the animation is supposed to play loop.
     * @param callback The object that will be called when the animation ends.
     */
    public BitmapAnimation(Bitmap[] bitmaps, float animationDuration, boolean isLoop, AnimationCallback callback) {
        super(animationDuration, isLoop, callback);
        mFrames = bitmaps;
    }

    /**
     * Constructor that will load a bitmap array from the resources.
     *
     * @param resources Context resources used to load animation bitmaps.
     * @param resourceIds The resource ids used to instantiate animation bitmaps.
     * @param animationDuration Animation duration in milliseconds.
     * @param isLoop True if the animation is supposed to play loop.
     * @param callback The object that will be called when the animation ends.
     */
    public BitmapAnimation(Resources resources, int[] resourceIds, float animationDuration, boolean isLoop, AnimationCallback callback) {
        this(getBitmapsFromResources(resources, resourceIds), animationDuration, isLoop, callback);
    }

    /**
     * Loads an array of bitmaps from the Resources.
     *
     * @param resources The Resources from which to load the bitmaps.
     * @param resourceIds The ids of the resources to load.
     * @return The array of loaded bitmaps.
     */
    private static Bitmap[] getBitmapsFromResources(Resources resources, int[] resourceIds) {
        final int bitmapCount = resourceIds.length;
        Bitmap[] bitmaps = new Bitmap[bitmapCount];

        for (int i = 0; i < bitmapCount; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(resources, resourceIds[i]);
        }
        return bitmaps;
    }

    @Override
    public void draw(Canvas canvas, PointF position) {
        canvas.drawBitmap(getCurrentFrame(), position.x, position.y, null);
    }

    @Override
    protected int getFrameCount() {
        return mFrames.length;
    }

    @Override
    protected Bitmap getCurrentFrame() {
        return mFrames[mCurrentFrameId];
    }
}