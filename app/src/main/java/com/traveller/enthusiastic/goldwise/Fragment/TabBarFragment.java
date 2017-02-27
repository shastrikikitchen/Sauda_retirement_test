package com.traveller.enthusiastic.goldwise.Fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traveller.enthusiastic.goldwise.R;
import com.traveller.enthusiastic.goldwise.helper.BaseFragment;
import com.traveller.enthusiastic.appUtill.ASCIILOG;
import com.traveller.enthusiastic.appUtill.PagerSlidingTabStrip;


/**
     * Created by sauda on 25/02/17.
     */

    public abstract class TabBarFragment extends BaseFragment {
        protected ViewPager pager;
        protected PagerSlidingTabStrip mSlidingTabs;
        private Toolbar toolbar;


//    private View viewShadow;

        protected int currentPosition;
        private View mainContent;
        private View appBar;


        private OnSelectedTabIndexListener mSelectedTabListener;

        private PageChangeListener pageChangeListener;
        public interface OnSelectedTabIndexListener {
            void onSelectedTabIndex(int tabIndex);
        }


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View layout = inflater.inflate(getLayoutRes(), container, false);
            getDataFromBundle();
            pager = (ViewPager) layout.findViewById(R.id.pager);
            mainContent = layout.findViewById(R.id.main_content);
            appBar = layout.findViewById(R.id.appbar);
            //toolbar =  (Toolbar) appBar.findViewById(R.id.toolbarLayout);

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                if(appBar!=null){
//                    appBar.setElevation(0);
//                }
//            }
            mSlidingTabs = (PagerSlidingTabStrip) layout.findViewById(R.id.sliding_tabs);
            mSlidingTabs.setTextSize(spToPx(14));
            mSlidingTabs.setIndicatorColor(ContextCompat.getColor(getActivity(), R.color.white));
            mSlidingTabs.setDividerColor(ContextCompat.getColor(getActivity(), R.color.transparent));
            mSlidingTabs.setUnderlineColor(ContextCompat.getColor(getActivity(),R.color.blue_500));
            mSlidingTabs.setActivateTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            mSlidingTabs.setDeactivateTextColor(ContextCompat.getColor(getActivity(), android.R.color.darker_gray));
            mSlidingTabs.setHorizontalScrollBarEnabled(true);
            pageChangeListener = new PageChangeListener(null);
            mSlidingTabs.setOnPageChangeListener(pageChangeListener);
            return layout;
        }

        public int spToPx(int sp) {
            float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
            return (int) (sp * scaledDensity);
        }
        public View getTab(int pos) {
            return  mSlidingTabs.getTab(pos);
        }

        protected @LayoutRes
        int getLayoutRes(){
            return R.layout.fragment_tab;
        }
        private void getDataFromBundle() {

            Bundle bundle = getArguments();

            if (null != bundle) {

                currentPosition = /*bundle.getInt(CURRENT_POSITION, 0)*/ 0;
            }
        }
        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            pager.setAdapter(createTabContentAdapter());
            mSlidingTabs.setViewPager(pager);
            pager.setCurrentItem(currentPosition);

        }
        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if(getActivity() instanceof OnSelectedTabIndexListener)
                mSelectedTabListener = (OnSelectedTabIndexListener) getActivity();
        }
        public int getCurrentTabIndex() {

            return this.pager.getCurrentItem();
        }

        protected abstract FragmentPagerAdapter createTabContentAdapter();


        public class PageChangeListener implements ViewPager.OnPageChangeListener {

            private OnSelectedTabIndexListener mListener;

            public PageChangeListener(OnSelectedTabIndexListener onSelectedTabIndexListener) {
                mListener = onSelectedTabIndexListener;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                ASCIILOG.d("PAGE SELECTED " + position);
                if(mListener != null)
                    mListener.onSelectedTabIndex(position);
                onTabSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        }
        public abstract void onTabSelected(int position);

        public void updateTab(int index,String content){

            if(mSlidingTabs != null){
                mSlidingTabs.changeTitle(index,content);
            }else{
                ASCIILOG.d("OOps! unable to find sliding tab layout");
            }
        }




    }

