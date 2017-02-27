package com.traveller.enthusiastic.goldwise.View;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.traveller.enthusiastic.goldwise.Adapter.ProjectionPagerAdaptor;
import com.traveller.enthusiastic.goldwise.R;
import com.traveller.enthusiastic.goldwise.helper.IConstants;
import com.traveller.enthusiastic.appUtill.ASCIILOG;
import com.traveller.enthusiastic.appUtill.CirclePageIndicator;
import com.traveller.enthusiastic.networkUtils.Response.GoalWiseResponse;

/**
 * Created by sauda on 25/02/17.
 */

public class ProjectionView {
    Activity activity;
    LinearLayout widgetConteainer;
    FragmentManager fragmentManager;
    private View rootView = null;

    public ProjectionView(Activity activity, LinearLayout wc, FragmentManager fragmentManager){
       this.activity = activity;
       this.widgetConteainer = wc;
        this.fragmentManager = fragmentManager;
   }


    public View getView(String tag) {


        View view = widgetConteainer.findViewWithTag(tag);
        if (view == null && activity != null) {

            view = activity.getLayoutInflater().inflate(R.layout.card_view, null);
            rootView = view;
            widgetConteainer.addView(view);
            view.setTag(tag);

            view.setVisibility(View.GONE);
        }
        /*if (view != null)
            view.setVisibility(View.GONE);*/
        return view;
    }
    public  void updateData(View container,Object data){{
        {
            try {
                container =  getView(IConstants.PROJECTIONVIEW);
                if (container != null && activity != null) {

                    container.setVisibility(View.VISIBLE);

                    final ViewPager viewPager = (ViewPager) container.findViewById(R.id.vp_projection);
//                    final TextView textview = (TextView) container.findViewById(R.id.tempTextview);
                    String tmp = "hddhf";
                    //textview.setText(tmp);
                    GoalWiseResponse response = (GoalWiseResponse) data;


                    ProjectionPagerAdaptor mPolicyAgreementWidgetAdapter =
                            new ProjectionPagerAdaptor(fragmentManager,response);
                    viewPager.setAdapter(mPolicyAgreementWidgetAdapter);

                    //Indicator Code
                    float density = activity.getResources().getDisplayMetrics().density;
                    CirclePageIndicator cirIndicator = (CirclePageIndicator) container.findViewById(R.id.viewpagerAnnouncementIndicator);

                    cirIndicator.setFillColor(ContextCompat.getColor(activity, R.color.blue_700));
                    cirIndicator.setPageColor(ContextCompat.getColor(activity, R.color.purple_A700));
                    cirIndicator.setStrokeColor(ContextCompat.getColor(activity, R.color.blue_700));
                    cirIndicator.setStrokeWidth(0.1f * density);
                    cirIndicator.setRadius(4 * density);
                    cirIndicator.setSelected(true);
                    ViewGroup.LayoutParams params = viewPager.getLayoutParams();
                    params.height = 400;
                    viewPager.setLayoutParams(params);

                    cirIndicator.setViewPager(viewPager);
                    cirIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        int prevPosition = 0;

                        @Override
                        public void onPageSelected(int currentPosition) {
                            if (currentPosition > prevPosition) {
                                prevPosition = currentPosition;
                            }
                            if (currentPosition < prevPosition) {
                                prevPosition = currentPosition;

                            }
                        }

                        @Override
                        public void onPageScrolled(int currentPosition, float arg1, int arg2) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int arg0) {

                        }
                    });
                }


            } catch (Exception e) {
                ASCIILOG.e("", e);
                e.printStackTrace();
            }
        }
    }}


}
