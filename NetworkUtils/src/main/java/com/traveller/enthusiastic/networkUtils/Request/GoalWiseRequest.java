package com.traveller.enthusiastic.networkUtils.Request;

/**
 * Created by sauda on 25/02/17.
 */

public class GoalWiseRequest {
    private int time_horizon;
    private  String goal;
    private  int risk_cat;
    private long monthly_inv;
    private  String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getTime_horizon() {
        return time_horizon;
    }

    public void setTime_horizon(int time_horizon) {
        this.time_horizon = time_horizon;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getRisk_cat() {
        return risk_cat;
    }

    public void setRisk_cat(int risk_cat) {
        this.risk_cat = risk_cat;
    }

    public long getMonthly_inv() {
        return monthly_inv;
    }

    public void setMonthly_inv(long monthly_inv) {
        this.monthly_inv = monthly_inv;
    }
}
