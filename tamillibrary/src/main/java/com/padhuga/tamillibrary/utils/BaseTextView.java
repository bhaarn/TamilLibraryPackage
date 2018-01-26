package com.padhuga.tamillibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.padhuga.tamillibrary.R;

public class BaseTextView extends AppCompatTextView {

    private SharedPreferences sharedPref;

    public BaseTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        super.setTextSize(retrieveFontSize(context));
        super.setTextColor(Color.parseColor(retrieveTextTheme(context)));
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setTextSize(retrieveFontSize(context));
        super.setTextColor(Color.parseColor(retrieveTextTheme(context)));
    }

    public BaseTextView(Context context) {
        super(context);
        super.setTextSize(retrieveFontSize(context));
        super.setTextColor(Color.parseColor(retrieveTextTheme(context)));
    }

    private int retrieveFontSize(Context context) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getInt(Constants.PREF_COMMON_TEXT_SIZE, -1);
    }

    private String retrieveTextTheme(Context context) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return "#" + Integer.toHexString(sharedPref.getInt("text_color_preference", ContextCompat.getColor(context, R.color.colorListViewHeaderText)));
    }
}
