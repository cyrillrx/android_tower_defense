package org.es.minigames.towerdefense.unit;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import org.es.engine.graphics.animation.AnimationCallback;
import org.es.engine.graphics.sprite.Sprite;
import org.es.minigames.towerdefense.battleground.Battleground;
import org.es.minigames.utils.PositionUtils;

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
        // TODO replace distance by speed * elapsed time
        float distanceAvailable = 0.05f;
        moveToNextPoint(distanceAvailable);
        updateAnimation();
    }

    private void findAPath(Battleground battleground) {
        // TODO call path finding algorithms here
        if (mDestinations.isEmpty()) {
//        mDestinations.clear();
            mDestinations.add(new PointF(0.5f, 1.5f));
            mDestinations.add(new PointF(10.5f, 1.5f));
            mDestinations.add(new PointF(10.5f, 3.5f));
            mDestinations.add(battleground.getGoal(this));

            PointF destination = mDestinations.peek();
            turnTowards(destination.x, destination.y);
        }
    }

    /** Use the available distance to move towards the next destination point. */
    private void moveToNextPoint(float distanceAvailable) {
        PointF destination = mDestinations.peek();
        double distanceToDestination = PositionUtils.distance(getCenterX(), getCenterY(), destination.x, destination.y);

        // Not enough to reach the next point.
        if (distanceAvailable < distanceToDestination) {
            PointF diff = PositionUtils.polarToCartesian(mRotationAngle, distanceAvailable, true);
            // just move forward
            setPosition(getPosX() + diff.x, getPosY() + diff.y);

        } else { // Enough to reach the next point.
            // Remove the reached point from destination list.
            mDestinations.remove();
            if (mDestinations.isEmpty()) {
                mFinisher = true;

            } else {
                distanceAvailable -= distanceToDestination;
                destination = mDestinations.peek();
                turnTowards(destination.x, destination.y);
                moveToNextPoint(distanceAvailable);
            }
        }
    }

    @Override
    protected void doUpdateRotationAnimation() {

        // Range covered by the animation.
        final double animationRange = 90.0;

        if (PositionUtils.angleInRange(mRotationAngle, 0, animationRange)) {
            setAnimationId(AnimationId.RIGHT);

        } else if (angleInRange(270, animationRange)) {
            setAnimationId(AnimationId.DOWN);

        } else if (angleInRange(180, animationRange)) {
            setAnimationId(AnimationId.LEFT);

        } else if (angleInRange(90, animationRange)) {
            setAnimationId(AnimationId.UP);
        }
    }

    public boolean isFinisher() { return mFinisher; }

    @Override
    public boolean isOutOfPlay() {
        return isDead() || isFinisher();
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
