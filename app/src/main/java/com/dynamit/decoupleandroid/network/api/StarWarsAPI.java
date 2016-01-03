package com.dynamit.decoupleandroid.network.api;

import com.dynamit.decoupleandroid.network.models.FilmListResponse;
import com.dynamit.decoupleandroid.network.models.common.Film;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by michaelyotive_hr on 12/6/15.
 */
public interface StarWarsAPI {

    @GET("/films")
    void getFilms(Callback<FilmListResponse> filmListResponseCallback);

    @GET("/films/{id}")
    void getFilmById(@Path("id") String Id, Callback<Film> filmCallback);
}
