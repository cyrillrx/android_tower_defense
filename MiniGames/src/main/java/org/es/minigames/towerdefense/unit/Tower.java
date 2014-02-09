package org.es.minigames.towerdefense.unit;

import android.graphics.Canvas;

import org.es.gameengine.AnimationCallback;
import org.es.gameengine.drawable.Animation;
import org.es.gameengine.drawable.GenericSprite;
import org.es.gameengine.drawable.Sprite;
import org.es.minigames.towerdefense.unit.AbstractUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Tower extends AbstractUnit implements Sprite<Tower.AnimationId>, AnimationCallback {

    public static enum Type {
        BASIC
    }

    public static enum AnimationId {
        DOWN,
        DOWN_LEFT,
        LEFT,
        LEFT_UP,
        UP,
        UP_RIGHT,
        RIGHT,
        RIGHT_DOWN
    }

    // Tower states
    private static final int STATE_STATIC   = 0;
    private static final int STATE_RUNNING  = 1;

    private final Sprite<Tower.AnimationId> mSprite;

    public Tower(Sprite<Tower.AnimationId> sprite, int health, int damage, int attackRange, int attackDelay, int weight) {
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
    public void moveX(int value) { mSprite.moveX(value); }

    @Override
    public void moveY(int value) { mSprite.moveY(value); }
}
