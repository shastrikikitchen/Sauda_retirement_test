package com.traveller.enthusiastic.networkUtils;

import android.text.TextUtils;


import com.traveller.enthusiastic.appUtill.ASCIILOG;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by sauda on 14/02/17.
 */

public class GETQueryBuilder {
    public static final String PROTOCOL_CHARSET = "utf-8";

    public static String prepareURL(String url, Object req) {
        Map<String, String> params = getQueryParams(req);
        return prepareGET_URL(url,params);
    }
    private static Map<String,String> getQueryParams(Object req){

        Field[] fields = req.getClass().getDeclaredFields();
        HashMap<String,String> params = new HashMap<>();
        AccessibleObject.setAccessible(fields, true);
        for(Field f:fields){
            try {
                Object value = f.get(req);
                String name = f.getName();
                if("Xauthztoken".equals(name))
                    name = "X-authz-token";
                else if("Xsellerauthztoken".equals(name))
                    name = "X-seller-authz-token";
                params.put(name, value.toString());
            } catch (IllegalAccessException e) {
                ASCIILOG.e("failed to access this field",e);

            } catch (NullPointerException npe){
                // Ignoring Null values
            }
        }
        return params;
    }
    public static String prepareGET_URL(String url, Map<String, String> params) {

        StringBuilder queryParam = new StringBuilder();
        Iterator iter = params.entrySet().iterator();
        try {
            while (iter.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) iter.next();
                if (TextUtils.isEmpty((String) entry.getValue())) {
                    continue;
                }

                queryParam.append((String) entry.getKey());
                queryParam.append('=');
//                if(!isEncodeUrl(url)) {
//                    queryParam.append((String) entry.getValue());
//
//                } else {
                    queryParam.append(URLEncoder.encode((String) entry.getValue(), PROTOCOL_CHARSET));
                //}
                queryParam.append('&');
            }
        } catch (UnsupportedEncodingException e) {
            ASCIILOG.e("preparing get url for url --> " + url, e);
        }

        if(queryParam.length() > 0) {
            queryParam.deleteCharAt(queryParam.length() - 1);
        }
        ASCIILOG.d("generated url is " + url + "?" + queryParam.toString()+"...end");
        return url + "?" + queryParam.toString();
    }

}
