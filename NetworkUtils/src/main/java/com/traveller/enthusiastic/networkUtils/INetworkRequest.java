package com.traveller.enthusiastic.networkUtils;

import com.android.volley.VolleyError;

/**
 * Created by sauda on 14/02/17.
 */

public interface INetworkRequest {
        void execute();

        INetworkRequest duplicate();

        void processError(VolleyError error);

        void cancelRequest();

        void execute(boolean forceRefresh);

        String getGroupKey();

        boolean shouldCacheResponse();

        Object getRequestTag();
    }


