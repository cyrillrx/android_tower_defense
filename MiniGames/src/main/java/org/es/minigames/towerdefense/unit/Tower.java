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

    private static int getBisectorAngle(AnimationId animId) {
        return 0;
    }

    /** The current element followed by the tower. */
    private Destructible mFollowed;

    /**
     * The angle of the last update.<br />
     * Used to optimize the update of rotation animation.
     */
    private double mLastAngle;

    public Tower(Sprite<AnimationId> sprite, int weight, int health,
                 int damage, float attackRange, long attackDelay) {
        super(sprite, 1f, 1f, weight, health, damage, attackRange, attackDelay);
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
        if (isFollowing()) {
            paint.setColor(Color.RED);

            // Draw a line from the tower to the focused element.
            canvas.drawLine(
                    getCenterX() * getCoef(),
                    getCenterY() * getCoef(),
                    mFollowed.getCenterX() * getCoef(),
                    mFollowed.getCenterY() * getCoef(),
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

    public void update(Collection<? extends Destructible> elementsOnScreen) {
        updateFollow(elementsOnScreen);
        actOnFollowed();
        updateAnimation();
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

        if (updateToThisRotation(0, animationRange, AnimationId.RIGHT)) {
            setAnimationId(AnimationId.RIGHT);

        } else if (updateToThisRotation(45, animationRange, AnimationId.RIGHT_DOWN)) {
            setAnimationId(AnimationId.RIGHT);

        } else if (updateToThisRotation(90, animationRange, AnimationId.DOWN)) {
            setAnimationId(AnimationId.DOWN);

        } else if (updateToThisRotation(135, animationRange, AnimationId.DOWN_LEFT)) {
            setAnimationId(AnimationId.DOWN_LEFT);

        } else if (updateToThisRotation(180, animationRange, AnimationId.LEFT)) {
            setAnimationId(AnimationId.LEFT);

        } else if (updateToThisRotation(225, animationRange, AnimationId.LEFT_UP)) {
            setAnimationId(AnimationId.LEFT_UP);

        } else if (updateToThisRotation(270, animationRange, AnimationId.UP)) {
            setAnimationId(AnimationId.UP);

        } else if (updateToThisRotation(315, animationRange, AnimationId.UP_RIGHT)) {
            setAnimationId(AnimationId.UP_RIGHT);
        }

        mLastAngle = mRotationAngle;
    }

    // TODO rename
    private boolean updateToThisRotation(double bisectorAngle, double range, AnimationId animId) {

        double halfRange = range / 2.0;
        double angle = (mRotationAngle < 0) ? mRotationAngle + 360.0 : mRotationAngle;
        return (angle % 360.0 > (bisectorAngle - halfRange) % 360.0) &&
                (angle % 360.0 < (bisectorAngle + halfRange) % 360.0) &&
                getAnimationId() != animId;
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
            nearestUnit = (AbstractUnit) getNearest(element, nearestUnit);
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
     * Act on the element being followed.
     * <ul>
     * <li>Turn towards the followed element.</li>
     * <li>Shot the followed element.</li>
     * </ul>
     */
    private void actOnFollowed() {
        if (mFollowed == null) { return; }
        turnTowards(mFollowed);
        shoot(mFollowed);
    }

    /**
     * Turns the tower towards the element in parameter.
     *
     * @param element The element towards which to turn to.
     */
    private void turnTowards(Destructible element) {
        final float posX = element.getCenterX();
        final float posY = element.getCenterY();
        mRotationAngle = PositionUtils.angleInDegrees(getCenterX(), getCenterY(), posX, posY);
    }

    private void shoot(Destructible element) {
        long delay = System.currentTimeMillis() - mLastAttack;

        if (delay < mAttackDelay) { return ; }

        // Do attack !
        mFollowed.receiveDamages(this);
        mLastAttack = System.currentTimeMillis();
    }
}