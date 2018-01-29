package com.padhuga.tamillibrary.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.padhuga.tamillibrary.activities.DetailsFragment;
import com.padhuga.tamillibrary.models.ParentModel;

public class SectionDetailAdapter extends FragmentPagerAdapter {

    private final ParentModel parentModel;
    private final int position;
    private final int groupPosition;

    public SectionDetailAdapter(FragmentManager fm, ParentModel parentModel, int position, int groupPosition) {
        super(fm);
        this.parentModel = parentModel;
        this.position = position;
        this.groupPosition = groupPosition;
    }

    @Override
    public Fragment getItem(int position) {
        return DetailsFragment.newInstance(this.position, groupPosition, position);
    }

    @Override
    public int getCount() {
        return parentModel.getData().getType().get(position).getType().get(groupPosition).getType().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.parentModel.getData().getType().get(position).getTitle();
    }
}
