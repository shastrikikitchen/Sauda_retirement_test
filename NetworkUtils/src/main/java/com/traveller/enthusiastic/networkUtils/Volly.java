package com.traveller.enthusiastic.networkUtils;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.traveller.enthusiastic.appUtill.ASCIILOG;
import com.traveller.enthusiastic.networkUtils.SSL.SDHurlStack;
import com.traveller.enthusiastic.utils.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;

/**
 * Created by sauda on 14/02/17.
 */

public class Volly {
    private static RequestQueue mRequestQueue;
        private static RequestQueue mImageRequestQueue;
        private static RequestQueue mLoginRequestQueue;

        private static RequestQueue mGatewayAuthQueue;
        private static RequestQueue mGatewayQueue;

        private static char[] KEYSTORE_PASSWORD = "qwerty".toCharArray();
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
        public static void init(Application app, String email) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, java.security.cert.CertificateException, IOException {


            ASCIILOG.d("init Volley request manager");
            appContext = app;
            hostName = /*APIUtils.getHostName();*/ "ec2-52-220-95-9.ap-southeast-1.compute.amazonaws.com";

            mImageRequestQueue = Volley.newRequestQueue(app, email);
            //mRequestQueue = Volley.newRequestQueue(app, email);
            mGatewayQueue = Volley.newRequestQueue(app, email);
            SSLSocketFactory sslSocketFactory = getSSLSocketFactory_Certificate("BKS", R.raw.sslcert);

            mRequestQueue = Volley.newRequestQueue(appContext.getApplicationContext(),  new SDHurlStack("ec2-52-220-95-9.ap-southeast-1.compute.amazonaws.com", sslSocketFactory));

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


    private static TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
                        if (certs != null && certs.length > 0){
                            certs[0].checkValidity();
                        } else {
                            originalTrustManager.checkClientTrusted(certs, authType);
                        }
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
                        if (certs != null && certs.length > 0){
                            certs[0].checkValidity();
                        } else {
                            originalTrustManager.checkServerTrusted(certs, authType);
                        }
                    }
                }
        };
    }

    private static SSLSocketFactory getSSLSocketFactory_Certificate(String keyStoreType, int keystoreResId)
            throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException, java.security.cert.CertificateException {

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = appContext.getApplicationContext().getResources().openRawResource(keystoreResId);

        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();

        if (keyStoreType == null || keyStoreType.length() == 0) {
            keyStoreType = KeyStore.getDefaultType();
        }
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);

        return sslContext.getSocketFactory();
    }

    private SSLSocketFactory getSSLSocketFactory_KeyStore(String keyStoreType, int keystoreResId, String keyPassword)
            throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException, java.security.cert.CertificateException {

        InputStream caInput = appContext.getApplicationContext().getResources().openRawResource(keystoreResId);

        // creating a KeyStore containing trusted CAs

        if (keyStoreType == null || keyStoreType.length() == 0) {
            keyStoreType = KeyStore.getDefaultType();
        }
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);

        keyStore.load(caInput, keyPassword.toCharArray());

        // creating a TrustManager that trusts the CAs in the KeyStore

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);

        return sslContext.getSocketFactory();
    }


        private static SSLSocketFactory newSslSocketFactory() {
            try {
                // Get an instance of the Bouncy Castle KeyStore format
                KeyStore trusted = KeyStore.getInstance("BKS");
                // Get the raw resource, which contains the keystore with
                // your trusted certificates (root and any intermediate certs)

                /*TODO:  check the raw file later*/
                InputStream in = appContext.getApplicationContext().getResources().openRawResource(R.raw.ssl);
                try {
                    // Initialize the keystore with the provided trusted certificates
                    // Provide the password of the keystore
                    trusted.load(in, KEYSTORE_PASSWORD);
                } finally {
                    in.close();
                }

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


