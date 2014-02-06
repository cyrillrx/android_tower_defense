package org.es.minigames.towerdefense.unit;

import android.content.res.Resources;
import android.graphics.Rect;

import org.es.gameengine.drawable.Animation;
import org.es.gameengine.drawable.GenericSprite;
import org.es.gameengine.drawable.Sprite;
import org.es.gameengine.drawable.SpriteSheetAnimation;
import org.es.minigames.R;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * A factory to build Towers.
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

        EnumMap<Tower.AnimationId, Animation> mAnimations = new EnumMap<>(Tower.AnimationId.class);
        mAnimations.put(Tower.AnimationId.DOWN,       new SpriteSheetAnimation(resources, RES_BASIC_TOWER_SS, new Rect[] { new Rect(0,     0, 150, 150) }, -1, false, null));
        mAnimations.put(Tower.AnimationId.DOWN_LEFT,  new SpriteSheetAnimation(resources, RES_BASIC_TOWER_SS, new Rect[] { new Rect(150,   0, 300, 150) }, -1, false, null));
        mAnimations.put(Tower.AnimationId.LEFT,       new SpriteSheetAnimation(resources, RES_BASIC_TOWER_SS, new Rect[] { new Rect(300,   0, 450, 150) }, -1, false, null));
        mAnimations.put(Tower.AnimationId.LEFT_UP,    new SpriteSheetAnimation(resources, RES_BASIC_TOWER_SS, new Rect[] { new Rect(450,   0, 600, 150) }, -1, false, null));
        mAnimations.put(Tower.AnimationId.UP ,        new SpriteSheetAnimation(resources, RES_BASIC_TOWER_SS, new Rect[] { new Rect(0,   150, 150, 300) }, -1, false, null));
        mAnimations.put(Tower.AnimationId.UP_RIGHT,   new SpriteSheetAnimation(resources, RES_BASIC_TOWER_SS, new Rect[] { new Rect(150, 150, 150, 300) }, -1, false, null));
        mAnimations.put(Tower.AnimationId.RIGHT,      new SpriteSheetAnimation(resources, RES_BASIC_TOWER_SS, new Rect[] { new Rect(300, 150, 450, 300) }, -1, false, null));
        mAnimations.put(Tower.AnimationId.RIGHT_DOWN, new SpriteSheetAnimation(resources, RES_BASIC_TOWER_SS, new Rect[] { new Rect(450, 150, 600, 300) }, -1, false, null));

        return createTower(new GenericSprite<>(mAnimations, Tower.AnimationId.DOWN),
                BASIC_TOWER_HEALTH,
                BASIC_TOWER_DAMAGE,
                BASIC_TOWER_ATTACK_RANGE, BASIC_TOWER_ATTACK_DELAY,
                BASIC_TOWER_WEIGHT);
    }

    protected static Tower createTower(Sprite<Tower.AnimationId> sprite, int health, int damage, int attackRange, int attackDelay, int weight) {
        return new Tower(sprite, health, damage, attackRange, attackDelay, weight);
    }
}
