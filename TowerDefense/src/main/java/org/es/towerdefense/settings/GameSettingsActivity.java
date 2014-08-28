package org.es.towerdefense.settings;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import org.es.towerdefense.R;

import java.util.List;

/**
 * Created by Elsha on 01/03/14.
 */
public class GameSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preferences_headers, target);
    }

    /**
     * Subclasses should override this method and verify
     * that the given fragment is a valid type to be attached to this activity.<br />
     *
     * The default implementation returns true for apps built for android:targetSdkVersion older than KITKAT (API 19).
     * For later versions, it will throw an exception.
     */
    @Override
    protected boolean isValidFragment(String fragmentName) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return true;
        }

        return HUDSettings.class.getName().equals(fragmentName);
    }
}
