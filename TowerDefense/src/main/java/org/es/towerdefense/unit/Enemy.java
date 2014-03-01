package org.es.towerdefense.unit;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import org.es.engine.graphics.sprite.Sprite;
import org.es.engine.graphics.utils.DrawingParam;
import org.es.engine.toolbox.pathfinding.ShortestPath;
import org.es.towerdefense.battleground.Battleground;
import org.es.utils.DrawTextUtils;
import org.es.utils.PositionUtils;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import static org.es.utils.DrawTextUtils.HorizontalAlign.CENTER;
import static org.es.utils.DrawTextUtils.HorizontalAlign.RIGHT;
import static org.es.utils.DrawTextUtils.VerticalAlign.BOTTOM;
import static org.es.utils.DrawTextUtils.VerticalAlign.TOP;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Enemy extends Offensive<Enemy.AnimationId> {

    public static enum AnimationId {
        LEFT, RIGHT, UP, DOWN
    }

    public static enum Type {
        CRAWLING, FLYING, BIG
    }

    private boolean mFinisher;

    private final Queue<PointF> mDestinations;

    private long mDebugCpuTime = 0;

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
        float distanceAvailable = 0.06f;
        moveToNextPoint(distanceAvailable);
        updateAnimation();
    }

    private void findAPath(Battleground battleground) {
        // TODO call path finding algorithms here

        long cpuTimeTemp;

        if (mDestinations.isEmpty()) {

            ShortestPath findPath = new ShortestPath();

            // TODO New Structure for tiles

            // Calculate the computed time for path finding algo
            cpuTimeTemp = System.currentTimeMillis();

            final Point goal = battleground.getGoal(0);
            final List<Point> destinations = findPath.findShortestPath((int) getCenterX(), (int) getCenterY(), goal.x, goal.y, battleground.getWalkingMap());
            // TODO try a different implementation to use addAll instead of a for each loop
            for (Point destinationTile : destinations) {
                mDestinations.add(new PointF(destinationTile.x + 0.5f, destinationTile.y + 0.5f));
            }
            mDebugCpuTime = System.currentTimeMillis() - cpuTimeTemp;
        }
    }

    /** Use the available distance to move towards the next destination point. */
    private void moveToNextPoint(float distanceAvailable) {

        PointF destination = mDestinations.peek();
        turnTowards(destination.x, destination.y);
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
                moveToNextPoint(distanceAvailable);
            }
        }
    }

    @Override
    protected void doUpdateRotationAnimation() {

        // Range covered by the animation.
        final double animationRange = 90.0;

        if (PositionUtils.angleInRange(mRotationAngle, 0, animationRange)) {
            changeAnimation(AnimationId.RIGHT);

        } else if (angleInRange(270, animationRange)) {
            changeAnimation(AnimationId.DOWN);

        } else if (angleInRange(180, animationRange)) {
            changeAnimation(AnimationId.LEFT);

        } else if (angleInRange(90, animationRange)) {
            changeAnimation(AnimationId.UP);
        }
    }

    public boolean isFinisher() { return mFinisher; }

    @Override
    public boolean isOutOfPlay() {
        return isDead() || isFinisher();
    }

    @Override
    public void drawHUD(Canvas canvas, DrawingParam param) {
        super.drawHUD(canvas, param);
    }

    @Override
    public void drawDebugHUD(Canvas canvas, DrawingParam param, Paint paint, SharedPreferences pref) {
        super.drawDebugHUD(canvas, param, paint, pref);

        // Save and change paint color.
        int initialColor = paint.getColor();
        paint.setColor(Color.RED);
        paint.setTextSize(20f);

        // cpu time consumed to compute the shortest path
        final String cpuText = "CPU : " + mDebugCpuTime + " ms";

        // Draw cpu time
        DrawTextUtils.drawText(cpuText, canvas,
                getCenterX() * param.coef() + param.offsetX(),
                (getPosY() + getHeight()) * param.coef() + param.offsetY(),
                CENTER, BOTTOM, paint);

        // restore paint color.
        paint.setColor(initialColor);
    }
}
