package org.es.gameengine.drawable;

/**
 * A sprite is a simple animated element.
 * <br />
 * Created by Cyril Leroux on 05/02/14.
 */
public interface Sprite<AnimationId extends Enum<AnimationId>> extends DrawableElement {

    public void startAnimation();

    public void stopAnimation();

    /** @return The current animation id. */
    public AnimationId getAnimationId();

    /** Set the current animation id. */
    public void setAnimationId(AnimationId animationId);

    /** @return The current animation. */
    public Animation getAnimation();

    /** @return The animation matching the animation id. */
    public Animation getAnimation(AnimationId animationId);
}
