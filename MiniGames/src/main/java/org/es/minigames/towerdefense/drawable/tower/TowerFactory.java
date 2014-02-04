package org.es.minigames.towerdefense.drawable.tower;

import android.content.res.Resources;
import android.graphics.Rect;

import org.es.gameengine.drawable.Animation;
import org.es.gameengine.drawable.BitmapAnimation;
import org.es.gameengine.drawable.SpriteSheetAnimation;
import org.es.minigames.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cyril Leroux on 02/02/14.
 */
public class TowerFactory {

    private static final int RES_BASIC_TOWER_SS  = R.drawable.tower_1_sprite_sheet;
    private static final int BASIC_TOWER_HEALTH = 200;
    private static final int BASIC_TOWER_DAMAGE = 20;
    private static final int BASIC_TOWER_ATTACK_RANGE  = 3;
    private static final int BASIC_TOWER_ATTACK_DELAY = 1000;
    private static final int BASIC_TOWER_WEIGHT = 2;

    public static Tower createBasicTower(Resources resources) {

        List<Animation> mAnimations = new ArrayList<>();
        mAnimations.add(Tower.DIRECTION_LEFT,  new SpriteSheetAnimation(resources, RES_BASIC_TOWER_SS, new Rect[] { new Rect(300, 0,   450, 150) },  -1, false, null));
        mAnimations.add(Tower.DIRECTION_RIGHT, new SpriteSheetAnimation(resources, RES_BASIC_TOWER_SS, new Rect[] { new Rect(300, 150, 450, 300) },  -1, false, null));
        mAnimations.add(Tower.DIRECTION_UP ,   new SpriteSheetAnimation(resources, RES_BASIC_TOWER_SS, new Rect[] { new Rect(0,   150, 150, 300) },  -1, false, null));
        mAnimations.add(Tower.DIRECTION_DOWN,  new SpriteSheetAnimation(resources, RES_BASIC_TOWER_SS, new Rect[] { new Rect(0,   0,   150, 150) },  -1, false, null));

        return createTower(mAnimations,
                BASIC_TOWER_HEALTH,
                BASIC_TOWER_DAMAGE,
                BASIC_TOWER_ATTACK_RANGE, BASIC_TOWER_ATTACK_DELAY,
                BASIC_TOWER_WEIGHT);
    }

    protected static Tower createTower(List<Animation> animations, int health, int damage, int attackRange, int attackDelay, int weight) {
        return new Tower(animations, health, damage, attackRange, attackDelay, weight);
    }
}
