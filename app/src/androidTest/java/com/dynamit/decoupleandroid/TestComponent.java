package com.dynamit.decoupleandroid;

import com.dynamit.decoupleandroid.di.ApplicationComponent;
import com.dynamit.decoupleandroid.di.scope.ApplicationScope;

import dagger.Component;

/**
 * Created by michaelyotive_hr on 12/22/15.
 */
@Component(modules = {DaggerTestApplicationModule.class, DaggerTestNetworkModule.class})
@ApplicationScope
public interface TestComponent extends ApplicationComponent {
    void inject(MainActivityTest mainActivityTest);
}
