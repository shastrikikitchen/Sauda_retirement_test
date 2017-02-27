package com.traveller.enthusiastic.networkUtils.Response;

import com.traveller.enthusiastic.networkUtils.ValueObject;

/**
 * Created by sauda on 25/02/17.
 */

public class GoalWiseResponse extends ValueObject {
    private  String status;
    private int code;
    private MSG msg;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public MSG getMsg() {
        return msg;
    }

    public void setMsg(MSG msg) {
        this.msg = msg;
    }

    public static class MSG{
        private Long monthly_investment;
        private Long risk_level;
        private String name;
        private String glide_path;
        private Long target_pv;
        private int cagr;
        private String annual_inv_schedule;
        private int asset_alloc;
        private String monthly_inv_schedule;
        private int increment;
        private Long starting_amount;
        private Long total_investment;
        private Long time_horizon;
        private Long target;
        private String funds;

private Projection projections;

        public Long getMonthly_investment() {
            return monthly_investment;
        }

        public void setMonthly_investment(Long monthly_investment) {
            this.monthly_investment = monthly_investment;
        }

        public Long getRisk_level() {
            return risk_level;
        }

        public void setRisk_level(Long risk_level) {
            this.risk_level = risk_level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGlide_path() {
            return glide_path;
        }

        public void setGlide_path(String glide_path) {
            this.glide_path = glide_path;
        }

        public Long getTarget_pv() {
            return target_pv;
        }

        public void setTarget_pv(Long target_pv) {
            this.target_pv = target_pv;
        }

        public int getCagr() {
            return cagr;
        }

        public void setCagr(int cagr) {
            this.cagr = cagr;
        }

        public String getAnnual_inv_schedule() {
            return annual_inv_schedule;
        }

        public void setAnnual_inv_schedule(String annual_inv_schedule) {
            this.annual_inv_schedule = annual_inv_schedule;
        }

        public int getAsset_alloc() {
            return asset_alloc;
        }

        public void setAsset_alloc(int asset_alloc) {
            this.asset_alloc = asset_alloc;
        }

        public String getMonthly_inv_schedule() {
            return monthly_inv_schedule;
        }

        public void setMonthly_inv_schedule(String monthly_inv_schedule) {
            this.monthly_inv_schedule = monthly_inv_schedule;
        }

        public int getIncrement() {
            return increment;
        }

        public void setIncrement(int increment) {
            this.increment = increment;
        }

        public Long getStarting_amount() {
            return starting_amount;
        }

        public void setStarting_amount(Long starting_amount) {
            this.starting_amount = starting_amount;
        }

        public Long getTotal_investment() {
            return total_investment;
        }

        public void setTotal_investment(Long total_investment) {
            this.total_investment = total_investment;
        }

        public Long getTime_horizon() {
            return time_horizon;
        }

        public void setTime_horizon(Long time_horizon) {
            this.time_horizon = time_horizon;
        }

        public Long getTarget() {
            return target;
        }

        public void setTarget(Long target) {
            this.target = target;
        }

        public String getFunds() {
            return funds;
        }

        public void setFunds(String funds) {
            this.funds = funds;
        }

        public Projection getProjections() {
            return projections;
        }

        public void setProjections(Projection projections) {
            this.projections = projections;
        }
    }
    public  static  class Projection{
        private String p50;

        private String p25;

        private String p75;

        public String getP50() {
            return p50;
        }

        public void setP50(String p50) {
            this.p50 = p50;
        }

        public String getP25() {
            return p25;
        }

        public void setP25(String p25) {
            this.p25 = p25;
        }

        public String getP75() {
            return p75;
        }

        public void setP75(String p75) {
            this.p75 = p75;
        }
    }

}
