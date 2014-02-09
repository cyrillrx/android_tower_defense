package org.es.minigames.towerdefense.unit;

import android.content.res.Resources;
import android.graphics.Rect;

import org.es.gameengine.drawable.Animation;
import org.es.gameengine.drawable.GenericSprite;
import org.es.gameengine.drawable.Sprite;
import org.es.gameengine.drawable.SpriteSheetAnimation;
import org.es.minigames.R;

import java.util.EnumMap;

/**
 * A factory to build Enemy units.
 * Created by Cyril Leroux on 06/02/14.
 */
public class EnemyFactory {

    private static final int RES_MONSTERS_SS = R.drawable.sprite_sheet_monsters;
    private static final int CRAWLING_HEALTH = 200;
    private static final int CRAWLING_DAMAGE = 20;
    private static final int CRAWLING_ATTACK_RANGE = 3;
    private static final int CRAWLING_ATTACK_DELAY = 1000;
    private static final int CRAWLING_WEIGHT = 2;

    public static Enemy createEnemy(Enemy.Type type, Resources resources) {

        switch (type) {
            case CRAWLING:
                return createCrawlingEnemy(resources);
            default:
                return null;
        }
    }

    private static Enemy createCrawlingEnemy(Resources resources) {

        EnumMap<Enemy.AnimationId, Animation> mAnimations = new EnumMap<>(Enemy.AnimationId.class);
        mAnimations.put(Enemy.AnimationId.DOWN, new SpriteSheetAnimation(resources, RES_MONSTERS_SS,
                new Rect[]{
                        new Rect(0, 0, 42, 42),
                        new Rect(42, 0, 84, 42),
                        new Rect(84, 0, 126, 42),
                        new Rect(42, 0, 84, 42)
                }, 200, true, null));
        mAnimations.put(Enemy.AnimationId.LEFT, new SpriteSheetAnimation(resources, RES_MONSTERS_SS,
                new Rect[]{
                        new Rect(0, 32, 32, 64),
                        new Rect(32, 32, 64, 64),
                        new Rect(64, 32, 96, 64)
                }, 200, true, null));
        mAnimations.put(Enemy.AnimationId.RIGHT, new SpriteSheetAnimation(resources, RES_MONSTERS_SS,
                new Rect[]{
                        new Rect(0, 90, 45, 135),
                        new Rect(45, 90, 90, 135),
                        new Rect(90, 90, 135, 135)
                }, 200, true, null));
        mAnimations.put(Enemy.AnimationId.UP, new SpriteSheetAnimation(resources, RES_MONSTERS_SS,
                new Rect[]{
                        new Rect(0, 120, 40, 160),
                        new Rect(40, 120, 80, 160),
                        new Rect(80, 120, 120, 160)
                }, 200, true, null));

        return createEnemy(new GenericSprite<>(mAnimations,
                Enemy.AnimationId.DOWN),
                CRAWLING_HEALTH,
                CRAWLING_DAMAGE,
                CRAWLING_ATTACK_RANGE, CRAWLING_ATTACK_DELAY,
                CRAWLING_WEIGHT);
    }

    protected static Enemy createEnemy(Sprite<Enemy.AnimationId> sprite, int health, int damage, int attackRange, int attackDelay, int weight) {
        return new Enemy(sprite, health, damage, attackRange, attackDelay, weight);
    }
}
