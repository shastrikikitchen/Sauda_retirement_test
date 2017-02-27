package com.traveller.enthusiastic.networkUtils;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.traveller.enthusiastic.appUtill.ASCIILOG;

import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by sauda on 14/02/17.
 */

public class Volly {
    private static RequestQueue mRequestQueue;
        private static RequestQueue mImageRequestQueue;
        private static RequestQueue mLoginRequestQueue;

        private static RequestQueue mGatewayAuthQueue;
        private static RequestQueue mGatewayQueue;

        private static char[] KEYSTORE_PASSWORD = "snapdealseller123".toCharArray();
        private static String hostName;
        private static ImageLoader imageLoader;


        private Volly() {

        }

        public static Context appContext;


        /**
         * Initialize Volley Request Queue.
         *
         * @param app Application instance.
         */
        public static void init(Application app, String email) {


            ASCIILOG.d("init SDVolley request manager");
            appContext = app;
            hostName = /*APIUtils.getHostName();*/ "stage";

            mImageRequestQueue = Volley.newRequestQueue(app, email);
            mRequestQueue = Volley.newRequestQueue(app, email);
            mGatewayQueue = Volley.newRequestQueue(app, email);

            mGatewayAuthQueue = Volley.newRequestQueue(app.getApplicationContext(), email);
            mLoginRequestQueue = Volley.newRequestQueue(app.getApplicationContext(),email);

            imageLoader = getImageLoader();

            RequestLifecycleManager.getInstance().init(app);

        }


        public static ImageLoader getImageLoader() {
            getRequestQueue();
            if (imageLoader == null) {
                imageLoader = new ImageLoader(mRequestQueue, LruBitmapCache.open());
            }

            return imageLoader;
        }

        public static RequestQueue getGatewayQueue() {
            return mGatewayQueue;
        }

        public static RequestQueue getGatewayAuthQueue() {
            return mGatewayAuthQueue;
        }

        /**
         * Method to get the Volley Image Request Queue.
         *
         * @return Request Queue
         */


        public static RequestQueue getImageRequestQueue() {
            return mImageRequestQueue;
        }

        /**
         * Method to get the Volley Request Queue.
         *
         * @return Request Queue
         */
        public static RequestQueue getRequestQueue() {
            return mRequestQueue;
        }

        public static RequestQueue getLoginRequestQueue() {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            return mLoginRequestQueue;
        }

        public static void invalidateCache(){
            mRequestQueue.getCache().clear();
            mGatewayQueue.getCache().clear();
        }

        /*call this method in activity onstop to cancel any pending requests for the current activity
        * pass the context of the activity
        * stops all pending requests
        * */
        public static void stopAllPendingRequests(final Object tag) {

            if (tag == null) return;
            ASCIILOG.d("stop pending requests for activity --> " + tag);
            mRequestQueue.cancelAll(tag);
            mGatewayQueue.cancelAll(tag);
            RequestLifecycleManager.getInstance().clearPendingQueue(tag);
        }

        public static void stopAllPendingRequests() {

            Volly.invalidateCache();

            mRequestQueue.stop();
            mGatewayQueue.stop();
            RequestLifecycleManager.getInstance().clearAllRequests();
        }

        private static SSLSocketFactory newSslSocketFactory() {
            try {
                // Get an instance of the Bouncy Castle KeyStore format
                KeyStore trusted = KeyStore.getInstance("BKS");
                // Get the raw resource, which contains the keystore with
                // your trusted certificates (root and any intermediate certs)

                /*TODO:  check the raw file later*/
                /*InputStream in = appContext.getApplicationContext().getResources().openRawResource(R.raw.clientkeystore);
                try {
                    // Initialize the keystore with the provided trusted certificates
                    // Provide the password of the keystore
                    trusted.load(in, KEYSTORE_PASSWORD);
                } finally {
                    in.close();
                }*/

                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(trusted);

                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, tmf.getTrustManagers(), null);

                SSLSocketFactory sf = context.getSocketFactory();
                return sf;
            } catch (Exception e) {
                throw new AssertionError(e);
            }
        }
    }


