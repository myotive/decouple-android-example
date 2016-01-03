package com.dynamit.decoupleandroid.network.api;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by michaelyotive_hr on 12/20/15.
 */
public interface TMDbAPI {
    @GET("/{version}/search/keyword")
    void search(@Path("version") String version,
                @Query("api_key") String apiKey,
                @Query("query")String query);
}
