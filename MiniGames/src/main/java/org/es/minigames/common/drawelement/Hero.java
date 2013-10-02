package org.es.minigames.common.drawelement;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;

import org.es.minigames.R;

/** Created by Cyril on 02/10/13. */
public class Hero extends AnimatedElement {

    private Animation mWalkLeft = null;
    private Animation mWalkRight = null;

    public Hero(Resources resources) {
        super(null);

        mWalkLeft = new Animation(resources,
                new int[] {
                        R.drawable.hero_left_1,
                        R.drawable.hero_left_2,
                        R.drawable.hero_left_3,
                        R.drawable.hero_left_4,
                        R.drawable.hero_left_5,
                        R.drawable.hero_left_6,
                }, 800, true);

        mWalkRight = new Animation(resources,
                new int[] {
                        R.drawable.hero_right_1,
                        R.drawable.hero_right_2,
                        R.drawable.hero_right_3,
                        R.drawable.hero_right_4,
                        R.drawable.hero_right_5,
                        R.drawable.hero_right_6,
                }, 800, true);

        mAnimation = mWalkRight;
    }

    protected void switchAnimation(Animation animation) {
        if (animation == null || animation.equals(mAnimation)) {
            return;
        }
        mAnimation = animation;
        startAnimation();
    }

    public void walkLeft() {
        switchAnimation(mWalkLeft);
    }

    public void walkRight() {
        switchAnimation(mWalkRight);
    }
}
