package org.es.towerdefense.unit;

import android.content.res.Resources;
import android.graphics.Rect;

import org.es.engine.graphics.animation.Animation;
import org.es.engine.graphics.animation.SpriteSheetAnimation;
import org.es.engine.graphics.sprite.GenericSprite;
import org.es.engine.graphics.sprite.Sprite;
import org.es.engine.graphics.sprite.SpriteSheet;
import org.es.towerdefense.R;

import java.util.EnumMap;

/**
 * A factory to build Towers.
 *
 * @author Cyril Leroux
 *         Created on 02/02/14.
 */
public class TowerFactory {

    private static final int RES_BASIC_TOWER_SS = R.drawable.sprite_sheet_tower_1;
    private static final int BASIC_TOWER_WEIGHT = 2;
    private static final int BASIC_TOWER_HEALTH = 200;
    private static final int BASIC_TOWER_DAMAGE = 20;
    private static final float BASIC_TOWER_ATTACK_RANGE = 3;
    private static final long BASIC_TOWER_ATTACK_DELAY = 1000;

    public static Tower createTower(Tower.Type type, Resources resources) {

        switch (type) {
            case BASIC:
            default:
                return createBasicTower(resources);
        }
    }

    private static Tower createBasicTower(Resources resources) {

        final SpriteSheet spriteSheet = new SpriteSheet(resources, RES_BASIC_TOWER_SS, 4, 2);

        final EnumMap<Tower.AnimationId, Animation> mAnimations = new EnumMap<>(Tower.AnimationId.class);

        mAnimations.put(Tower.AnimationId.DOWN,
                new SpriteSheetAnimation(spriteSheet.getBitmap(),
                        new Rect[]{ spriteSheet.getRect(0, 0) }, -1, false, null));

        mAnimations.put(Tower.AnimationId.DOWN_LEFT,
                new SpriteSheetAnimation(spriteSheet.getBitmap(),
                        new Rect[]{ spriteSheet.getRect(0, 1) }, -1, false, null));

        mAnimations.put(Tower.AnimationId.LEFT,
                new SpriteSheetAnimation(spriteSheet.getBitmap(),
                        new Rect[]{ spriteSheet.getRect(0, 2) }, -1, false, null));

        mAnimations.put(Tower.AnimationId.LEFT_UP,
                new SpriteSheetAnimation(spriteSheet.getBitmap(),
                        new Rect[]{ spriteSheet.getRect(0, 3) }, -1, false, null));

        mAnimations.put(Tower.AnimationId.UP,
                new SpriteSheetAnimation(spriteSheet.getBitmap(),
                        new Rect[]{ spriteSheet.getRect(1, 0) }, -1, false, null));

        mAnimations.put(Tower.AnimationId.UP_RIGHT,
                new SpriteSheetAnimation(spriteSheet.getBitmap(),
                        new Rect[]{ spriteSheet.getRect(1, 1) }, -1, false, null));

        mAnimations.put(Tower.AnimationId.RIGHT,
                new SpriteSheetAnimation(spriteSheet.getBitmap(),
                        new Rect[]{ spriteSheet.getRect(1, 2) }, -1, false, null));

        mAnimations.put(Tower.AnimationId.RIGHT_DOWN,
                new SpriteSheetAnimation(spriteSheet.getBitmap(),
                        new Rect[]{ spriteSheet.getRect(1, 3) }, -1, false, null));


        return createTower(
                new GenericSprite<>(mAnimations, Tower.AnimationId.DOWN),
                BASIC_TOWER_WEIGHT,
                BASIC_TOWER_HEALTH,
                BASIC_TOWER_DAMAGE,
                BASIC_TOWER_ATTACK_RANGE, BASIC_TOWER_ATTACK_DELAY);
    }

    private static Tower createTower(Sprite<Tower.AnimationId> sprite, int weight,
                                     int health, int damage, float attackRange, long attackDelay) {
        return new Tower(sprite, weight, health, damage, attackRange, attackDelay);
    }
}
