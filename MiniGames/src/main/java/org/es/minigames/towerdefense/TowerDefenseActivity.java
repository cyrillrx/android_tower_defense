package org.es.minigames.towerdefense;

import android.app.Activity;
import android.os.Bundle;

import org.es.minigames.BaseGameActivity;
import org.es.minigames.R;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class TowerDefenseActivity extends BaseGameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tower_defense);
    }

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {

    }
}
