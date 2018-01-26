package com.padhuga.tamillibrarypackage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.padhuga.tamillibrary.activities.MenuLogic;
import com.padhuga.tamillibrary.activities.MyView;
import com.padhuga.tamillibrary.models.ParentModel;
import com.padhuga.tamillibrary.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private MenuLogic menuLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = new MyView(this);
        setContentView(R.layout.activity_main);
        menuLogic = new MenuLogic();
        ParentModel parentModel = menuLogic.readJSONFromAssetsAndConvertTogson(this, getResources().getString(R.string.json_file_name));
        menuLogic.handleIntent(this, getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        menuLogic.handleIntent(this, intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_app_search:
                menuLogic.performSearch(MainActivity.this, item);
                return true;
            case R.id.action_app_rate:
                menuLogic.rateApp(MainActivity.this, getPackageName());
                return true;
            case R.id.action_font_size:
                menuLogic.changeFont(MainActivity.this);
                return true;
            case R.id.action_theme_style:
                menuLogic.ChangeTheme(MainActivity.this);
                return true;
            case R.id.action_feature_help:
                menuLogic.showHelp(MainActivity.this);
                return true;
            case R.id.action_app_share:
                menuLogic.shareApp(MainActivity.this, getPackageName(),
                        getResources().getString(R.string.app_name), getResources().getString(R.string.share_message));
                return true;
            case R.id.action_more_apps:
                menuLogic.moreApps(MainActivity.this);
                return true;
            case R.id.action_about:
                menuLogic.aboutUs(MainActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Boolean themeChange = data.getBooleanExtra(Constants.PREF_THEME_CHANGE, false);
                if(themeChange) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recreate();
                        }
                    }, 100);
                }
            }
        }
    }
}
