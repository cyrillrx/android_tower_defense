package org.es.towerdefense;

import android.os.Bundle;

import org.es.utils.BaseGameActivity;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class InGameActivity extends BaseGameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
    }

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {

    }

}
