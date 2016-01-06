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
import android.widget.Toast;

import com.dynamit.decoupleandroid.R;
import com.dynamit.decoupleandroid.SampleApplication;
import com.dynamit.decoupleandroid.adapter.SearchResultAdapter;
import com.dynamit.decoupleandroid.di.ApplicationComponent;
import com.dynamit.decoupleandroid.di.DaggerStarWarsFragmentComponent;
import com.dynamit.decoupleandroid.network.api.StarWarsAPI;
import com.dynamit.decoupleandroid.network.models.FilmListResponse;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by michaelyotive_hr on 12/6/15.
 */
public class StarWarsFragment extends Fragment {

    // Injections
    @Inject
    StarWarsAPI starWarsAPI;
    @Inject
    SearchResultAdapter searchResultAdapter;

    // Private members
    LinearLayoutManager linearLayoutManager;
    RecyclerView searchResults;
    Button submit;

    public static StarWarsFragment newInstance() {
        return new StarWarsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtain the ApplicationComponent created in the SampleApplication class
        ApplicationComponent applicationComponent = SampleApplication
                .getApplication(getContext())
                .getApplicationComponent();

        // Build the StarWarsFragmentComponent, which has a Dependency on ApplicationComponent.
        // Pass the instance of ApplicationComponent into StarWarsFragment, which keeps ApplicationScope -
        // meaning any singletons created in ApplicationComponent will be the same reference in
        // StarWarsFragmentComponent.
        // Once StarWarsFragmentComponent is destroyed, only StarWarsFragmentComponent's instances
        // will be cleaned up.
        DaggerStarWarsFragmentComponent
                .builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_starwars, container, false);

        setupUI(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Star Wars Fragment");
    }

    public void setupUI(View view){
        linearLayoutManager = new LinearLayoutManager(getContext());

        searchResults = (RecyclerView)view.findViewById(R.id.rv_sw_results);
        searchResults.setLayoutManager(linearLayoutManager);
        searchResults.setAdapter(searchResultAdapter);

        submit = (Button)view.findViewById(R.id.bt_sw_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starWarsAPI.getFilms(new Callback<FilmListResponse>() {
                    @Override
                    public void success(FilmListResponse filmListResponse, Response response) {

                        // Convert results to list of strings
                        List<String> searchResults = FilmListResponse.ConvertToList(filmListResponse);

                        // Update search results adapter with new data.
                        searchResultAdapter.setSearchResults(searchResults);
                        // Notify UI to update.
                        searchResultAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getContext(), "Error getting search results", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
