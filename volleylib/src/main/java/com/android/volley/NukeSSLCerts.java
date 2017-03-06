package com.android.volley;

import com.traveller.enthusiastic.appUtill.ASCIILOG;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by MY HP on 3/5/2017.
 */
public  class NukeSSLCerts {
        protected static final String TAG = "NukeSSLCerts";

        public static void nuke() {

            ASCIILOG.d("nuke ssl cert");
            try {

                TrustManager[] trustAllCerts = new TrustManager[] {

                        new X509TrustManager() {


                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                            }

                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                            }

                            public X509Certificate[] getAcceptedIssuers() {
                                ASCIILOG.d("nuke getAccepted");

                                X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                                return myTrustedAnchors;
                            }


                        }
                };


                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                });
            } catch (Exception e) {
                ASCIILOG.d("nuke error");

            }
        }
    }

