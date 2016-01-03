package com.dynamit.decoupleandroid.di;

import com.dynamit.decoupleandroid.MainActivity;
import com.dynamit.decoupleandroid.di.scope.ApplicationScope;
import com.dynamit.decoupleandroid.fragments.DaggerFragment;
import com.dynamit.decoupleandroid.fragments.OttoFragment;
import com.dynamit.decoupleandroid.network.StarWarsService;
import com.dynamit.decoupleandroid.network.api.StarWarsAPI;

import dagger.Component;

/**
 * TODO: Add a class header comment!
 * Created by michaelyotive on 11/29/15.
 */
@Component(modules = {ApplicationModule.class, NetworkModule.class})
@ApplicationScope
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(DaggerFragment daggerFragment);
    void inject(OttoFragment ottoFragment);

    void inject(StarWarsService starWarsService);

    // Provide Implementation for constructor injection, direct access from component, or
    // from dependant components.
    StarWarsAPI starwarsapi();
}
