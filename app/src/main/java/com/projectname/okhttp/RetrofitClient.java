package com.projectname.okhttp;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.client.UrlConnectionClient;

public class RetrofitClient extends UrlConnectionClient {
    public RetrofitClient() {
        super();
    }

    @Override
    public Response execute(Request request) throws IOException {
        return super.execute(request);
    }

    @Override
    protected HttpURLConnection openConnection(Request request) throws IOException {
        HttpURLConnection httpURLConnection = super.openConnection(request);
        httpURLConnection.setReadTimeout(999 * 1000);
        httpURLConnection.setConnectTimeout(999 * 1000);
        return httpURLConnection;
    }
}
