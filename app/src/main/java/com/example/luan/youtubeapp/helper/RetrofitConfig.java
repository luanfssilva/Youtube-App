package com.example.luan.youtubeapp.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by @luanfssilva on 06/07/2019.
 */


public class RetrofitConfig {

    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl( YoutubeConfig.URL_BASE )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
