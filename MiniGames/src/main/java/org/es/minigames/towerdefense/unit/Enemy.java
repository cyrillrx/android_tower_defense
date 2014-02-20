package org.es.minigames.towerdefense.unit;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import org.es.engine.graphics.animation.AnimationCallback;
import org.es.engine.graphics.sprite.Sprite;
import org.es.minigames.towerdefense.battleground.Battleground;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Enemy extends Offensive<Enemy.AnimationId> implements AnimationCallback {

    public static enum AnimationId {
        LEFT, RIGHT, UP, DOWN
    }

    public static enum Type {
        CRAWLING, FLYING, BIG
    }

    private boolean mFinisher;

    private final Queue<PointF> mDestinations;

    protected Enemy(Sprite<AnimationId> sprite, float width, float height, int weight, int health,
                    int damage, int attackRange, int attackDelay) {
        super(sprite, width, height, weight, health, damage, attackRange, attackDelay);
        mDestinations = new ArrayDeque<>();
        mFinisher = false;
    }

    public void update(Battleground battleground, boolean mustUpdate) {
        if (mustUpdate) {
            findAPath(battleground);
        }
        move();
    }

    private void findAPath(Battleground battleground) {
        mDestinations.clear();
        mDestinations.add(battleground.getGoal(this));
    }

    // TODO
    private void move() {
        moveX(0.05f);
        updateAnimation();
        if (mDestinations.isEmpty()) {
            mFinisher = true;
            return;
        }
        // Move towards the next destination.

    }

    public boolean isFinisher() { return mFinisher; }

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
