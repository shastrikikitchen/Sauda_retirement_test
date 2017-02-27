package com.traveller.enthusiastic.networkUtils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.traveller.enthusiastic.appUtill.ASCIILOG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sauda on 14/02/17.
 */

public class RequestLifecycleManager implements Application.ActivityLifecycleCallbacks {
    private static final RequestLifecycleManager ourInstance = new RequestLifecycleManager();
    private final HashMap<Object, List<INetworkRequest>> pendingReqlistMap = new HashMap<>();
    private Context mAppCtx;






    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Volly.stopAllPendingRequests(activity);
        clearPendingQueue(activity);

    }
    public void clearPendingQueue(Object tag) {
        if (pendingReqlistMap.containsKey(tag))
            pendingReqlistMap.remove(tag);
    }
    private void executePendingRequests() {
        ASCIILOG.d("executing pending requests");
        try{
            if(pendingReqlistMap != null) {
                for (List<INetworkRequest> reqList : pendingReqlistMap.values()) {
                    for (INetworkRequest req : reqList) {

                        if (null != req) {
                            req.execute();
                        }

                    }
                }

                pendingReqlistMap.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void addReqToPendingQueue(VolleyRequest req) {
        Object tag = req.getTag();
        INetworkRequest dupReq = req.duplicate();
        if (!pendingReqlistMap.containsKey(tag))
            pendingReqlistMap.put(tag, new ArrayList<INetworkRequest>());

        pendingReqlistMap.get(tag).add(dupReq);
    }
    private void dismissAndNotifyAllPendingRequests(VolleyError error) {
        for (List<INetworkRequest> reqList : pendingReqlistMap.values()) {
            if(reqList !=  null) {
                for (INetworkRequest req : reqList) {
                    if(req != null) {
                        req.processError(error);
                    }
                }
            }
        }
        pendingReqlistMap.clear();
    }
    void clearAllRequests() {
        Volly.getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        Volly.getImageRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });

        ASCIILOG.d("pending list map size -->" + pendingReqlistMap.size());
        pendingReqlistMap.clear();
    }
    public void init(Application app) {
        this.mAppCtx = app;
        app.registerActivityLifecycleCallbacks(this);
    }
    public static RequestLifecycleManager getInstance() {
        return ourInstance;
    }



}
