package org.es.gameengine.drawable;

/**
 * A sprite is a simple animated element.
 * <br />
 * Created by Cyril Leroux on 05/02/14.
 */
public interface Sprite extends DrawableElement {

    public void startAnimation();

    public void stopAnimation();

    /** @return The current animation id. */
    public int getAnimationId();

    /** Set the current animation id. */
    public void setAnimationId(final int id);

    /** @return The current animation. */
    public Animation getAnimation();
}
