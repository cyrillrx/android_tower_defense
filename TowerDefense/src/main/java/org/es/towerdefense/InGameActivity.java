package org.es.towerdefense;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import org.es.utils.BaseGameActivity;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class InGameActivity extends BaseGameActivity implements View.OnSystemUiVisibilityChangeListener {

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
