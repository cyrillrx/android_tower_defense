package org.es.minigames.towerdefense.unit;

import android.graphics.Canvas;
import android.graphics.Paint;

import org.es.engine.graphics.animation.AnimationCallback;
import org.es.engine.graphics.sprite.Sprite;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Enemy extends AbstractUnit<Enemy.AnimationId> implements AnimationCallback {

    public static enum AnimationId {
        LEFT, RIGHT, UP, DOWN
    }

    public static enum Type {
        CRAWLING, FLYING, BIG
    }

    protected Enemy(Sprite<AnimationId> sprite, float width, float height, int weight, int health,
                    int damage, int attackRange, int attackDelay) {
        super(sprite, width, height, weight, health, damage, attackRange, attackDelay);
    }

    @Override
    public void drawHUD(Canvas canvas) {
        super.drawHUD(canvas);
    }

    @Override
    public void drawDebugHUD(Canvas canvas, Paint paint) {
        super.drawDebugHUD(canvas, paint);
    }

    @Override
    public void onAnimationStopped() {  }

    /** Add value to the abscissa of the sprite. */
    public void moveX(float value) {
        setPosition(getPosX() + value, getPosY());
    }

    /** Add value to the ordinate of the sprite. */
    public void moveY(float value) {
        setPosition(getPosX() + value, getPosY() + value);
    }
}
