package com.traveller.enthusiastic.networkUtils.SSL;


    import com.android.volley.toolbox.HurlStack;

    import java.io.IOException;
    import java.net.HttpURLConnection;
    import java.net.URL;

    import javax.net.ssl.HostnameVerifier;
    import javax.net.ssl.HttpsURLConnection;
    import javax.net.ssl.SSLSession;
    import javax.net.ssl.SSLSocketFactory;

public class SDHurlStack extends HurlStack {

        private  UrlRewriter mUrlRewriter;
        private  String hostName;
        private  SSLSocketFactory mSslSocketFactory;

        public  SDHurlStack(String hostName, SSLSocketFactory mSslSocketFactory){
            super(null, mSslSocketFactory);
            this.hostName = hostName;
            this.mSslSocketFactory = mSslSocketFactory;

        }
    public  SDHurlStack( SSLSocketFactory mSslSocketFactory){
        super(null, mSslSocketFactory);
        this.mSslSocketFactory = mSslSocketFactory;

    }

        public SDHurlStack() {
            super(null);
        }

        public SDHurlStack(HurlStack.UrlRewriter urlRewriter) {
            super(urlRewriter, null);
        }

        public SDHurlStack(HurlStack.UrlRewriter urlRewriter, SSLSocketFactory sslSocketFactory) {
            super(urlRewriter, sslSocketFactory);
            this.mUrlRewriter = urlRewriter;
            this.mSslSocketFactory = sslSocketFactory;
        }




    @Override
        protected HttpURLConnection createConnection(URL url) throws IOException {

            HttpURLConnection connection  =  (HttpURLConnection)url.openConnection();
            if ("https".equals(url.getProtocol()) && mSslSocketFactory != null) {
                ((HttpsURLConnection)connection).setHostnameVerifier(hostnameVerifier);
            }

            return connection;
        }

        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                boolean  verifed =  hv.verify(hostName, session);
                return true;
            }
        };
    }


