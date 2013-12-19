package org.es.minigames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.es.minigames.platform.PlatformActivity;
import org.es.minigames.scrollingbackgrounds.ScrollingBackgroundActivity;


public class MainActivity extends BaseGameActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_scrolling_bg).setOnClickListener(this);
        findViewById(R.id.btn_platform).setOnClickListener(this);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.scrolling_bg, menu);
//        return true;
//    }

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
    }

    /**
     * Called to notify us that sign in succeeded. We react by loading the loot from the
     * cloud and updating the UI to show a sign-out button.
     */
    @Override
    public void onSignInSucceeded() {
        // Sign-in worked!
        showSignOutBar();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_scrolling_bg:
                startActivity(new Intent(getApplicationContext(), ScrollingBackgroundActivity.class));
                break;

            case R.id.btn_platform:
                startActivity(new Intent(getApplicationContext(), PlatformActivity.class));
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