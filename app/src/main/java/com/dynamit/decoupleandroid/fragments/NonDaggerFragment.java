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

import com.dynamit.decoupleandroid.BuildConfig;
import com.dynamit.decoupleandroid.R;
import com.dynamit.decoupleandroid.adapter.SearchResultAdapter;
import com.dynamit.decoupleandroid.network.api.StarWarsAPI;
import com.dynamit.decoupleandroid.network.models.FilmListResponse;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by michaelyotive_hr on 12/6/15.
 */
public class NonDaggerFragment extends Fragment {

    // Dependencies
    StarWarsAPI starWarsAPI;

    // Private members
    LinearLayoutManager linearLayoutManager;
    RecyclerView searchResults;
    SearchResultAdapter searchResultAdapter;
    Button submit;

    public static NonDaggerFragment newInstance() {
        return new NonDaggerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        starWarsAPI = createAPI();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_starwars,container, false);

        setupUI(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Non-Dagger Fragment");
    }

    public void setupUI(View view){
        linearLayoutManager = new LinearLayoutManager(getContext());

        searchResultAdapter = new SearchResultAdapter();

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

                        List<String> searchResults = FilmListResponse.ConvertToList(filmListResponse);

                        searchResultAdapter.setSearchResults(searchResults);
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

    private StarWarsAPI createAPI(){

        OkClient client = getClient();
        RequestInterceptor requestInterceptor = getInterceptor();
        RestAdapter.LogLevel logLevel = BuildConfig.LOG_NETWORK
                ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE;
        GsonConverter gsonConverter = getGsonConverter();

        RestAdapter adapter = new RestAdapter.Builder()
                .setClient(client)
                .setLogLevel(logLevel)
                .setEndpoint(BuildConfig.API_URL)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(gsonConverter)
                .build();

        return adapter.create(StarWarsAPI.class);
    }

    private GsonConverter getGsonConverter() {
        return new GsonConverter(new GsonBuilder().create());
    }

    private RequestInterceptor getInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/json");
                request.addHeader("Content-Type", "application/json");
            }
        };
    }

    private OkClient getClient() {
        final OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(60, TimeUnit.SECONDS);
        client.setConnectTimeout(60, TimeUnit.SECONDS);

        return new OkClient(client);
    }
}
