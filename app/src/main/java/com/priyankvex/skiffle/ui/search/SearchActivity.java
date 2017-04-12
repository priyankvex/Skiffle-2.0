package com.priyankvex.skiffle.ui.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.priyankvex.skiffle.SkiffleApp;
import com.priyankvex.skiffle.component.DaggerSearchComponent;
import com.priyankvex.skiffle.component.SearchComponent;
import com.priyankvex.skiffle.datasource.SearchSuggestionsProvider;
import com.priyankvex.skiffle.module.SearchModule;

import javax.inject.Inject;

/**
 * Created by priyankvex on 10/4/17.
 */

public class SearchActivity extends AppCompatActivity implements SearchMvp.SearchView{

    @Inject
    SearchPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SearchComponent component = DaggerSearchComponent.builder()
                .searchModule(new SearchModule(this))
                .skiffleApplicationComponent(SkiffleApp.getInstance().getComponent())
                .build();
        component.inject(this);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("owlcity", "Searching for query : " + query);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchSuggestionsProvider.AUTHORITY, SearchSuggestionsProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            mPresenter.getSearchResults(query);

        }

    }
}
