package com.projectname.okhttp;


import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface RetrofitInterface {

    @POST("/poi")
    @FormUrlEncoded
    void getPoi(@Field("post_data_string") String jsonString, RetrofitCallback<Response> callback);


    @POST("/user")
    @FormUrlEncoded
    void getUser(@Field("post_data_string") String jsonString, RetrofitCallback<Response> callback);


}

