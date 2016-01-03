package com.dynamit.decoupleandroid.di;

import android.content.Context;

import com.dynamit.decoupleandroid.di.scope.ApplicationScope;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import dagger.Module;
import dagger.Provides;

/**
 * TODO: Add a class header comment!
 * Created by michaelyotive on 11/29/15.
 */
@Module
public class ApplicationModule {

    private Context context;

    // You can maintain state by passing data into your modules.
    public ApplicationModule(Context context){
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
