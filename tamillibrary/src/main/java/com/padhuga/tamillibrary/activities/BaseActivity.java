package com.padhuga.tamillibrary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.padhuga.tamillibrary.R;
import com.padhuga.tamillibrary.models.ParentModel;
import com.padhuga.tamillibrary.utils.Constants;

public class BaseActivity extends AppCompatActivity {

    private MenuLogic menuLogic;
    static String jsonFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = new MyView(this);
        setContentView(R.layout.activity_main_layout);
        menuLogic = new MenuLogic();
        if(getIntent() != null) {
            jsonFileName = getIntent().getExtras().getString(Constants.ARG_JSON_FILE_NAME);
            if(jsonFileName != null){
                ParentModel parentModel = menuLogic.readJSONFromAssetsAndConvertTogson(this, jsonFileName);
            }
        }
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
        if (item.getItemId() == R.id.action_app_search) {
            menuLogic.performSearch(BaseActivity.this, item);
            return true;
        } else if (item.getItemId() == R.id.action_app_rate) {
            menuLogic.rateApp(BaseActivity.this, getPackageName());
            return true;
        } else if (item.getItemId() == R.id.action_font_size) {
            menuLogic.changeFont(BaseActivity.this);
            return true;
        } else if (item.getItemId() == R.id.action_theme_style) {
            menuLogic.ChangeTheme(BaseActivity.this);
            return true;
        } else if (item.getItemId() == R.id.action_feature_help) {
            menuLogic.showHelp(BaseActivity.this);
            return true;
        } else if (item.getItemId() == R.id.action_app_share) {
            menuLogic.shareApp(BaseActivity.this, getPackageName(),
                    getResources().getString(R.string.app_name), getResources().getString(R.string.share_message));
            return true;
        } else if (item.getItemId() == R.id.action_more_apps) {
            menuLogic.moreApps(BaseActivity.this);
            return true;
        } else if (item.getItemId() == R.id.action_about) {
            menuLogic.aboutUs(BaseActivity.this);
            return true;
        } else
            return super.onOptionsItemSelected(item);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Boolean themeChange = data.getBooleanExtra(Constants.PREF_THEME_CHANGE, false);
                if (themeChange) {
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

