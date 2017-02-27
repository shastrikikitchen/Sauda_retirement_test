package com.traveller.enthusiastic.networkUtils.API;

import com.android.volley.Request;
import com.traveller.enthusiastic.networkUtils.INetworkRequest;
import com.traveller.enthusiastic.networkUtils.Request.DemoRequest;
import com.traveller.enthusiastic.networkUtils.Response.DemoResponse;
import com.traveller.enthusiastic.networkUtils.VolleyListener;
import com.traveller.enthusiastic.networkUtils.VolleyRequest;

/**
 * Created by sauda on 15/02/17.
 */

public class DemoAPi extends VolleyRequest<DemoRequest,DemoResponse> {
    public DemoAPi(Object tag, VolleyListener<DemoResponse> listener, DemoRequest request) {
        super(Request.Method.POST, "http://www.json-generator.com/api/json/get/cgjDpnWhfm?indent=2 " /*"http://localhost:8080/catalog/v0/historySearch"*/, request, DemoResponse.class, listener, tag);
    }

    protected DemoAPi(VolleyRequest request) {
        super(request);
    }

    @Override
    public INetworkRequest duplicate() {
        return new DemoAPi(this);
    }

    @Override
    public Request.Priority getPriority() {
        return Request.Priority.IMMEDIATE;
    }

    public static class Builder {
        private Object tag;
        private VolleyListener<DemoResponse> listener;
        private String searchTerm;
        private int pageSize;
        private int pageNumber;
        private boolean pricingSearch;
        private String type;

        public Builder setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder setListener(VolleyListener<DemoResponse> listener) {
            this.listener = listener;
            return this;
        }

        public Builder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        public Builder setPricingSearch(boolean pricingSearch) {
            this.pricingSearch = pricingSearch;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setSearchTerm(String searchTerm) {
            this.searchTerm = searchTerm;
            return this;
        }

        public DemoAPi build() {
            DemoRequest request = new DemoRequest();
            request.setPageNumber(pageNumber);
            request.setPageSize(pageSize);
            request.setPricingSearch(pricingSearch);
            request.setSearchTerm(searchTerm);
            request.setType(type);
            return new DemoAPi(tag, listener, request);
        }
    }
}


