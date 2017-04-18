package com.priyankvex.skiffle.ui.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.SkiffleApp;
import com.priyankvex.skiffle.component.DaggerSearchComponent;
import com.priyankvex.skiffle.component.SearchComponent;
import com.priyankvex.skiffle.datasource.SearchSuggestionsProvider;
import com.priyankvex.skiffle.model.SearchResultsListItem;
import com.priyankvex.skiffle.module.SearchModule;
import com.priyankvex.skiffle.ui.home.NewReleasesFragment;
import com.priyankvex.skiffle.util.ActivityUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankvex on 10/4/17.
 */

public class SearchActivity extends AppCompatActivity implements SearchMvp.SearchView,
        ResultsPreviewFragment.ResultsPreviewCommunicator{

    @Inject
    SearchPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ResultsPreviewFragment mResultsPreviewFragment;

    private SearchComponent mComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mComponent = DaggerSearchComponent.builder()
                .searchModule(new SearchModule(this))
                .skiffleApplicationComponent(SkiffleApp.getInstance().getComponent())
                .build();
        mComponent.inject(this);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("owlcity", "Searching for query : " + query);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchSuggestionsProvider.AUTHORITY, SearchSuggestionsProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            setUpToolbar(query);
            mPresenter.getSearchResults(query);
        }

        if (mResultsPreviewFragment == null){
            mResultsPreviewFragment = ResultsPreviewFragment.getInstance();
        }
        ActivityUtil.replaceFragmentInContainer(getSupportFragmentManager(),
                mResultsPreviewFragment, R.id.container);

    }

    @Override
    public void showSearchResults(ArrayList<SearchResultsListItem> results) {
        mResultsPreviewFragment.showSearchResults(results);
    }

    @Override
    public void onSearchResultsPreviewItemClicked(SearchResultsListItem item, int position) {
        Log.d("owlcity", "Item clicked is a : " + item.itemType);
    }

    @Override
    public SearchComponent getSearchComponent() {
        return mComponent;
    }

    private void setUpToolbar(String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
