package com.dynamit.decoupleandroid.network.api;

import com.dynamit.decoupleandroid.network.models.FilmListResponse;
import com.dynamit.decoupleandroid.network.models.PeopleListResponse;
import com.dynamit.decoupleandroid.network.models.common.Film;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Retrofit API of the SWAPI (https://swapi.co/).
 */
public interface StarWarsAPI {

    @GET("/films")
    void getFilms(Callback<FilmListResponse> filmListResponseCallback);

    @GET("/films/{id}")
    void getFilmById(@Path("id") String Id, Callback<Film> filmCallback);

    @GET("/people")
    void getPeople(Callback<PeopleListResponse> peopleListResponseCallback);
}
