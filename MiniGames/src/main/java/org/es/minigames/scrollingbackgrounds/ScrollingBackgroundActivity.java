package org.es.minigames.scrollingbackgrounds;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.es.minigames.R;

/**
 * @author Cyril Leroux
 *         Created on 18/09/13.
 */
public class ScrollingBackgroundActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_bg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scrolling_bg, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        ScrollingBackgroundView view = (ScrollingBackgroundView) findViewById(R.id.scrolling_bg_view);

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
