package com.dynamit.decoupleandroid;

import android.content.Context;

import com.dynamit.decoupleandroid.di.ApplicationComponent;
import com.dynamit.decoupleandroid.di.ApplicationModule;
import com.dynamit.decoupleandroid.di.DaggerApplicationComponent;
import com.dynamit.decoupleandroid.di.NetworkModule;

public class SampleApplication extends android.app.Application {

    ApplicationComponent applicationComponent;

    /**
     * Helper method to obtain SampleApplication class from context.
     * @param context
     * @return SampleApplication
     */
    public static SampleApplication getApplication(Context context){
        return (SampleApplication)context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // If Modules require no parameters, you can also do the following...
        // applicationComponent = DaggerApplicationComponent.create();

        // Otherwise, you have to do the following
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }

    /**
     * Allowing ApplicationComponent to be set. This allows unit tests to provide
     * Mocked objects though Dagger.
     * @param applicationComponent
     */
    public void setApplicationComponent(ApplicationComponent applicationComponent){
        this.applicationComponent = applicationComponent;
    }
}
