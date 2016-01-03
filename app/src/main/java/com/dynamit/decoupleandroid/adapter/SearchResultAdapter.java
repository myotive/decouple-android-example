package com.dynamit.decoupleandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dynamit.decoupleandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelyotive_hr on 12/9/15.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private List<String> searchResults;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public SearchResultAdapter(){
        this.searchResults = new ArrayList<>();
    }
    public SearchResultAdapter(List<String> searchResults){
        this.searchResults = searchResults;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView)LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(searchResults.get(position));
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public List<String> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<String> searchResults) {
        this.searchResults = searchResults;
    }
}
