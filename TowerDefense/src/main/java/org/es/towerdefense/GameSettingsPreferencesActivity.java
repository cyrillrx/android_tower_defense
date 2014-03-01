package org.es.towerdefense;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Button;

import org.es.towerdefense.settings.HUDSettings;

import java.util.List;

/**
 * Created by Elsha on 01/03/14.
 */
public class GameSettingsPreferencesActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add a button to the header list.
        if (hasHeaders()) {
            Button button = new Button(this);
            button.setText("Back");
            button.setWidth(100);
            setListFooter(button);
        }

        //getFragmentManager().beginTransaction().replace(android.R.id.content, new HUDSettings()).commit();
    }

    /**
     * Populate the activity with the top-level headers.
     */

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preferences_headers, target);
    }
}
