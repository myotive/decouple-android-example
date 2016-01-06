package com.dynamit.decoupleandroid.di;

import android.content.Context;

import com.dynamit.decoupleandroid.BuildConfig;
import com.dynamit.decoupleandroid.di.scope.ApplicationScope;
import com.dynamit.decoupleandroid.network.StarWarsService;
import com.dynamit.decoupleandroid.network.api.StarWarsAPI;
import com.dynamit.decoupleandroid.network.api.TMDbAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by michaelyotive_hr on 12/6/15.
 */
@Module
public class NetworkModule {

    /**
     * This provider is marked as ApplicationScope, making it a singleton.
     * @param restAdapter
     * @return
     */
    @Provides
    @ApplicationScope
    StarWarsAPI provideStarWarsAPI(@Named("StarWars")RestAdapter restAdapter){
        return restAdapter.create(StarWarsAPI.class);
    }


    /**
     * This provider isn't used in this sample. I wanted to show how named qualifiers are used
     * in dagger and how you can have multiple providers for the same object.
     * @param restAdapter
     * @return
     */
    @Provides
    TMDbAPI provideTMDbAPI(@Named("TMDbAPI") RestAdapter restAdapter){
        return restAdapter.create(TMDbAPI.class);
    }

    @Provides
    @ApplicationScope
    StarWarsService proviceStarWarsService(Context context){
        return new StarWarsService(context);
    }

    @Provides
    @Named("StarWars")
    RestAdapter provideStarWarsRestAdapter(OkClient client, RequestInterceptor requestInterceptor, Gson gson) {

        RestAdapter.LogLevel logLevel = BuildConfig.LOG_NETWORK
                ? RestAdapter.LogLevel.FULL
                : RestAdapter.LogLevel.NONE;

        return new RestAdapter.Builder()
                .setClient(client)
                .setLogLevel(logLevel)
                .setEndpoint(BuildConfig.API_URL)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @Provides
    @Named("TMDbAPI")
    RestAdapter provideTMDbRestAdapter(OkClient client, RequestInterceptor requestInterceptor, Gson gson) {

        RestAdapter.LogLevel logLevel = BuildConfig.LOG_NETWORK
                ? RestAdapter.LogLevel.FULL
                : RestAdapter.LogLevel.NONE;

        return new RestAdapter.Builder()
                .setClient(client)
                .setLogLevel(logLevel)
                .setEndpoint(BuildConfig.TMDB_API_URL)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    OkClient provideClient() {
        return new OkClient(new OkHttpClient());
    }

    @Provides
    RequestInterceptor provideRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/json");
                request.addHeader("Content-Type", "application/json");
            }
        };
    }
}
