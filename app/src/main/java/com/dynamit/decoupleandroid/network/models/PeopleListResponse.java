package com.dynamit.decoupleandroid.network.models;

import com.dynamit.decoupleandroid.network.models.common.BaseResponse;
import com.dynamit.decoupleandroid.network.models.common.Person;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelyotive_hr on 1/3/16.
 */
public class PeopleListResponse extends BaseResponse {
    @Expose
    List<Person> results = new ArrayList<Person>();

    public List<Person> getResults() {
        return results;
    }

    public void setResults(List<Person> results) {
        this.results = results;
    }
}
