package org.es.minigames.towerdefense.unit;

import android.graphics.Canvas;

import org.es.gameengine.AnimationCallback;
import org.es.gameengine.drawable.Animation;
import org.es.gameengine.drawable.Sprite;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Enemy extends AbstractUnit implements Sprite<Enemy.AnimationId>, AnimationCallback {

    public static enum AnimationId {
        LEFT, RIGHT, UP, DOWN
    }

    public static enum Type {
        CRAWLING, FLYING, BIG
    }

    private final Sprite<Enemy.AnimationId> mSprite;

    protected Enemy(Sprite<Enemy.AnimationId> sprite, int health, int damage, int attackRange, int attackDelay, int weight) {
        super(health, damage, attackRange, attackDelay, weight);
        mSprite = sprite;
    }

    @Override
    public void onAnimationStopped() {  }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        mSprite.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
    }

    @Override
    public void draw(Canvas canvas) { mSprite.draw(canvas); }

    @Override
    public void startAnimation() { mSprite.startAnimation(); }

    @Override
    public void stopAnimation() { mSprite.stopAnimation(); }

    @Override
    public void updateAnimation() { mSprite.updateAnimation(); }

    @Override
    public AnimationId getAnimationId() { return mSprite.getAnimationId(); }

    @Override
    public void setAnimationId(AnimationId animationId) { mSprite.setAnimationId(animationId); }

    @Override
    public Animation getAnimation() { return mSprite.getAnimation(); }

    @Override
    public Animation getAnimation(AnimationId animationId) { return mSprite.getAnimation(animationId); }

    @Override
    public void setPosition(float x, float y) { mSprite.setPosition(x, y); }

    @Override
    public void moveX(int value) { mSprite.moveX(value); }

    @Override
    public void moveY(int value) { mSprite.moveY(value); }
}
