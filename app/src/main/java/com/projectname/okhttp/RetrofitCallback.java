package com.projectname.okhttp;

import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.projectname.util.Utils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class RetrofitCallback<T> implements Callback<T> {

    public abstract void success(T arg0, RestError restError);

    public abstract void failure(RestError restError);

    @Override
    public void failure(RetrofitError error) {

        Log.d("RetrofitCallback", "failure");

        final RestError restError = (RestError) error.getBodyAs(RestError.class);


        if (restError != null) {
            restError.setMessage("Problem while fetching data from server.Please try again later.");
            failure(restError);
        } else {
            if (error.getCause() instanceof SocketTimeoutException) {
//                failure(new RestError("Connection Timeout"));
                failure(new RestError("Network error, Please try after some time"));
            } else if (error.getCause() instanceof UnknownHostException || error.getCause() instanceof ConnectException) {
                failure(new RestError("The internet connection appears to be offline."));
            } else if (error.getCause() instanceof JSONException) {
                failure(new RestError("Unable to parse response from server"));
            } else {
                failure(new RestError("Problem while fetching data from server.Please try again later."));
            }
        }
    }

    @Override
    public void success(T arg0, Response arg1) {

        Log.d("RetrofitCallback", "success");

        try {

            Log.d("RetrofitCallback", "try");
            final Gson gson = new Gson();
            final RestError restError = gson.fromJson(Utils.getStringFromResponse(arg1), RestError.class);
            success(arg0, restError);
        } catch (IllegalStateException | JsonSyntaxException exception) {

            failure(new RestError("Problem while fetching data from server.Please try again later."));
            Log.d("RetrofitCallback", "catch 1");
        } catch (Exception e) {
            failure(new RestError("Problem while fetching data from server.Please try again later."));
            Log.d("RetrofitCallback", "catch 2");
        }


    }

}
