package org.es.towerdefense.component;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import org.es.engine.graphics.drawable.DrawableElement;
import org.es.engine.graphics.utils.DrawingParam;
import org.es.towerdefense.R;
import org.es.towerdefense.object.Player;
import org.es.towerdefense.process.GameMgr;
import org.es.utils.DrawTextUtils;

/**
 * @author Cyril Leroux
 *         Created 02/03/14.
 */
public class HUD implements DrawableElement {

    private final Player mPlayer;
    private final GameMgr mGameMgr;

    private final HudButton mPause;
    private final HudButton mPlay;
    private final HudText mScore;
    private final HudText mMoney;
    private final HudText mHealth;

    // TODO init with a resource manager
    public HUD(Player player, GameMgr gameMgr, Resources resources) {

        mPlayer = player;
        mGameMgr = gameMgr;

        mPlay = new HudButton(resources, R.drawable.ic_play, R.drawable.ic_play, new RectF());
        mPause = new HudButton(resources, R.drawable.ic_pause, R.drawable.ic_pause, new RectF());


        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(30f);

        mScore = new HudText(resources.getString(R.string.hud_score), 0, 0, DrawTextUtils.HorizontalAlign.RIGHT, DrawTextUtils.VerticalAlign.CENTER, paint);
        mMoney = new HudText(resources.getString(R.string.hud_money), 0, 0, DrawTextUtils.HorizontalAlign.RIGHT, DrawTextUtils.VerticalAlign.CENTER, paint);
        mHealth = new HudText(resources.getString(R.string.hud_health), 0, 0, DrawTextUtils.HorizontalAlign.RIGHT, DrawTextUtils.VerticalAlign.CENTER, paint);
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {

        final float margin = surfaceHeight * 0.02f;

        RectF playPauseBounds = new RectF(0, 0, surfaceHeight * 0.1f, surfaceHeight * 0.1f);
        playPauseBounds.offset(surfaceWidth - playPauseBounds.width() - margin, margin);
        mPlay.setBounds(playPauseBounds);
        mPause.setBounds(playPauseBounds);

        mMoney.setPosition(margin, playPauseBounds.centerY());
        mScore.setPosition(surfaceWidth / 2f, playPauseBounds.centerY());
        mHealth.setPosition(surfaceWidth * 2f / 3f, playPauseBounds.centerY());
    }

    @Override
    public void draw(Canvas canvas, DrawingParam param) {

        if (mGameMgr.isPaused()) {
            mPlay.draw(canvas, null);
        } else {
            mPause.draw(canvas, null);
        }

        mScore.setText(String.valueOf(mPlayer.getScore()));
        mScore.draw(canvas, null);
        mMoney.setText(String.valueOf(mPlayer.getMoney()));
        mMoney.draw(canvas, null);
        mHealth.setText(String.valueOf(mPlayer.getHealth()));
        mHealth.draw(canvas, null);
    }

    /**
     * Intercepts event sent by the drawing thread.
     * Returns true if the event is consumed.
     * @param event
     * @return true if the event is consumed. False otherwise.
     */
    public boolean catchEvent(MotionEvent event) {

        if (mPause.getBounds().contains(event.getX(), event.getY())) {
            // Toggle pause
            if (mGameMgr.isPaused()) {
                mGameMgr.resume();
            } else {
                mGameMgr.pause();
            }
            return true;
        }
        return false;
    }

    @Override
    public float getPosX() { return 0; }

    @Override
    public float getPosY() { return 0; }

    @Override
    public void setPosition(float x, float y) { }

    @Override
    public void offsetPosition(float dx, float dy) { }

    @Override
    public float getWidth() { return 0; }

    @Override
    public float getHeight() { return 0; }
}
