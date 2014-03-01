package org.es.towerdefense.unit;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.es.engine.graphics.sprite.Sprite;
import org.es.engine.graphics.utils.DrawingParam;
import org.es.towerdefense.settings.PreferenceKey;
import org.es.utils.PositionUtils;

import java.util.Collection;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Tower extends Offensive<Tower.AnimationId> {

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

    public Tower(Sprite<AnimationId> sprite, int weight, int health,
                 int damage, float attackRange, long attackDelay) {
        super(sprite, 1f, 1f, weight, health, damage, attackRange, attackDelay);
    }

    public void update(Collection<? extends Destructible> elementsOnScreen) {
        updateFocus(elementsOnScreen);
        actOnFocused();
        updateAnimation();
    }

    /**
     * Update the current state of focus.
     *
     * @param elementsOnScreen The elements on screen.
     */
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
     * @param element The element to look for.
     * @return True is the element is in sight. False otherwise.
     */
    private boolean isInSight(Destructible element) {
        // TODO
        final float posX = element.getCenterX();
        final float posY = element.getCenterY();
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
     * @param element1 The position of the first element.
     * @param element2 The position of the second element.
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
        turnTowards(mFocused.getCenterX(), mFocused.getCenterY());
        attack(mFocused);
    }

    @Override
    protected void doUpdateRotationAnimation() {

        // Range covered by the animation.
        final double animationRange = 45.0;

        if (PositionUtils.angleInRange(mRotationAngle, 0, animationRange)) {
            changeAnimation(AnimationId.RIGHT);

        } else if (angleInRange(45, animationRange)) {
            changeAnimation(AnimationId.UP_RIGHT);

        } else if (angleInRange(90, animationRange)) {
            changeAnimation(AnimationId.UP);

        } else if (angleInRange(135, animationRange)) {
            changeAnimation(AnimationId.LEFT_UP);

        } else if (angleInRange(180, animationRange)) {
            changeAnimation(AnimationId.LEFT);

        } else if (angleInRange(225, animationRange)) {
            changeAnimation(AnimationId.DOWN_LEFT);

        } else if (angleInRange(270, animationRange)) {
            changeAnimation(AnimationId.DOWN);

        } else if (angleInRange(315, animationRange)) {
            changeAnimation(AnimationId.RIGHT_DOWN);
        }
    }

    @Override
    public void drawHUD(Canvas canvas, DrawingParam param) {
        super.drawHUD(canvas, param);
    }

    @Override
    public void drawDebugHUD(Canvas canvas, DrawingParam param, Paint paint, SharedPreferences pref) {
        boolean displayDebugHUD = pref.getBoolean(PreferenceKey.KEY_TOWER_DEBUG, true);
        if (!displayDebugHUD) { return; }

        super.drawDebugHUD(canvas, param, paint, pref);

        // Save paint color and style.
        int initialColor = paint.getColor();
        Paint.Style initialStyle = paint.getStyle();


        // Change paint color depending on the focus state.
        if (isFocused()) {
            paint.setColor(Color.RED);

            // Draw a line from the tower to the focused element.
            boolean displayFocus = pref.getBoolean(PreferenceKey.KEY_TOWER_FOCUS, true);
            if (displayFocus) {
            canvas.drawLine(
                    getCenterX() * param.coef() + param.offsetX(),
                    getCenterY() * param.coef() + param.offsetY(),
                    mFocused.getCenterX() * param.coef() + param.offsetX(),
                    mFocused.getCenterY() * param.coef() + param.offsetY(),
                    paint);
            }
        } else {
            paint.setColor(Color.BLUE);
        }

        // Draw the range of sight.
        boolean displayRange = pref.getBoolean(PreferenceKey.KEY_TOWER_RANGE, true);
        if (displayRange) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(
                getCenterX() * param.coef() + param.offsetX(),
                getCenterY() * param.coef() + param.offsetY(),
                mAttackRange * param.coef(), paint);

        }

        // restore paint color and style.
        paint.setColor(initialColor);
        paint.setStyle(initialStyle);
    }
}