package com.traveller.enthusiastic.networkUtils.Request;

/**
 * Created by sauda on 15/02/17.
 */

public class DemoRequest {
    private String searchTerm;
    private int pageSize;
    private int pageNumber;
    private boolean pricingSearch;
    private String type;

    public DemoRequest() {
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isPricingSearch() {
        return pricingSearch;
    }

    public void setPricingSearch(boolean pricingSearch) {
        this.pricingSearch = pricingSearch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
