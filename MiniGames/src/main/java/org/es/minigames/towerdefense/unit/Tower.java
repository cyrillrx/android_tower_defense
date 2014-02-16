package org.es.minigames.towerdefense.unit;

import android.graphics.PointF;

import org.es.engine.graphics.animation.AnimationCallback;
import org.es.engine.graphics.sprite.Sprite;
import org.es.minigames.utils.PositionUtils;

import java.util.Collection;
import java.util.List;

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

    /** The current unit followed by the tower. */
    private AbstractUnit mFollowed;

    public Tower(Sprite<AnimationId> sprite, int health, int damage, int attackRange, int attackDelay, int weight) {
        super(sprite, health, damage, attackRange, attackDelay, weight);
    }

    @Override
    public void onAnimationStopped() { }

    public void update(Collection<? extends AbstractUnit> elementsOnScreen) {
        // TODO Create a new interface "destructible element"
        updateFollow(elementsOnScreen);
        actOnFollowed();
        updateAnimation();
    }

    /** @return True if the tower is currently following a unit. */
    private boolean isFollowing() {
        return mFollowed != null;
    }

    /** Update the current state of the auto-follow. */
    private void updateFollow(Collection<? extends AbstractUnit> elementsOnScreen) {
        if (isFollowing() && isInSight(mFollowed)) {
            return;
        }
        mFollowed = getNearestUnitInSight(elementsOnScreen);
    }

    /**
     * Check if a unit is in sight.
     * @param unit
     * @return True is the unit is in sight. False otherwise.
     */
    private boolean isInSight(AbstractUnit unit) {
        // TODO Create a new interface "destructible element"
        return true;
        // TODO
//        PointF position = unit.getPosition();
//        return mAttackRange >= PositionUtils.distance(mPosition.x, mPosition.x, position.x, position.y);
    }

    /**
     * Research units in sight.
     *
     * @return The nearest unit or null if no unit is in sight.
     */
    private AbstractUnit getNearestUnitInSight(Collection<? extends AbstractUnit> destructibleElements) {
        // TODO Create a new interface "destructible element"
        AbstractUnit nearestElement = null;

        for (AbstractUnit element : destructibleElements) {
            // If the element is not in sight, no need to go further. Go check the next one.
            if (!isInSight(element)) { continue; }

            // The first element found is the nearest for the moment.
            if (nearestElement == null) {
                nearestElement = element;
                continue;
            }

            nearestElement = nearest(element, mFollowed);
        }

        return nearestElement;
    }

    private AbstractUnit nearest(AbstractUnit element1, AbstractUnit element2) {
        PointF pos1 = element1.getPosition();
        PointF pos2 = element2.getPosition();
        double distance1 = PositionUtils.distance(mPosition.x, mPosition.x, pos1.x, pos1.y);
        double distance2 = PositionUtils.distance(mPosition.x, mPosition.x, pos2.x, pos2.y);
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
        // TODO
        if (mFollowed == null) { return; }
        turnTowards(mFollowed);
    }

    /**
     * Turns the tower towards the element in parameter.
     * @param element The element towards which to turn to.
     */
    private void turnTowards(AbstractUnit element) {
        // TODO Create a new interface "destructible element"
        // TODO update with center positions
        PointF position = element.getPosition();
        mRotationAngle = PositionUtils.angleInDegrees(mPosition.x, mPosition.x, position.x, position.y);
    }
}
