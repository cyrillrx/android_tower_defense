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
 * A factory to build Enemy units.
 *
 * @author Cyril Leroux
 *         Created on 06/02/14.
 */
public class EnemyFactory {

    // TODO create a configClass
    private static final int RES_MONSTERS_SS = R.drawable.sprite_sheet_monsters;
    private static final float CRAWLING_WIDTH = 0.5f;
    private static final float CRAWLING_HEIGHT = 0.5f;
    private static final int CRAWLING_WEIGHT = 2;
    private static final int CRAWLING_HEALTH = 200;
    private static final int CRAWLING_DAMAGE = 20;
    private static final int CRAWLING_ATTACK_RANGE = 3;
    private static final int CRAWLING_ATTACK_DELAY = 1000;

    public static Enemy createEnemy(Enemy.Type type, Resources resources) {

        switch (type) {
            case CRAWLING:
            default:
                return createCrawlingEnemy(resources);
        }
    }

    private static Enemy createCrawlingEnemy(Resources resources) {

        final SpriteSheet spriteSheet = new SpriteSheet(resources, RES_MONSTERS_SS, 12, 8);
        final EnumMap<Enemy.AnimationId, Animation> mAnimations = new EnumMap<>(Enemy.AnimationId.class);

        mAnimations.put(Enemy.AnimationId.DOWN,
                new SpriteSheetAnimation(spriteSheet.getBitmap(),
                        new Rect[]{
                                spriteSheet.getRect(0, 0),
                                spriteSheet.getRect(0, 1),
                                spriteSheet.getRect(0, 2),
                                spriteSheet.getRect(0, 1)
                        }, 200, true, null));

        mAnimations.put(Enemy.AnimationId.LEFT,
                new SpriteSheetAnimation(spriteSheet.getBitmap(),
                        new Rect[]{
                                spriteSheet.getRect(1, 0),
                                spriteSheet.getRect(1, 1),
                                spriteSheet.getRect(1, 2),
                                spriteSheet.getRect(1, 1)
                        }, 200, true, null));

        mAnimations.put(Enemy.AnimationId.RIGHT,
                new SpriteSheetAnimation(spriteSheet.getBitmap(),
                        new Rect[]{
                                spriteSheet.getRect(2, 0),
                                spriteSheet.getRect(2, 1),
                                spriteSheet.getRect(2, 2),
                                spriteSheet.getRect(2, 1)
                        }, 200, true, null));

        mAnimations.put(Enemy.AnimationId.UP,
                new SpriteSheetAnimation(
                        spriteSheet.getBitmap(),
                        new Rect[]{
                                spriteSheet.getRect(3, 0),
                                spriteSheet.getRect(3, 1),
                                spriteSheet.getRect(3, 2),
                                spriteSheet.getRect(3, 1)
                        }, 200, true, null));

        return createEnemy(
                new GenericSprite<>(mAnimations, Enemy.AnimationId.RIGHT),
                CRAWLING_WIDTH, CRAWLING_HEIGHT, CRAWLING_WEIGHT,
                CRAWLING_HEALTH,
                CRAWLING_DAMAGE,
                CRAWLING_ATTACK_RANGE, CRAWLING_ATTACK_DELAY);
    }

    protected static Enemy createEnemy(Sprite<Enemy.AnimationId> sprite, float width, float height, int weight,
                                       int health, int damage, int attackRange, int attackDelay) {
        return new Enemy(sprite, width, height, weight, health, damage, attackRange, attackDelay);
    }
}
