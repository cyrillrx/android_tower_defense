package org.es.towerdefense.settings;

/**
 * Created by Elsha on 01/03/14.
 */

import android.os.Bundle;
import android.preference.PreferenceFragment;

import org.es.towerdefense.R;

/**
 * This fragment shows the preferences for the first header.
 */
public class HUDSettings extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make sure default values are applied.  In a real app, you would
        // want this in a shared function that is used to retrieve the
        // SharedPreferences wherever they are needed.
        //PreferenceManager.setDefaultValues(getActivity(), R.xml.display_preferences, false);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.display_preferences);
    }
}