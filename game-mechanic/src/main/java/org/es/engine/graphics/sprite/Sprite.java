package org.es.engine.graphics.sprite;

import org.es.engine.graphics.animation.Animation;
import org.es.engine.graphics.drawable.DrawableElement;

/**
 * A sprite is a simple animated element.
 *
 * @author Cyril Leroux
 *         Created on 05/02/14.
 */
public interface Sprite<AnimationId extends Enum<AnimationId>> extends DrawableElement {

    // TODO change by the definition of a bounding rectangle and a set position.
    // TODO Setting the dimension can cause ratio issues.

    /**
     * Define the animation bounding rectangle.
     * This function must be called just before the draw.
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    void setDimensions(float left, float top, float right, float bottom);

    void startAnimation();

    void stopAnimation();

    /** Update the current frame of the animation if necessary. */
    boolean updateAnimationFrame();

    /** @return The current animation id. */
    AnimationId getAnimationId();

    /**
     * Set the current animation id.
     *
     * @param animationId The new animation Id.
     */
    void setAnimationId(AnimationId animationId);

    /** @return The current animation. */
    Animation getAnimation();

    /** @return The animation matching the animation id. */
    Animation getAnimation(AnimationId animationId);
}
