package org.es.towerdefense.process;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import org.es.engine.graphics.utils.DrawingParam;
import org.es.towerdefense.battleground.Battleground;
import org.es.towerdefense.battleground.BattlegroundDAO;
import org.es.towerdefense.object.Player;
import org.es.towerdefense.object.Wave;
import org.es.towerdefense.unit.Destructible;
import org.es.towerdefense.unit.Enemy;
import org.es.towerdefense.unit.Tower;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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
    private int mSurfaceWidth;
    private int mSurfaceHeight;

    private boolean mPaused;
    private long mPauseStartTime;

    public GameMgr(Context context) {
        mContext = context;
        mDrawingParam = new DrawingParam();
        mPaused = false;
        mPauseStartTime = 0;

        mSurfaceWidth = 0;
        mSurfaceHeight = 0;

        mEnemies = new HashSet<>();
        mTowers = new HashSet<>();
        mGarbage = new HashSet<>();

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
        Map<Enemy.Type, Integer> attakers = new HashMap<>();
        attakers.put(Enemy.Type.CRAWLING, 20);

        Queue<Wave> waves = new ArrayDeque<>();
        waves.add(new Wave(attakers, 40000));

        mWaveManager = new WaveManager(waves, mBattleground, mEnemies, mContext.getResources());

        mWaveManager.spawnEnemy();
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
                mGarbage.add(enemy);
                mEnemies.remove(enemy);
                // spawn a new enemy
                mWaveManager.spawnEnemy();
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

        // Draw the elements
        // It is important to draw towers first if there are flying enemies.
        for (Tower tower : mTowers) {
            tower.drawHUD(canvas, param);
            tower.drawDebugHUD(canvas, param, mDebugPaint);
        }
        for (Enemy enemy : mEnemies) {
            enemy.drawHUD(canvas, param);
            enemy.drawDebugHUD(canvas, param, mDebugPaint);
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
            final String versionName = "Version Name: " + info.versionName;
            Rect textBoundsCode = new Rect();
            mDebugPaint.getTextBounds(versionCode, 0, versionCode.length(), textBoundsCode);
            Rect textBoundsName = new Rect();
            mDebugPaint.getTextBounds(versionName, 0, versionName.length(), textBoundsName);

            float yCode = mSurfaceHeight - textBoundsCode.height();
            float yName = yCode - textBoundsName.height();

            canvas.drawText(versionName, 0, yName, mDebugPaint);
            canvas.drawText(versionCode, 0, yCode, mDebugPaint);

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
        mSurfaceWidth = surfaceWidth;
        mSurfaceHeight = surfaceHeight;
        mBattleground.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
    }

    // TODO move into utils
    private static void drawCenteredText(String text, Canvas canvas, float centerX, float centerY, Paint paint) {
        Rect textBounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBounds);
        float posX = centerX - textBounds.width() / 2f;
        float posY = centerY - textBounds.height() / 2f;
        canvas.drawText(text, posX, posY, paint);
    }
}
