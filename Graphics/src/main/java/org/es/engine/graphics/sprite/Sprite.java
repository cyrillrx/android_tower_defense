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

    public void startAnimation();

    public void stopAnimation();

    public void updateAnimation();

    /** @return The current animation id. */
    public AnimationId getAnimationId();

    /** Set the current animation id. */
    public void setAnimationId(AnimationId animationId);

    /** @return The current animation. */
    public Animation getAnimation();

    /** @return The animation matching the animation id. */
    public Animation getAnimation(AnimationId animationId);

    /** Set the position of the sprite in the canvas. */
    public void setPosition(float x, float y);

    /** Add value to the abscissa of the sprite. */
    public void moveX(int value);

    /** Add value to the ordinate of the sprite. */
    public void moveY(int value);
}
