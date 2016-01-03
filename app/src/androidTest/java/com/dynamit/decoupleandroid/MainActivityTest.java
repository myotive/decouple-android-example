package com.dynamit.decoupleandroid;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.design.widget.NavigationView;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.UiThreadTest;
import android.widget.Button;

import com.dynamit.decoupleandroid.network.api.StarWarsAPI;
import com.dynamit.decoupleandroid.network.models.FilmListResponse;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;

import retrofit.Callback;

/**
 * TODO: Add a class header comment!
 * Created by michaelyotive on 11/29/15.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Inject
    StarWarsAPI starWarsAPI;

    TestComponent testComponent;

    @Captor
    ArgumentCaptor<Callback<FilmListResponse>> getFilmListCallback;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,
            true);

    @Before
    public void setup(){

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        SampleApplication application = (SampleApplication)instrumentation.getTargetContext().getApplicationContext();

        testComponent = DaggerTestComponent
                .builder()
                .daggerTestApplicationModule(new DaggerTestApplicationModule(application))
                .daggerTestNetworkModule(new DaggerTestNetworkModule())
                .build();

        application.setApplicationComponent(testComponent);
        testComponent.inject(this);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    @UiThreadTest
    public void testDaggerFragment_getFilms(){

        Activity activity = activityRule.getActivity();
        NavigationView navigationView = (NavigationView)activity.findViewById(R.id.nav_view);

        // go to dagger fragment
        navigationView.getMenu().getItem(1).setChecked(true);

        Button button = (Button)activityRule.getActivity().findViewById(R.id.bt_sw_submit);
        button.performClick();

        Mockito.verify(starWarsAPI).getFilms(getFilmListCallback.capture());

        FilmListResponse testResponse = new FilmListResponse();
        getFilmListCallback.getValue().success(testResponse, null);

        RecyclerView results = (RecyclerView)activity.findViewById(R.id.rv_sw_results);
        Assert.assertEquals(testResponse.getResults().size() == 0,
                results.getAdapter().getItemCount() == 0);
    }
}
