package com.dynamit.dagger2tests;

import android.support.v7.widget.RecyclerView;

import com.dynamit.dagger2tests.di.DaggerTestApplicationModule;
import com.dynamit.dagger2tests.di.DaggerTestComponent;
import com.dynamit.dagger2tests.di.DaggerTestNetworkModule;
import com.dynamit.dagger2tests.di.TestComponent;
import com.dynamit.decoupleandroid.BuildConfig;
import com.dynamit.decoupleandroid.MainActivity;
import com.dynamit.decoupleandroid.R;
import com.dynamit.decoupleandroid.SampleApplication;
import com.dynamit.decoupleandroid.fragments.OttoFragment;
import com.dynamit.decoupleandroid.fragments.StarWarsFragment;
import com.dynamit.decoupleandroid.network.api.StarWarsAPI;
import com.dynamit.decoupleandroid.network.models.FilmListResponse;
import com.dynamit.decoupleandroid.network.models.PeopleListResponse;
import com.dynamit.decoupleandroid.network.models.common.Film;
import com.squareup.otto.Bus;
import com.squareup.otto.DeadEvent;
import com.squareup.otto.Subscribe;

import junit.framework.Assert;

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
    private ArgumentCaptor<Callback<PeopleListResponse>> peopleListCaptor;
    @Captor
    private ArgumentCaptor<Callback<FilmListResponse>> filmListResponseCaptor;

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

    @Test
    public void testStarWarsFragment_GetFilms(){
        StarWarsFragment fragment = StarWarsFragment.newInstance();
        SupportFragmentTestUtil.startFragment(fragment, MainActivity.class);

        FilmListResponse expectedListResponse = new FilmListResponse();
        expectedListResponse.getResults().add(new Film());
        expectedListResponse.getResults().add(new Film());
        expectedListResponse.getResults().add(new Film());

        fragment.getView().findViewById(R.id.bt_sw_submit).performClick();

        Mockito.verify(starWarsAPI).getFilms(filmListResponseCaptor.capture());
        filmListResponseCaptor.getValue().success(expectedListResponse, null);

        RecyclerView recyclerView = (RecyclerView)fragment.getView().findViewById(R.id.rv_sw_results);
        Assert.assertEquals(3, recyclerView.getAdapter().getItemCount());
    }

    @Test(timeout = 1000)
    public void testOttoFragment_GetFilmResults(){
        final AtomicBoolean testDone = new AtomicBoolean(false);

        OttoFragment fragment = OttoFragment.newInstance();
        SupportFragmentTestUtil.startFragment(fragment, MainActivity.class);

        // test event
        Object event = new Object() {
            @Subscribe
            public void onResults(FilmListResponse FilmListResponse) {
                testDone.set(true);
            }
        };
        bus.register(event);

        fragment.getView().findViewById(R.id.bt_otto_submit).performClick();

        Mockito.verify(starWarsAPI).getFilms(filmListResponseCaptor.capture());
        filmListResponseCaptor.getValue().success(new FilmListResponse(), null);

        while(!testDone.get());

        bus.unregister(event);
    }

    @Test(timeout = 1000)
    public void testOttoFragment_DeadEvent(){
        final AtomicBoolean testDone = new AtomicBoolean(false);

        OttoFragment fragment = OttoFragment.newInstance();
        SupportFragmentTestUtil.startFragment(fragment, MainActivity.class);

        // test dead event
        Object event = new Object() {
            @Subscribe
            public void onDeadEvent(DeadEvent deadEvent){
                testDone.set(true);
            }
        };
        bus.register(event);

        fragment.getView().findViewById(R.id.bt_otto_dead_event).performClick();

        Mockito.verify(starWarsAPI).getPeople(peopleListCaptor.capture());
        peopleListCaptor.getValue().success(new PeopleListResponse(), null);

        while(!testDone.get());

        bus.unregister(event);
    }
}