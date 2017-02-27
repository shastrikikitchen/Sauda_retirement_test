package com.traveller.enthusiastic.goldwise.helper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.traveller.enthusiastic.goldwise.Fragment.RetirementGoalFragment;
import com.traveller.enthusiastic.goldwise.Fragment.TabBarFragment;
import com.traveller.enthusiastic.goldwise.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sauda on 25/02/17.
 */

public class NavigationHelper {
    FragmentActivity mActivity;
    private String currFragName;

    public NavigationHelper(FragmentActivity activity) {
        this.mActivity = activity;

    }

    private List<Fragment> getFragmentStackInContainer() {
        List<Fragment> fragments = mActivity.getSupportFragmentManager().getFragments();
        List<Fragment> result = new ArrayList<>();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f != null && f.getId() == R.id.contentContainer) {
                    result.add(f);
                }
            }
        }
        return result;
    }

    public Fragment gotoFragment(String tag, Bundle b) {
        Fragment tagFragment = new RetirementGoalFragment();
        List<Fragment> fragmentStack = getFragmentStackInContainer();
        if (b != null) {
            tagFragment.setArguments(b);
        }
        addFragment(tagFragment, tag);
        currFragName = tag;
        return tagFragment;
    }

    private void addFragment(Fragment newFrag, String tag) {
        try {

            if (newFrag != null) {
                mActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.contentContainer, newFrag, tag)
                        .commitAllowingStateLoss();
                mActivity.supportInvalidateOptionsMenu();
            }
        } catch (Exception e) {
            // Log.e(String.valueOf(Log.DEBUG),"exception while adding frag in home activity");
        }
    }

    public void popFragment(Fragment frag) {
        if (mActivity != null) {
            mActivity.getSupportFragmentManager().beginTransaction().remove(frag).commit();
        }
    }

    private int getFragmentLevel(Fragment fragment) {
        if (fragment instanceof RetirementGoalFragment)
            return 0;
        return -1;
    }

}
