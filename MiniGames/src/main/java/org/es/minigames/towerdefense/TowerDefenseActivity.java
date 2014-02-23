package org.es.minigames.towerdefense;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import org.es.minigames.BaseGameActivity;
import org.es.minigames.R;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class TowerDefenseActivity extends BaseGameActivity {

    private View mDecorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tower_defense);

        mDecorView = getWindow().getDecorView();
        // Navigation bar hiding:  Backwards compatible to ICS.
        mDecorView.setOnSystemUiVisibilityChangeListener(
                new View.OnSystemUiVisibilityChangeListener() {
                    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                    @Override
                    public void onSystemUiVisibilityChange(int flags) {
                        //boolean visible = (flags & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
                        // controls.setVisibility(visible ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void hideSystemUI() {

        int visible = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            visible |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        // Status bar hiding: Backwards compatible to Kitkat
        if (Build.VERSION.SDK_INT >= 18) {
            visible |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        mDecorView.setSystemUiVisibility(visible);
    }

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {

    }
}
