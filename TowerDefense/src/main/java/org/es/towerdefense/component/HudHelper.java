package org.es.towerdefense.component;

import android.content.res.Resources;
import android.graphics.Paint;

import org.es.towerdefense.R;
import org.es.towerdefense.object.Player;
import org.es.towerdefense.process.GameMgr;
import org.es.utils.DrawTextUtils;

/**
 * @author Cyril Leroux
 *         Created 06/03/14.
 */
public class HudHelper {

    public static void initMainHud(HUD mainHud, final Player player, final GameMgr gameMgr, final Resources resources) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(30f);


        final float margin = 0.02f;
        final float buttonSide = 0.1f;

        final float textTop = buttonSide / 2f + margin;

        final HudText score = new HudText(resources.getString(R.string.hud_score), margin, textTop,
                DrawTextUtils.HorizontalAlign.RIGHT, DrawTextUtils.VerticalAlign.CENTER, paint) {
            @Override
            public String getText() {
                return String.valueOf(player.getScore());
            }
        };
        mainHud.addControl(score);

        final HudText money = new HudText(resources.getString(R.string.hud_money), 1f/3f, textTop,
                DrawTextUtils.HorizontalAlign.RIGHT, DrawTextUtils.VerticalAlign.CENTER, paint) {
            @Override
            public String getText() {
                return String.valueOf(player.getMoney());
            }
        };
        mainHud.addControl(money);

        final HudText health = new HudText(resources.getString(R.string.hud_health), 2f/3f, textTop,
                DrawTextUtils.HorizontalAlign.RIGHT, DrawTextUtils.VerticalAlign.CENTER, paint) {
            @Override
            public String getText() {
                return String.valueOf(player.getHealth());
            }
        };
        mainHud.addControl(health);
    }
}
