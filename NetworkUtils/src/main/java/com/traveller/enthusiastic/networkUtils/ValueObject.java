package com.traveller.enthusiastic.networkUtils;

import java.io.Serializable;

/**
 * Created by sauda on 15/02/17.
 */

public  abstract class ValueObject extends SimpleObservable<ValueObject> implements Serializable {


        protected boolean successful;

        protected String errorCode;

        protected String errorMessage;

        private long mResponseTime;

        private boolean isCachedResponse;


        public boolean isSuccessful() {
            return successful;
        }

        public void setSuccessful(boolean successful) {
            this.successful = successful;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public void setResponseTime(long time){
            this.mResponseTime = time;
        }

        public long getResponseTime(){
            return mResponseTime;
        }

        public boolean isCachedResponse() {
            return isCachedResponse;
        }

        public void setCachedResponse(boolean cachedResponse) {
            isCachedResponse = cachedResponse;
        }
    }


