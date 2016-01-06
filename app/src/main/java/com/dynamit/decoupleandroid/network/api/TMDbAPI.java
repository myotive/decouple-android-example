package com.dynamit.decoupleandroid.network.api;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * themoviedb.org API
 * This API is only being used in this sample app to illustrate the use of qualifiers
 * within Dagger 2.
 * See NetworkModule.
 */
public interface TMDbAPI {
    @GET("/{version}/search/keyword")
    void search(@Path("version") String version,
                @Query("api_key") String apiKey,
                @Query("query")String query);
}
