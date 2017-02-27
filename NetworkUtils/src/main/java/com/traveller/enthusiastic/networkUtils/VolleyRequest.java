package com.traveller.enthusiastic.networkUtils;


import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.traveller.enthusiastic.appUtill.ASCIILOG;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by sauda on 14/02/17.
 */


public abstract class VolleyRequest<Req, Res extends ValueObject> extends Request<Res> implements  INetworkRequest {

    public static final String SET_COOKIE = "Set-Cookie";
    public static final String ACCEPT_TYPE = "accept-Type";
    public static final String PROTOCOL_CHARSET = "utf-8";
    public static final String PROTOCOL_JSON_CONTENT_TYPE = String.format("application/json; charset=%s", new Object[]{PROTOCOL_CHARSET});
    public static final String PROTOCOL_HTML_CONTENT_TYPE = String.format("text/html; charset=%s", new Object[]{PROTOCOL_CHARSET});
    public static final String PROTOCOL_FORM_CONTENT_TYPE = String.format("application/x-www-form-urlencoded; charset=%s", new Object[]{PROTOCOL_CHARSET});
    protected VolleyListener<Res> mListener;
    private final Class<Res> mResClass;
    protected String mRequestBody;
    protected Req mRequest;


    public VolleyRequest(int method, String url, Req requestBody, Class<Res> resClass, VolleyListener<Res> listener, Object tag) {
        super(method, url, listener);
        mListener = listener;
        mResClass = resClass;
        mRequest = requestBody;

        if (method == Method.GET && requestBody != null) {
            this.mUrl = GETQueryBuilder.prepareURL(url, requestBody);
        }

        if (method != Method.GET) {
            mRequestBody = requestBody != null ? APIUtils.createJsonFromModel(requestBody) : null;
        }

        if (tag != null) {
            this.setTag(tag);
        }
        ASCIILOG.d("SDVolleyRequest mRequestBody " + mRequestBody + "  url " + mUrl );

        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        setRetryPolicy(retryPolicy);

    }

    public static byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();

        try {
            Iterator iter = params.entrySet().iterator();

            while (iter.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) iter.next();
                encodedParams.append(URLEncoder.encode((String) entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode((String) entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }

            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, e);
        }
    }

    //increase priority of json data requests to take over image requests
    @Override
    public Request.Priority getPriority() {
        return Priority.HIGH;
    }

    @Override
    public void deliverError(VolleyError error) {
        try {

            ASCIILOG.e("network error received for url: " + getUrl(), error);
            //check for Access denied error
            if (error instanceof AuthFailureError) {
                super.deliverError(error);
            }
        } catch (Exception e) {
            ASCIILOG.e("error in processing onerror", e);
        }
    }

    @Override
    protected void deliverResponse(Res response) {
        try {
            if (null != mListener) {
                mListener.onResponse(response);
            }
        } catch (Exception e) {
            ASCIILOG.e("error in processing response", e);
        }
    }

    @Override
    protected Response<Res> parseNetworkResponse(NetworkResponse response) {

        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));


            json = processResponse(mUrl, json);

            ObjectMapper objectMapper = ObjectMapperSingleton.getInstance().getJacksonObjectMapper();

            Res readValue = objectMapper.readValue(json, mResClass);


            String errorCode = readValue.getErrorCode();
           //success
                Cache.Entry entry = null;
                long responseTime = System.currentTimeMillis();
                boolean isCacheResponse = false;
                if(shouldCacheResponse() && readValue.isSuccessful()){
                    if(response.headers.containsKey(Volley.HEADER_CACHE_RESPONSE_TIME)){
                        responseTime = Long.parseLong(response.headers.get(Volley.HEADER_CACHE_RESPONSE_TIME));
                        isCacheResponse = true;
                    }
                    entry = new Cache.Entry();
                    entry.data = response.data;
                    entry.softTtl = responseTime + getCacheTime();
                    entry.ttl = responseTime + getCacheTime();
                    entry.responseHeaders = response.headers;
                    entry.lastModified = responseTime;
                    entry.groupKey = getGroupKey();
                }

                readValue.setResponseTime(responseTime);
                readValue.setCachedResponse(isCacheResponse);

                return Response.success(readValue, entry);
//                return Response.success(readValue, HttpHeaderParser.parseCacheHeaders(response));


        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonParseException e) {
            return Response.error(new ParseError(e));
        } catch (JsonMappingException e) {
            return Response.error(new ParseError(e));
        } catch (IOException e) {
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    private String processResponse(String url, String json) {
        try {
            //List of Strings of API Names for which will consists of only string (Not JSON)
            String[] htmlApis = {"getFAQs", "getAnnouncementDetails"};

            for (String apiName : htmlApis) {
                if (url.contains(apiName)) {
                    json = ObjectMapperSingleton.getInstance().getJacksonObjectMapper().writeValueAsString(json);
                    json = "{\"data\" :" + json + "}";

                    if(ASCIILOG.isLogEnabled()) {
                        ASCIILOG.d("Inside :: new JSON is " + json);
                    }
                    return json;
                }
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return volleyError;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        HashMap<String, String> headers = new HashMap<String, String>();
//        String cookieString = APIUtils.getCookie();
        //headers.put(COOKIE, cookieString);
        //headers.put(DEVICE_ID, AppUtils.getUniqueId(Volly.appContext));
        //SDLog.d("cookie for request --> " + cookieString);
        return headers;
    }

    public String getBodyContentType() {
        return PROTOCOL_JSON_CONTENT_TYPE;
    }

    public byte[] getBody() {
        try {
            return this.mRequestBody == null ? null : this.mRequestBody.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {

            return null;
        } catch (Exception e) {

            return null;
        }
    }

    //start this APIEndpoint call

    @Override
    public void execute(boolean forceRefresh) {
        if(shouldCacheResponse() && forceRefresh){
            if(!TextUtils.isEmpty(getGroupKey())){
                Volly.getRequestQueue().getCache().removeAllOf(getGroupKey());
            }else{
                Volly.getRequestQueue().getCache().remove(getCacheKey());
            }
        }
        this.setShouldCache(shouldCacheResponse());
        Volly.getRequestQueue().add(this);
    }

    @Override
    public void execute() {
        execute(false);
    }

    protected VolleyRequest(VolleyRequest request) {
        this(request.getMethod(), request.getUrl(), null, request.mResClass, request.mListener, request.getTag());
        this.mRequestBody = request.mRequestBody;
        this.mRequest = (Req) request.mRequest;
    }

    @Override
    public void processError(VolleyError error) {
        this.deliverError(error);
    }

    @Override
    public void cancelRequest() {
        this.cancel();
    }


    public boolean shouldCacheResponse(){
        return false;
    }

    protected long getCacheTime(){
        return 0l;
    }

    @Override
    public void cancel() {
        super.cancel();
        mListener = null;
    }


    @Override
    public String getCacheKey() {
        if(getMethod() != Method.GET && mRequest!=null){
            return getUrl()+"_"+mRequest.toString();
        }else {
            return super.getCacheKey();
        }

    }

    @Override
    public String getGroupKey() {
        return null;
    }

    @Override
    public Object getRequestTag() {
        return getTag();
    }
}

