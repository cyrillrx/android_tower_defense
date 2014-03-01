package org.es.towerdefense;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.es.towerdefense.settings.GameSettingsActivity;
import org.es.utils.BaseGameActivity;


public class MainActivity extends BaseGameActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_settings).setOnClickListener(this);

        findViewById(R.id.btn_demo).setOnClickListener(this);
        findViewById(R.id.btn_single_player).setOnClickListener(this);

        boolean enable = isSignedIn();

        Button multiplayer = (Button) findViewById(R.id.btn_multiplayer);
        multiplayer.setOnClickListener(this);
        multiplayer.setEnabled(enable);

        Button achievements = (Button) findViewById(R.id.btn_achievements);
        achievements.setOnClickListener(this);
        multiplayer.setEnabled(enable);

        Button highScore = (Button)  findViewById(R.id.btn_high_score);
        highScore.setOnClickListener(this);
        multiplayer.setEnabled(enable);

        findViewById(R.id.button_sign_in).setOnClickListener(this);
        findViewById(R.id.button_sign_out).setOnClickListener(this);
    }

    // Shows the "sign in" bar (explanation and button).
    private void showSignInBar() {
        findViewById(R.id.sign_in_bar).setVisibility(View.VISIBLE);
        findViewById(R.id.sign_out_bar).setVisibility(View.GONE);
    }

    // Shows the "sign out" bar (explanation and button).
    private void showSignOutBar() {
        findViewById(R.id.sign_in_bar).setVisibility(View.GONE);
        findViewById(R.id.sign_out_bar).setVisibility(View.VISIBLE);
    }

    /**
     * Called to notify us that sign in failed. Notice that a failure in sign in is not
     * necessarily due to an error; it might be that the user never signed in, so our
     * attempt to automatically sign in fails because the user has not gone through
     * the authorization flow. So our reaction to sign in failure is to show the sign in
     * button. When the user clicks that button, the sign in process will start/resume.
     */
    @Override
    public void onSignInFailed() {
        // Sign-in has failed. So show the user the sign-in button
        // so they can click the "Sign-in" button.
        showSignInBar();

        findViewById(R.id.btn_multiplayer).setEnabled(false);
        findViewById(R.id.btn_achievements).setEnabled(false);
        findViewById(R.id.btn_high_score).setEnabled(false);
    }

    /**
     * Called to notify us that sign in succeeded. We react by loading the loot from the
     * cloud and updating the UI to show a sign-out button.
     */
    @Override
    public void onSignInSucceeded() {
        // Sign-in worked!
        showSignOutBar();

        findViewById(R.id.btn_multiplayer).setEnabled(true);
        findViewById(R.id.btn_achievements).setEnabled(true);
        findViewById(R.id.btn_high_score).setEnabled(true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_settings:
                startActivity(new Intent(getApplicationContext(), GameSettingsActivity.class));
                break;

            case R.id.btn_demo:
                startActivity(new Intent(getApplicationContext(), InGameActivity.class));
                break;

            case R.id.btn_single_player:
                break;

            case R.id.btn_multiplayer:
                break;

            case R.id.btn_achievements:
                break;

            case R.id.btn_high_score:
                break;

            case R.id.button_sign_in:
                // start the sign-in flow
                beginUserInitiatedSignIn();
                break;

            case R.id.button_sign_out:
                // sign out.
                signOut();
                showSignInBar();
                break;
        }
    }
}