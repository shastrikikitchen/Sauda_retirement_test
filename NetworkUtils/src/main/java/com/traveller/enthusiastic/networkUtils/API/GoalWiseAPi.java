package com.traveller.enthusiastic.networkUtils.API;

import com.android.volley.AuthFailureError;
import com.traveller.enthusiastic.networkUtils.INetworkRequest;
import com.traveller.enthusiastic.networkUtils.VolleyListener;
import com.traveller.enthusiastic.networkUtils.Request.GoalWiseRequest;
import com.traveller.enthusiastic.networkUtils.Response.GoalWiseResponse;
import com.traveller.enthusiastic.networkUtils.VolleyRequest;

import java.util.Map;

/**
 * Created by sauda on 25/02/17.
 */

public class GoalWiseAPi extends VolleyRequest<GoalWiseRequest,GoalWiseResponse> {
    public GoalWiseAPi(GoalWiseRequest requestBody, VolleyListener<GoalWiseResponse> listener, Object tag) {
        super(Method.POST, "https://ec2-52-220-95-9.ap-southeast-1.compute.amazonaws.com/api/goal-plan", requestBody, GoalWiseResponse.class, listener, tag);
       // super(Method.POST, "http://www.json-generator.com/api/json/get/bPWTzgyJFe?indent=2", requestBody, GoalWiseResponse.class, listener, tag);
    }

    /*@Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> header = super.getHeaders();
        return header;
    }*/

    @Override
    public INetworkRequest duplicate() {
        return this;
    }
    public  static class Builder{
        private int time_horizon;
        private  String goal;
        private  int risk_cat;
        private long monthly_inv;
        private VolleyListener<GoalWiseResponse> listener;
        private Object tag;

        public Builder setTime_horizon(int time_horizon) {
            this.time_horizon = time_horizon;
            return this;
        }

        public Builder setGoal(String goal) {
            this.goal = goal;
            return this;
        }

        public Builder setRisk_cat(int risk_cat) {
            this.risk_cat = risk_cat;
            return this;
        }

        public Builder setMonthly_inv(long monthly_inv) {
            this.monthly_inv = monthly_inv;
            return this;
        }

        public Builder setListener(VolleyListener<GoalWiseResponse> listener) {
            this.listener = listener;
            return this;

        }


        public Builder setTag(Object tag) {
            this.tag = tag;
            return this;
        }
        public GoalWiseAPi build(){
            GoalWiseRequest request = new GoalWiseRequest();
            request.setGoal(goal);
            request.setMonthly_inv(monthly_inv);
            request.setRisk_cat(risk_cat);
            request.setTime_horizon(time_horizon);
            request.setAccess_token("YXcxNEFDTU4IAUN.cDUcOQYbZQwsLBgbMwQCYwIwfRomFERrEHU3Dw");
            return new GoalWiseAPi(request,listener,tag);
        }
    }

}
