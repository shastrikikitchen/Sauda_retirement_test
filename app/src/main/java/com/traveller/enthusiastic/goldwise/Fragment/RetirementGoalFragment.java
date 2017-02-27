package com.traveller.enthusiastic.goldwise.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.traveller.enthusiastic.goldwise.Adapter.RetirementFragmentAdaptor;
import com.traveller.enthusiastic.goldwise.R;
import com.traveller.enthusiastic.goldwise.helper.BaseFragment;
import com.traveller.enthusiastic.appUtill.ASCIILOG;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by sauda on 25/02/17.
 */

public class RetirementGoalFragment extends TabBarFragment {
    private BaseFragment listFragment;
    private String[] categories;


    private HashMap<Integer, WeakReference<Fragment>> fragMap = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.pager.setCurrentItem(0);
    }

    @Override
    protected FragmentPagerAdapter createTabContentAdapter() {
        categories = getActivity().getResources().getStringArray(R.array.categories);
        RetirementFragmentAdaptor mPagerAdapter = new RetirementFragmentAdaptor(getChildFragmentManager(), getActivity(), this, categories);
        pager.setOffscreenPageLimit(1);
        return mPagerAdapter;    }

    @Override
    public void onTabSelected(int position) {
        if (fragMap.get(position) != null)
            if(position == 0) {
                listFragment = (OverViewFragment)fragMap.get(position).get();
            }else if (position == 1){
                listFragment = (FundsFragment)fragMap.get(position).get();
            }
    }

    public Fragment getPagerFragment(int position) {
        Bundle extra = null;
        if (getArguments() == null) {
            extra = new Bundle();
        } else {
            extra = new Bundle(getArguments());
        }
        if(position == 0) {
            ASCIILOG.d("Position of Tab is: " + position + " mSelectedCategory :: ");
            OverViewFragment cFragment = new OverViewFragment();
            cFragment.setArguments(extra);
            fragMap.put(position, new WeakReference<Fragment>(cFragment));
            return cFragment;
        }else {
            FundsFragment fundsFragment = new FundsFragment();
            fundsFragment.setArguments(extra);
            fragMap.put(position,new WeakReference<Fragment>(fundsFragment));
            return fundsFragment;
        }
    }
    public void setMargin(){
        if(mSlidingTabs!=null)
            mSlidingTabs.setMarginForTabs();
    }
}
