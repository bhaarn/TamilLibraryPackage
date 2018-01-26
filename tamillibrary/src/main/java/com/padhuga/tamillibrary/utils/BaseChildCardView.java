package com.padhuga.tamillibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.padhuga.tamillibrary.R;

public class BaseChildCardView extends CardView{

    public BaseChildCardView(Context context) {
        super(context);
        super.setCardBackgroundColor(Color.parseColor(retrieveChildCardTheme(context)));
    }

    public BaseChildCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setCardBackgroundColor(Color.parseColor(retrieveChildCardTheme(context)));
    }

    public BaseChildCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setCardBackgroundColor(Color.parseColor(retrieveChildCardTheme(context)));
    }

    private String retrieveChildCardTheme(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return "#" + Integer.toHexString(sharedPref.getInt("child_card_color_preference", ContextCompat.getColor(context, R.color.colorBackground)));
    }
}
