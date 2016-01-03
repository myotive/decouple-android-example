package com.dynamit.decoupleandroid.network.models;

import com.dynamit.decoupleandroid.network.models.common.BaseResponse;
import com.dynamit.decoupleandroid.network.models.common.Film;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelyotive_hr on 12/6/15.
 */
public class FilmListResponse extends BaseResponse {
    @Expose
    List<Film> results = new ArrayList<>();

    public List<Film> getResults() {
        return results;
    }

    public void setResults(List<Film> results) {
        this.results = results;
    }

    public static List<String> ConvertToList(FilmListResponse filmListResponse) {
        List<String> res = new ArrayList<>();
        for(Film f : filmListResponse.getResults()){
            res.add(f.getTitle());
        }

        return res;
    }
}
