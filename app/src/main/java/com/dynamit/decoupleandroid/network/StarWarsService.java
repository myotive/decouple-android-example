package com.dynamit.decoupleandroid.network;

import android.content.Context;

import com.dynamit.decoupleandroid.SampleApplication;
import com.dynamit.decoupleandroid.network.api.StarWarsAPI;
import com.dynamit.decoupleandroid.network.models.FilmListResponse;
import com.dynamit.decoupleandroid.network.models.PeopleListResponse;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by michaelyotive_hr on 12/22/15.
 */
public class StarWarsService {
    @Inject
    StarWarsAPI starWarsAPI;
    @Inject
    Bus bus;

    public StarWarsService(Context context){
        SampleApplication.getApplication(context).getApplicationComponent().inject(this);
    }

    public void getFilms(){
        starWarsAPI.getFilms(new Callback<FilmListResponse>() {
            @Override
            public void success(FilmListResponse filmListResponse, Response response) {
                bus.post(filmListResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                bus.post(error);
            }
        });
    }

    public void getPeople(){
        starWarsAPI.getPeople(new Callback<PeopleListResponse>() {
            @Override
            public void success(PeopleListResponse peopleListResponse, Response response) {
                bus.post(peopleListResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                bus.post(error);
            }
        });
    }
}
