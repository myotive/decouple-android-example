package com.dynamit.decoupleandroid.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dynamit.decoupleandroid.R;
import com.dynamit.decoupleandroid.SampleApplication;
import com.dynamit.decoupleandroid.adapter.SearchResultAdapter;
import com.dynamit.decoupleandroid.network.StarWarsService;
import com.dynamit.decoupleandroid.network.models.FilmListResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by michaelyotive_hr on 12/22/15.
 */
public class OttoFragment extends Fragment {

    @Inject
    StarWarsService starWarsService;
    @Inject
    Bus bus;

    // Private members
    LinearLayoutManager linearLayoutManager;
    RecyclerView searchResults;
    SearchResultAdapter searchResultAdapter;
    Button submit, deadEvent;

    public static OttoFragment newInstance() {
        return new OttoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SampleApplication
                .getApplication(getContext())
                .getApplicationComponent()
                .inject(this);

        View view = inflater.inflate(R.layout.fragment_otto,container, false);

        setupUI(view);

        return view;
    }

    public void setupUI(View view){
        linearLayoutManager = new LinearLayoutManager(getContext());

        searchResultAdapter = new SearchResultAdapter();

        searchResults = (RecyclerView)view.findViewById(R.id.rv_otto_results);
        searchResults.setLayoutManager(linearLayoutManager);
        searchResults.setAdapter(searchResultAdapter);

        submit = (Button)view.findViewById(R.id.bt_otto_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starWarsService.getFilms();
            }
        });

        deadEvent = (Button)view.findViewById(R.id.bt_otto_dead_event);
        deadEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starWarsService.getPeople();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);

        getActivity().setTitle("Otto Fragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Subscribe
    public void onGetFilmsResult(FilmListResponse filmListResponse){
        List<String> searchResults = FilmListResponse.ConvertToList(filmListResponse);

        searchResultAdapter.setSearchResults(searchResults);
        searchResultAdapter.notifyDataSetChanged();
    }
}
