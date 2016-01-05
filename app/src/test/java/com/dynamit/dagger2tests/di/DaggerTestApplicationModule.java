package com.dynamit.dagger2tests.di;

import android.content.Context;

import com.dynamit.decoupleandroid.di.scope.ApplicationScope;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import dagger.Module;
import dagger.Provides;

/**
 * Created by michaelyotive_hr on 12/22/15.
 */
@Module
public class DaggerTestApplicationModule {
    private Context context;

    // You can maintain state by passing data into your modules.
    public DaggerTestApplicationModule(Context context){
        this.context = context;
    }

    @Provides
    Context provideContext(){
        return this.context;
    }

    @Provides
    @ApplicationScope
    Bus provideBus(){
        return new Bus(ThreadEnforcer.ANY);
    }
}
