package com.demo.cnode_android.model.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ck on 2016/11/7.
 */

public class ApiClient {
    public static final ApiService service = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://cnodejs.org/api/v1/")
            .build()
            .create(ApiService.class);

}
