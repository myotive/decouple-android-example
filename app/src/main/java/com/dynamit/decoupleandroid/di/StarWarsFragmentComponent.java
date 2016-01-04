package com.dynamit.decoupleandroid.di;

import com.dynamit.decoupleandroid.di.scope.FragmentScope;
import com.dynamit.decoupleandroid.fragments.StarWarsFragment;

import dagger.Component;

/**
 * Created by michaelyotive_hr on 1/3/16.
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class,
            modules = StarWarsFragmentModule.class)
public interface StarWarsFragmentComponent {

    void inject(StarWarsFragment starWarsFragment);
}
