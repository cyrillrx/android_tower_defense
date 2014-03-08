package org.es.towerdefense.component;

import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.RectF;

import org.es.towerdefense.R;
import org.es.towerdefense.object.Player;
import org.es.towerdefense.process.GameMgr;
import org.es.utils.DrawTextUtils;

/**
 * @author Cyril Leroux
 *         Created 06/03/14.
 */
public class HudHelper {

    private static HudToggleButton PLAY_PAUSE;

    private static HudText SCORE;
    private static HudText MONEY;
    private static HudText HEALTH;

    // TODO init with a resource manager
    public static void initMainHud(HUD mainHud, float surfaceWidth, float surfaceHeight,
                                   final Player player, final GameMgr gameMgr, final Resources resources) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(30f);

        final float margin = 0.02f;
        final float buttonSide = 0.1f;

        //
        // Play pause button
        //

        RectF playPauseBounds = new RectF(0, 0, surfaceHeight * buttonSide, surfaceHeight * buttonSide);
        playPauseBounds.offset(surfaceWidth - playPauseBounds.width() - margin, margin);

        if (PLAY_PAUSE == null) {
            PLAY_PAUSE = new HudToggleButton(resources,
                    R.drawable.ic_pause, R.drawable.ic_pause,
                    R.drawable.ic_play, R.drawable.ic_play, playPauseBounds) {
                @Override
                protected void onClick() { gameMgr.pause(); }

                @Override
                protected void onClick2() { gameMgr.resume(); }
            };
            mainHud.addControl(PLAY_PAUSE);
        } else {
            PLAY_PAUSE.setBounds(playPauseBounds);
        }


        final float textTop = buttonSide / 2f + margin;

        //
        // Score
        //

        if (SCORE == null) {
            SCORE = new HudText(resources.getString(R.string.hud_score), margin, textTop,
                    DrawTextUtils.HorizontalAlign.RIGHT, DrawTextUtils.VerticalAlign.CENTER, paint) {
                @Override
                protected String getText() {
                    return String.valueOf(player.getScore());
                }
            };
            mainHud.addControl(SCORE);
        } else {
            SCORE.setPosition(margin, textTop);
        }

        //
        // Money
        //

        if (MONEY == null) {
            MONEY = new HudText(resources.getString(R.string.hud_money), 1f / 3f, textTop,
                    DrawTextUtils.HorizontalAlign.RIGHT, DrawTextUtils.VerticalAlign.CENTER, paint) {
                @Override
                protected String getText() {
                    return String.valueOf(player.getMoney());
                }
            };
            mainHud.addControl(MONEY);
        } else {
            MONEY.setPosition(1f / 3f, textTop);
        }

        //
        // Health
        //

        if (HEALTH == null) {
            HEALTH = new HudText(resources.getString(R.string.hud_health), 2f / 3f, textTop,
                    DrawTextUtils.HorizontalAlign.RIGHT, DrawTextUtils.VerticalAlign.CENTER, paint) {
                @Override
                protected String getText() {
                    return String.valueOf(player.getHealth());
                }
            };
            mainHud.addControl(HEALTH);
        } else {
            HEALTH.setPosition(2f / 3f, textTop);
        }
    }
}
