package com.dynamit.dagger2tests;

import com.dynamit.dagger2tests.di.DaggerTestApplicationModule;
import com.dynamit.dagger2tests.di.DaggerTestComponent;
import com.dynamit.dagger2tests.di.DaggerTestNetworkModule;
import com.dynamit.dagger2tests.di.TestComponent;
import com.dynamit.decoupleandroid.BuildConfig;
import com.dynamit.decoupleandroid.MainActivity;
import com.dynamit.decoupleandroid.R;
import com.dynamit.decoupleandroid.SampleApplication;
import com.dynamit.decoupleandroid.fragments.OttoFragment;
import com.dynamit.decoupleandroid.network.api.StarWarsAPI;
import com.dynamit.decoupleandroid.network.models.PeopleListResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.DeadEvent;
import com.squareup.otto.Subscribe;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import retrofit.Callback;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,
        application = SampleApplication.class,
        sdk = 21,
        packageName="com.dynamit.decoupleandroid")
public class StarWarsFragmentUnitTest {
    private MainActivity mainActivity;

    @Inject
    Bus bus;
    @Inject
    StarWarsAPI starWarsAPI;

    @Captor
    private ArgumentCaptor<Callback<PeopleListResponse>> cb;

    TestComponent testComponent;

    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.setupActivity(MainActivity.class);

        SampleApplication app = (SampleApplication) RuntimeEnvironment.application;
        testComponent = DaggerTestComponent
                .builder()
                .daggerTestApplicationModule(new DaggerTestApplicationModule(app))
                .daggerTestNetworkModule(new DaggerTestNetworkModule(app))
                .build();

        testComponent.inject(this);
        app.setApplicationComponent(testComponent);

        MockitoAnnotations.initMocks(this);
    }


    @Test(timeout = 1000)
    public void testOttoFragment_DeadEvent(){
        final AtomicBoolean testDone = new AtomicBoolean(false);;

        OttoFragment fragment = OttoFragment.newInstance();
        SupportFragmentTestUtil.startFragment(fragment, MainActivity.class);

        // test dead event
        bus.register(new Object() {
            @Subscribe
            public void onDeadEvent(DeadEvent deadEvent){
                testDone.set(true);
            }
        });

        fragment.getView().findViewById(R.id.bt_otto_dead_event).performClick();

        Mockito.verify(starWarsAPI).getPeople(cb.capture());
        cb.getValue().success(new PeopleListResponse(), null);

        while(!testDone.get());
    }
}