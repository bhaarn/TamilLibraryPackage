package com.padhuga.tamillibrarypackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.padhuga.tamillibrary.activities.MainActivity;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(getResources().getString(R.string.json_file_name_key), getResources().getString(R.string.json_file_name));
        intent.putExtra(getResources().getString(R.string.list_type), getResources().getString(R.string.list_view_type));
        startActivity(intent);
        finish();
    }
}
