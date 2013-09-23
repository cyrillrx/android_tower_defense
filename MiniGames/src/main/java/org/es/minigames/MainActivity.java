package org.es.minigames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import org.es.minigames.platform.PlatformActivity;
import org.es.minigames.scrollingbackgrounds.ScrollingBackgroundActivity;


public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.btn_scrolling_bg)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_platform)).setOnClickListener(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.scrolling_bg, menu);
//        return true;
//    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_scrolling_bg:
                startActivity(new Intent(getApplicationContext(), ScrollingBackgroundActivity.class));
                break;

            case R.id.btn_platform:
                startActivity(new Intent(getApplicationContext(), PlatformActivity.class));
                break;
        }
    }
}