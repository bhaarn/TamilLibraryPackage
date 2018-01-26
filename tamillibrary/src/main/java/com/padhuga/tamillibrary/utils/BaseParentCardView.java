package com.padhuga.tamillibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.padhuga.tamillibrary.R;

public class BaseParentCardView extends CardView{

    public BaseParentCardView(Context context) {
        super(context);
        super.setCardBackgroundColor(Color.parseColor(retrieveParentCardTheme(context)));
    }

    public BaseParentCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setCardBackgroundColor(Color.parseColor(retrieveParentCardTheme(context)));
    }

    public BaseParentCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setCardBackgroundColor(Color.parseColor(retrieveParentCardTheme(context)));
    }

    private String retrieveParentCardTheme(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return "#" + Integer.toHexString(sharedPref.getInt("parent_card_color_preference", ContextCompat.getColor(context, R.color.colorBottomBackground)));
    }
}
