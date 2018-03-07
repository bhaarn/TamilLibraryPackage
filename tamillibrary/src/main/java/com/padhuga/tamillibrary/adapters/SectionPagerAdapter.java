package com.padhuga.tamillibrary.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.padhuga.tamillibrary.activities.MainFragment;
import com.padhuga.tamillibrary.models.ParentModel;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private final ParentModel parentModel;
    private final int listType;

    public SectionPagerAdapter(FragmentManager fm, ParentModel parentModel, int listType) {
        super(fm);
        this.parentModel = parentModel;
        this.listType = listType;
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.newInstance(listType, position);
    }

    @Override
    public int getCount() {
        return parentModel.getData().getType().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.parentModel.getData().getType().get(position).getTitle();
    }
}
