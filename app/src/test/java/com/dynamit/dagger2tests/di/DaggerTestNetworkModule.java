package com.dynamit.dagger2tests.di;

import android.content.Context;

import com.dynamit.dagger2tests.retrofit.MockClient;
import com.dynamit.decoupleandroid.BuildConfig;
import com.dynamit.decoupleandroid.di.scope.ApplicationScope;
import com.dynamit.decoupleandroid.network.StarWarsService;
import com.dynamit.decoupleandroid.network.api.StarWarsAPI;
import com.dynamit.decoupleandroid.network.api.TMDbAPI;

import org.mockito.Mockito;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * TODO: Add a class header comment!
 * Created by michaelyotive on 11/29/15.
 */
@Module
public class DaggerTestNetworkModule {

    private Context context;
    public DaggerTestNetworkModule(Context context){
        this.context = context;
    }

    @Provides
    @ApplicationScope
    StarWarsAPI provideStarWarsAPI() {
        return Mockito.mock(StarWarsAPI.class);
    }

    @Provides
    @ApplicationScope
    TMDbAPI provideTMDbAPI() {
        return Mockito.mock(TMDbAPI.class);
    }

    @Provides
    @ApplicationScope
    StarWarsService proviceStarWarsService(Context context) {
        return new StarWarsService(context);
    }

    @Provides
    @Named("StarWars")
    RestAdapter provideStarWarsRestAdapter(){
        return new RestAdapter
                .Builder()
                .setEndpoint(BuildConfig.API_URL)
                .setClient(new MockClient(context))
                .build();
    }

    @Provides
    @Named("TMDbAPI")
    RestAdapter provideTMDbRestAdapter(){
        return new RestAdapter
                .Builder()
                .setEndpoint(BuildConfig.TMDB_API_URL)
                .setClient(new MockClient(context))
                .build();
    }
}
