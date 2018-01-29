package com.padhuga.tamillibrary.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.padhuga.tamillibrary.R;
import com.padhuga.tamillibrary.adapters.SectionDetailAdapter;
import com.padhuga.tamillibrary.utils.Constants;

import static com.padhuga.tamillibrary.activities.MenuLogic.parentModel;

public class DetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int position = getIntent().getIntExtra(Constants.ARG_SECTION_POSITION, -1);
        int groupPosition = getIntent().getIntExtra(Constants.ARG_PARENT_POSITION, -1);
        int childPosition = getIntent().getIntExtra(Constants.ARG_CHILD_POSITION, -1);
        Initialize(this, position, groupPosition, childPosition);
    }

    private void Initialize(Activity activity, int position, int groupPosition, int childPosition) {
        initializeAds();

        SectionDetailAdapter mSectionDetailAdapter = new SectionDetailAdapter(((FragmentActivity)activity).getSupportFragmentManager(), parentModel, position, groupPosition);
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionDetailAdapter);
        mViewPager.setCurrentItem(childPosition);
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
