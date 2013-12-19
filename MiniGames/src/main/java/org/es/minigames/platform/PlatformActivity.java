package org.es.minigames.platform;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.es.minigames.R;

public class PlatformActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.platform, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        PlatformView view = (PlatformView) findViewById(R.id.platform_view);

        switch (item.getItemId()) {

            case R.id.fps_10:
                view.setFrameRate(10);
                break;

            case R.id.fps_20:
                view.setFrameRate(20);
                break;

            case R.id.fps_30:
                view.setFrameRate(30);
                break;

            case R.id.fps_40:
                view.setFrameRate(40);
                break;

            case R.id.fps_60:
                view.setFrameRate(60);
                break;

            case R.id.fps_100:
                view.setFrameRate(100);
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}
