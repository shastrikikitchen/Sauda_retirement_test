package com.traveller.enthusiastic.goldwise.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.traveller.enthusiastic.goldwise.Fragment.OverViewProjectionFragment;
import com.traveller.enthusiastic.networkUtils.Response.GoalWiseResponse;

import java.util.List;

/**
 * Created by sauda on 25/02/17.
 */

public class ProjectionPagerAdaptor extends FragmentStatePagerAdapter {
    private  int height;
    private  GoalWiseResponse response;
    public ProjectionPagerAdaptor(FragmentManager fragmentManager,GoalWiseResponse response) {
            super(fragmentManager);
        this.response = response;
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public Fragment getItem(int position) {
            OverViewProjectionFragment fragment = new OverViewProjectionFragment();
            fragment.getHeignt();
            fragment.setResponse(response);
            return fragment;
        }

    public int getHeight() {
        return height;
    }

}
