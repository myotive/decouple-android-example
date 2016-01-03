package com.dynamit.decoupleandroid;

import android.content.Context;

import com.dynamit.decoupleandroid.di.NetworkModule;
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

    @Provides
    StarWarsAPI provideStarWarsAPI() {
        return Mockito.mock(StarWarsAPI.class);
    }

    @Provides
    TMDbAPI provideTMDbAPI() {
        return Mockito.mock(TMDbAPI.class);
    }

    @Provides
    StarWarsService proviceStarWarsService() {
        return Mockito.mock(StarWarsService.class);
    }

    @Provides
    RestAdapter provideRestAdapter(){
        return Mockito.mock(RestAdapter.class);
    }

}
