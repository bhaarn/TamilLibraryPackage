package com.padhuga.tamillibrary.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.padhuga.tamillibrary.R;
import com.padhuga.tamillibrary.adapters.SectionPagerAdapter;

import static com.padhuga.tamillibrary.activities.MenuLogic.parentModel;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initialize(this);
    }

    private void Initialize(Activity activity) {
        initializeAds();

        SectionPagerAdapter mSectionsPagerAdapter = new SectionPagerAdapter(((FragmentActivity)activity).getSupportFragmentManager(), parentModel);
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void initializeAds() {
        MobileAds.initialize(this, getResources().getString(R.string.ad_id));
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("66E56BD85B959A0701EA3C5F7D32E19D")
                .build();
        mAdView.loadAd(adRequest);
        Boolean b = adRequest.isTestDevice(this);
        Log.d("Bharani", b.toString());
    }
}

