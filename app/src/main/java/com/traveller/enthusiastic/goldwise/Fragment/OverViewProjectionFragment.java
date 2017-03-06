package com.traveller.enthusiastic.goldwise.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.traveller.enthusiastic.goldwise.R;
import com.traveller.enthusiastic.goldwise.helper.Utility;
import com.traveller.enthusiastic.networkUtils.Response.GoalWiseResponse;

/**
 * Created by sauda on 25/02/17.
 */

public class OverViewProjectionFragment extends Fragment  {
    TextView projectedAmount_tv;
    TextView totalInvestments_tv;
    TextView goodCase_tv;
    TextView badCase_tv;
    View view;
    GoalWiseResponse response;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public  int getHeignt(){

        return view!= null?view.getHeight():500;
    }
    public void  setResponse(GoalWiseResponse response){
        this.response = response;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.card_view_data, container, false);
        initView(view);
       return view;
    }

    private void initView(View view) {
        projectedAmount_tv = (TextView) view.findViewById(R.id.projected_amount);
        totalInvestments_tv = (TextView) view.findViewById(R.id.totalInvestments);
        goodCase_tv = (TextView) view.findViewById(R.id.goodCase);
        badCase_tv = (TextView) view.findViewById(R.id.badCase);
        setData();
    }
    public void setData() {
        if (response != null) {
            badCase_tv.setText(Utility.getFormatedAmount(getContext(), "123456"));
            goodCase_tv.setText(Utility.getFormatedAmount(getContext(), "5656"));
            totalInvestments_tv.setText(Utility.getFormatedAmount(getContext(), response.getMsg().getTotal_investment()+""));
            String [] projection = response.getMsg().getProjections().getP75().split(";");
            String projSplit = (!TextUtils.isEmpty(projection[1]))?projection[1]:"90897";
            projectedAmount_tv.setText(Utility.getFormatedAmount(getContext(), projSplit));

        }else {
            badCase_tv.setText(Utility.getFormatedAmount(getContext(), "123456"));
            goodCase_tv.setText(Utility.getFormatedAmount(getContext(), "5656"));
            totalInvestments_tv.setText(Utility.getFormatedAmount(getContext(), "676768"));
            projectedAmount_tv.setText(Utility.getFormatedAmount(getContext(), "90897"));
        }
    }

}

