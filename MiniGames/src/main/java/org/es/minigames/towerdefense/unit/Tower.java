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
public class Tower extends Offensive<Tower.AnimationId> implements AnimationCallback {

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

    /** The current element focused by the tower. */
    private Destructible mFocused;

    /**
     * The angle of the last update.<br />
     * Used to optimize the update of rotation animation.
     */
    private double mLastAngle;

    public Tower(Sprite<AnimationId> sprite, int weight, int health,
                 int damage, float attackRange, long attackDelay) {
        super(sprite, 1f, 1f, weight, health, damage, attackRange, attackDelay);
    }

    public void update(Collection<? extends Destructible> elementsOnScreen) {
        updateFocus(elementsOnScreen);
        actOnFocused();
        updateAnimation();
    }

    /** Update the current state of focus. */
    private void updateFocus(Collection<? extends Destructible> elementsOnScreen) {
        if (isFocused() && isInSight(mFocused) && !mFocused.isOutOfPlay()) {
            return;
        }
        mFocused = getNearestUnitToFocus(elementsOnScreen);
    }

    /** @return True if the tower is currently focusing a unit. */
    private boolean isFocused() {
        return mFocused != null;
    }

    /**
     * Check if a unit is in sight.
     *
     * @param destructibleElement
     * @return True is the unit is in sight. False otherwise.
     */
    private boolean isInSight(Destructible destructibleElement) {
        // TODO
        final float posX = destructibleElement.getCenterX();
        final float posY = destructibleElement.getCenterY();
        final double distance = PositionUtils.distance(getCenterX(), getCenterY(), posX, posY);
        return mAttackRange >= distance;
    }

    /**
     * Research units to focus on.
     * This method should ignore allies and neutral elements.
     *
     * @return The nearest unit or null if no unit is in sight.
     */
    private Offensive getNearestUnitToFocus(Collection<? extends Destructible> destructibleElements) {

        Offensive nearestUnit = null;
        for (Destructible element : destructibleElements) {
            // If the element is not in sight, no need to go further. Go check the next one.
            if (!isInSight(element)) { continue; }
            // TODO add a check for alignment (allie, foe or neutral).
            // TODO if allie of neutral => continue
            if (!(element instanceof Enemy)) { continue; }

            // The first element found is the nearest for the moment.
            if (nearestUnit == null) {
                nearestUnit = (Offensive) element;
                continue;
            }
            nearestUnit = (Offensive) getNearest(element, nearestUnit);
        }
        return nearestUnit;
    }

    // TODO move getNearest() function in PositionUtils class.
    /**
     * Get the nearest of both elements passed in parameter
     *
     * @param element1
     * @param element2
     * @return The nearest element.
     */
    private Destructible getNearest(Destructible element1, Destructible element2) {
        final float elem1X = element1.getCenterX();
        final float elem1Y = element1.getCenterY();
        final float elem2X = element2.getCenterX();
        final float elem2Y = element2.getCenterY();
        final double distance1 = PositionUtils.distance(getCenterX(), getCenterY(), elem1X, elem1Y);
        final double distance2 = PositionUtils.distance(getCenterX(), getCenterY(), elem2X, elem2Y);
        return (distance1 < distance2) ? element1 : element2;
    }

    /**
     * Act on the element being focused.
     * <ul>
     *     <li>Turn towards the focused element.</li>
     *     <li>Shot the focused element.</li>
     * </ul>
     */
    private void actOnFocused() {
        if (mFocused == null) { return; }
        turnTowards(mFocused);
        shoot(mFocused);
    }

    /**
     * Turns the tower towards the element in parameter.
     *
     * @param element The element towards which to turn to.
     */
    private void turnTowards(Destructible element) {
        final float posX = element.getCenterX();
        final float posY = element.getCenterY();
        mRotationAngle = PositionUtils.angleInDegrees(getCenterX(), getCenterY(), posX, posY, true);
    }

    private void shoot(Destructible element) {
        long delay = System.currentTimeMillis() - mLastAttack;

        if (delay < mAttackDelay) { return ; }

        // Do attack !
        mFocused.receiveDamages(this);
        mLastAttack = System.currentTimeMillis();
    }

    @Override
    public void updateAnimation() {
        updateRotationAnimation();
        super.updateAnimation();
    }

    // TODO comment
    // TODO optimize
    // TODO rename
    private void updateRotationAnimation() {

        if (mRotationAngle == mLastAngle) { return; }

        double animationRange = 45.0;
        if (isInRange(0, animationRange)) {
            setAnimationId(AnimationId.RIGHT);

        } else if (isInRange(315, animationRange)) {
            setAnimationId(AnimationId.RIGHT_DOWN);

        } else if (isInRange(270, animationRange)) {
            setAnimationId(AnimationId.DOWN);

        } else if (isInRange(225, animationRange)) {
            setAnimationId(AnimationId.DOWN_LEFT);

        } else if (isInRange(180, animationRange)) {
            setAnimationId(AnimationId.LEFT);

        } else if (isInRange(135, animationRange)) {
            setAnimationId(AnimationId.LEFT_UP);

        } else if (isInRange(90, animationRange)) {
            setAnimationId(AnimationId.UP);

        } else if (isInRange(45, animationRange)) {
            setAnimationId(AnimationId.UP_RIGHT);
        }

        mLastAngle = mRotationAngle;
    }

    // TODO rename
    private boolean isInRange(double bisectorAngle, double range) {
        final double angle = (mRotationAngle < 0) ? mRotationAngle + 360.0 : mRotationAngle;
        final double lowerBound = bisectorAngle - range / 2.0;
        final double upperBound = bisectorAngle + range / 2.0;

        if (lowerBound < 0) {
            return angle > lowerBound + 360 || angle < upperBound;
        }

        return angle > lowerBound && angle < upperBound;
    }

    @Override
    public void drawHUD(Canvas canvas) {
        super.drawHUD(canvas);
    }

    @Override
    public void drawDebugHUD(Canvas canvas, Paint paint) {
        super.drawDebugHUD(canvas, paint);

        // Save paint color.
        int initialColor = paint.getColor();

        // Change paint color depending on the focus state.
        if (isFocused()) {
            paint.setColor(Color.RED);

            // Draw a line from the tower to the focused element.
            canvas.drawLine(
                    getCenterX() * getCoef(),
                    getCenterY() * getCoef(),
                    mFocused.getCenterX() * getCoef(),
                    mFocused.getCenterY() * getCoef(),
                    paint);
        } else {
            paint.setColor(Color.BLUE);
        }

        // Draw the range of sight.
        canvas.drawCircle(
                getCenterX() * getCoef(),
                getCenterY() * getCoef(),
                mAttackRange * getCoef(), paint);

        // restore paint color.
        paint.setColor(initialColor);
    }

    @Override
    public void onAnimationStopped() { }
}