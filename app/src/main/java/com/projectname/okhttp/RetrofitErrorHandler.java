package com.projectname.okhttp;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;


public class RetrofitErrorHandler implements ErrorHandler {

    public RetrofitErrorHandler() {

    }

    @Override
    public Throwable handleError(RetrofitError cause) {

        if (cause.getCause() != null) {
            if (cause.isNetworkError()) {
                if (cause.getCause() instanceof SocketTimeoutException) {
                    new CustomException("socket time out");
                }
                if (cause.getCause() instanceof UnknownHostException) {
                    new CustomException("no internet connection");

                }
            } else {
                new CustomException("some error occur");
            }

            return cause.getCause();
        } else {
            new CustomException("some error occur");
            return new Exception("some error occur");
        }

    }

}
