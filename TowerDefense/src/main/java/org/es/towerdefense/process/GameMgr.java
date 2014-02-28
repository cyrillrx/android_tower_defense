package org.es.towerdefense.process;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;

import org.es.engine.graphics.utils.DrawingParam;
import org.es.towerdefense.battleground.Battleground;
import org.es.towerdefense.battleground.BattlegroundDAO;
import org.es.towerdefense.object.Player;
import org.es.towerdefense.object.Wave;
import org.es.towerdefense.unit.Destructible;
import org.es.towerdefense.unit.Enemy;
import org.es.towerdefense.unit.Tower;
import org.es.utils.DrawTextUtils;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cyril Leroux
 *         Created on 13/02/14.
 */
public class GameMgr {

    private final Context mContext;
    /** The parameters used to draw the elements on the screen. */
    private final DrawingParam mDrawingParam;
    private final Paint mDebugPaint;

    private final Player mPlayer;
    private final Battleground mBattleground;
    private final WaveManager mWaveManager;

    // TODO mDrawable should evolve to a list of towers or static elements (handle barricades)
    //private final Set<DrawableElement> mDrawables;
    private final Set<Enemy> mEnemies;
    private final Set<Tower> mTowers;
    private final Set<Destructible> mGarbage;

    private boolean mPaused;
    private long mPauseStartTime;

    public GameMgr(Context context) {
        mContext = context;
        mDrawingParam = new DrawingParam();
        mPaused = false;
        mPauseStartTime = 0;

        // Creating concurrent sets :
        mEnemies = Collections.newSetFromMap(new ConcurrentHashMap<Enemy, Boolean>());
        mTowers = Collections.newSetFromMap(new ConcurrentHashMap<Tower, Boolean>());
        mGarbage = Collections.newSetFromMap(new ConcurrentHashMap<Destructible, Boolean>());

        mDebugPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDebugPaint.setAntiAlias(true);
        mDebugPaint.setStrokeWidth(1f);
        mDebugPaint.setStyle(Paint.Style.FILL);
        mDebugPaint.setTextSize(20f);

        final Resources resources = context.getResources();

        mPlayer = new Player(20, 1000);
        // Initialize the battleground
        // TODO Load from file
        mBattleground = BattlegroundDAO.loadDebugBattleGround(resources, mDrawingParam, mTowers);

        // Initialize the wave manager
        Map<Enemy.Type, Integer> attackers = new HashMap<>();
        attackers.put(Enemy.Type.CRAWLING, 20);

        Queue<Wave> waves = new ArrayDeque<>();
        waves.add(new Wave(attackers, 40000));

        mWaveManager = new WaveManager(waves, mBattleground, mEnemies, mContext.getResources());
    }

    public void update() {
        if (mPaused) {
            return;
        }
        // mBattleground.update();
        mWaveManager.update();


        for (Tower tower : mTowers) {
            if (tower.isOutOfPlay()) {
                continue;
            }
            tower.update(mEnemies);
        }

        for (Enemy enemy : mEnemies) {
            if (enemy.isOutOfPlay()) {
                if (enemy.isDead()) {
                    mPlayer.incrementScore(100);
                } else if (enemy.isFinisher()) {
                    mPlayer.decrementHealth(1);
                }
                mGarbage.add(enemy);
                mEnemies.remove(enemy);
                continue;
            }
            // TODO not always true
            enemy.update(mBattleground, true);
        }
    }

    public void draw(Canvas canvas) {

        // Draw background
        mBattleground.draw(canvas, mDrawingParam);

        // Draw the elements
        // It is important to draw towers first if there are flying enemies.
        for (Tower tower : mTowers) {
            tower.draw(canvas, mDrawingParam);
        }
        for (Enemy enemy : mEnemies) {
            enemy.draw(canvas, mDrawingParam);
        }

        // Draw animations (such as missiles).
        // TODO Draw the animations

        // Draw the main HUD
        drawHUD(canvas, mDrawingParam);
    }

    /** Pause prevents the update function to run. */
    public void pause() {
        if (mPaused) { return; }
        mPauseStartTime = System.currentTimeMillis();
        mPaused = true;
    }

    /**
     * Resumes all the time aware elements
     * and pass them the elapsed time so they can synchronize.
     */
    public void resume() {
        if (!mPaused) { return; }
        final long elapsedTime = System.currentTimeMillis() - mPauseStartTime;

        mWaveManager.onResume(elapsedTime);

        // Resume time aware elements.
        for (Tower tower : mTowers) {
            tower.onResume(elapsedTime);
        }
        for (Enemy enemy : mEnemies) {
            enemy.onResume(elapsedTime);
        }
        mPaused = false;
    }

    public boolean isPaused() { return mPaused; }

    /**
     * Draw all Head-up display.
     * <ul>
     * <li>Draw towers HUD</li>
     * <li>Draw enemies HUD</li>
     * <li>Draw main HUD</li>
     * </ul>
     */
    protected void drawHUD(Canvas canvas, DrawingParam param) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        // Draw the elements
        // It is important to draw towers first if there are flying enemies.
        for (Tower tower : mTowers) {
            tower.drawHUD(canvas, param);
            tower.drawDebugHUD(canvas, param, mDebugPaint, preferences);
        }
        for (Enemy enemy : mEnemies) {
            enemy.drawHUD(canvas, param);
            enemy.drawDebugHUD(canvas, param, mDebugPaint, preferences);
        }

        drawMainHUD(canvas);
        drawMainHUDDebug(canvas);
    }

    /**
     * Draw the main Head-up display.<br />
     * Scores, GUI, ...
     */
    protected void drawMainHUD(Canvas canvas) {
        // TODO Draw the main HUD

        Paint mainHUDPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainHUDPaint.setAntiAlias(true);
        mainHUDPaint.setStrokeWidth(1f);
        mainHUDPaint.setStyle(Paint.Style.FILL);
        mainHUDPaint.setTextSize(30f);

        final int canvasWidth = canvas.getWidth();

        final String scoreText = "Score " + mPlayer.getScore();
        DrawTextUtils.drawText(scoreText, canvas,
                (int) (canvasWidth / 4f), 10,
                DrawTextUtils.HorizontalAlign.CENTER, DrawTextUtils.VerticalAlign.BOTTOM, mainHUDPaint);

        final String healthText = "Health " + mPlayer.getHealth();
        DrawTextUtils.drawText(healthText, canvas,
                (int) (canvasWidth / 4f * 3f), 10,
                DrawTextUtils.HorizontalAlign.CENTER, DrawTextUtils.VerticalAlign.BOTTOM, mainHUDPaint);
    }

    // TODO update comment

    /**
     * Draw the main Head-up display.<br />
     * Scores, GUI, ...
     */
    protected void drawMainHUDDebug(Canvas canvas) {

        // Draw app version
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);

            final String versionCode = "Version Code: " + String.valueOf(info.versionCode);
            Rect textBoundsCode = DrawTextUtils.drawText(versionCode, canvas,
                    10, canvas.getHeight(),
                    DrawTextUtils.HorizontalAlign.RIGHT, DrawTextUtils.VerticalAlign.TOP, mDebugPaint);

            final String versionName = "Version Name: " + info.versionName;
            DrawTextUtils.drawText(versionName, canvas,
                    10, canvas.getHeight() - textBoundsCode.height(),
                    DrawTextUtils.HorizontalAlign.RIGHT, DrawTextUtils.VerticalAlign.TOP, mDebugPaint);

        } catch (PackageManager.NameNotFoundException e) { }

        // Draw
        for (Destructible destructible : mGarbage) {
            if (destructible.isDead()) {
                // TODO Code a ramaining text draw
                //drawCenteredText("Dead !", canvas, mBattleground.getCenterX(), mBattleground.getCenterY(), mDebugPaint);

            } else if (destructible instanceof Enemy && ((Enemy) destructible).isFinisher()) {
                // TODO Code a ramaining text draw
                //drawCenteredText("Finnisher !", canvas, mBattleground.getCenterX(), mBattleground.getCenterY(), mDebugPaint);
            }
        }
    }

    public void updateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        mBattleground.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
    }
}
