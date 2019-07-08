package com.example.luan.youtubeapp.api;

import com.example.luan.youtubeapp.model.Resultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by @luanfssilva on 07/07/2019.
 */

public interface YoutubeService {

    @GET("search")
    Call<Resultado> recuperarVideos(@Query("part") String part,
                                    @Query("order") String order,
                                    @Query("maxResults") String maxResults,
                                    @Query("key") String key,
                                    @Query("channelId") String channelId,
                                    @Query("q") String q

    );

}
