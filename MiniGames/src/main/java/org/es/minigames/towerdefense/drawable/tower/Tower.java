package org.es.minigames.towerdefense.drawable.tower;

import android.graphics.Canvas;

import org.es.gameengine.drawable.Animation;
import org.es.minigames.towerdefense.drawable.AbstractUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Tower extends AbstractUnit {

    // Tower states
    private static final int STATE_STATIC   = 0;
    private static final int STATE_RUNNING  = 1;

    public static final int DIRECTION_LEFT     = 0;
    public static final int DIRECTION_RIGHT    = 1;
    public static final int DIRECTION_UP       = 2;
    public static final int DIRECTION_DOWN     = 3;

    private List<Animation> mAnimations = new ArrayList<>();
    private int mCurrentState = 0;

    public Tower(List<Animation> animations, int health, int damage, int attackRange, int attackDelay, int weight) {
        super(health, damage, attackRange, attackDelay, weight);
        mAnimations = animations;
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {

    }

    @Override
    public void draw(Canvas canvas) {
        mAnimations.get(mCurrentState).draw(canvas, mPosition);
    }
}
