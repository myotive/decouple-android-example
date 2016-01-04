package com.dynamit.decoupleandroid.di;

import com.dynamit.decoupleandroid.adapter.SearchResultAdapter;
import com.dynamit.decoupleandroid.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by michaelyotive_hr on 1/3/16.
 */
@Module
public class StarWarsFragmentModule {

    @Provides
    @FragmentScope
    SearchResultAdapter provideSearchResultAdapter(){
        return new SearchResultAdapter();
    }
}
