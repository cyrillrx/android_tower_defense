package org.es.minigames.towerdefense.unit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.es.engine.graphics.animation.AnimationCallback;
import org.es.engine.graphics.sprite.Sprite;
import org.es.minigames.utils.PositionUtils;

import java.util.Collection;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Tower extends AbstractUnit<Tower.AnimationId> implements AnimationCallback {

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

    /** The current element followed by the tower. */
    private Destructible mFollowed;

    public Tower(Sprite<AnimationId> sprite, int weight,
                 int health, int damage, int attackRange, int attackDelay) {
        super(sprite, 1f, 1f, weight, health, damage, attackRange, attackDelay);
    }

    // TODO delete : just for debug
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (isFollowing()) {
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStrokeWidth(1f);
            canvas.drawLine(
                    getCenterX() * getCoef(),
                    getCenterY() * getCoef(),
                    mFollowed.getCenterX() * getCoef(),
                    mFollowed.getCenterY() * getCoef(),
                    paint);
        }
    }

    @Override
    public void onAnimationStopped() { }

    public void update(Collection<? extends Destructible> elementsOnScreen) {
        updateFollow(elementsOnScreen);
        actOnFollowed();
        updateAnimation();
    }

    /** @return True if the tower is currently following a unit. */
    private boolean isFollowing() {
        return mFollowed != null;
    }

    /** Update the current state of the auto-follow. */
    private void updateFollow(Collection<? extends Destructible> elementsOnScreen) {
        if (isFollowing() && isInSight(mFollowed)) {
            return;
        }
        mFollowed = getNearestUnitInSight(elementsOnScreen);
    }

    /**
     * Check if a unit is in sight.
     * @param destructibleElement
     * @return True is the unit is in sight. False otherwise.
     */
    private boolean isInSight(Destructible destructibleElement) {
        return true;
        // TODO
        //        PointF position = unit.getPosition();
        //        return mAttackRange >= PositionUtils.distance(mPosition.x, mPosition.x, position.x, position.y);
    }

    /**
     * Research units in sight.
     * This method should ignore allies and neutral elements.
     *
     * @return The nearest unit or null if no unit is in sight.
     */
    private AbstractUnit getNearestUnitInSight(Collection<? extends Destructible> destructibleElements) {

        AbstractUnit nearestUnit = null;
        for (Destructible element : destructibleElements) {
            // If the element is not in sight, no need to go further. Go check the next one.
            if (!isInSight(element)) { continue; }
            // TODO add a check for alignment (allie, foe or neutral).
            // TODO if allie of neutral => continue
            if (!(element instanceof Enemy)) { continue; }

            // The first element found is the nearest for the moment.
            if (nearestUnit == null) {
                nearestUnit = (AbstractUnit) element;
                continue;
            }

            nearestUnit = (AbstractUnit) nearest(element, nearestUnit);
        }

        return nearestUnit;
    }

    private Destructible nearest(Destructible element1, Destructible element2) {
        final float elem1X = element1.getCenterX();
        final float elem1Y = element1.getCenterY();
        final float elem2X = element2.getCenterX();
        final float elem2Y = element2.getCenterY();
        final double distance1 = PositionUtils.distance(getCenterX(), getCenterY(), elem1X, elem1Y);
        final double distance2 = PositionUtils.distance(getCenterX(), getCenterY(), elem2X, elem2Y);
        return (distance1 < distance2) ? element1 : element2;
    }

    /**
     * Act on the element being followed.
     * <ul>
     *     <li>Turn towards the followed element.</li>
     *     <li>Shot the followed element.</li>
     * </ul>
     */
    private void actOnFollowed() {
        if (mFollowed == null) { return; }
        turnTowards(mFollowed);
        // TODO shoot
    }

    /**
     * Turns the tower towards the element in parameter.
     * @param element The element towards which to turn to.
     */
    private void turnTowards(Destructible element) {
        final float posX = element.getCenterX();
        final float posY = element.getCenterY();
        mRotationAngle = PositionUtils.angleInDegrees(getCenterX(), getCenterY(), posX, posY);
    }
}