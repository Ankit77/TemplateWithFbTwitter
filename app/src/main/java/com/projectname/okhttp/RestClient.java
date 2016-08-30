package com.projectname.okhttp;

import android.content.Context;


import com.projectname.util.Constans;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RestClient {
    private static RestClient instance;
    private RetrofitInterface apiService;

    public RestClient(Context context) {
        instance = this;

        //final OkHttpClient okHttpClient = new OkHttpClient();

        //OkClient okClient=new OkClient(okHttpClient);

        /**
         * SSl ALLOW The call OkClient bellow
         */

        OkClient okClient = new OkClient(SelfSigningClientBuilder.createClient());

        //.setClient(new OkClient(SelfSigningClientBuilder.createClient()))

        final RestAdapter restAdapter = new RestAdapter.Builder().setClient(okClient).setErrorHandler(new RetrofitErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setEndpoint(Constans.BASE_URL).build();
        apiService = restAdapter.create(RetrofitInterface.class);


    }

    public static RestClient getInstance() {
        return instance;
    }

    public RetrofitInterface getApiService() {
        return apiService;
    }

}
