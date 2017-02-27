package com.traveller.enthusiastic.goldwise.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.traveller.enthusiastic.goldwise.Fragment.RetirementGoalFragment;

/**
 * Created by sauda on 25/02/17.
 */

public class RetirementFragmentAdaptor extends FragmentPagerAdapter {
    private String[] goalCategories;
    private Context context;
    private RetirementGoalFragment fragment;
    public RetirementFragmentAdaptor(FragmentManager fm, Context context, RetirementGoalFragment fragment, String [] categories) {
        super(fm);
        this.context = context;
        this.fragment = fragment;
        this.goalCategories = categories;


    }
    public void setOrderCategories(String[] goalCategories) {
        this.goalCategories = goalCategories;
    }

    @Override
    public Fragment getItem(int position) {
        return fragment.getPagerFragment(position);
    }

    @Override
    public int getCount() {
        return goalCategories.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return goalCategories[position];
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}

