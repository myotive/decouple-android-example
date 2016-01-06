package com.dynamit.decoupleandroid.di;

import com.dynamit.decoupleandroid.adapter.SearchResultAdapter;
import com.dynamit.decoupleandroid.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class StarWarsFragmentModule {

    @Provides
    @FragmentScope
    SearchResultAdapter provideSearchResultAdapter(){
        return new SearchResultAdapter();
    }
}
