package com.priyankvex.skiffle.ui.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.SkiffleApp;
import com.priyankvex.skiffle.component.DaggerSearchComponent;
import com.priyankvex.skiffle.component.SearchComponent;
import com.priyankvex.skiffle.datasource.SearchSuggestionsProvider;
import com.priyankvex.skiffle.model.AlbumItem;
import com.priyankvex.skiffle.model.AlbumList;
import com.priyankvex.skiffle.model.ArtistItem;
import com.priyankvex.skiffle.model.ArtistList;
import com.priyankvex.skiffle.model.SearchResultsListItem;
import com.priyankvex.skiffle.model.TrackItem;
import com.priyankvex.skiffle.model.TrackList;
import com.priyankvex.skiffle.module.SearchModule;
import com.priyankvex.skiffle.ui.showalbumdetails.ShowAlbumDetailsActivity;
import com.priyankvex.skiffle.ui.showartistdetails.ShowArtistDetailsActivity;
import com.priyankvex.skiffle.ui.showtrackdetails.ShowTrackDetailsActivity;
import com.priyankvex.skiffle.util.ActivityUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankvex on 10/4/17.
 */

public class SearchActivity extends AppCompatActivity implements SearchMvp.SearchView,
        ResultsPreviewFragment.ResultsPreviewCommunicator,
        SearchResultsFragment.SearchResultsFragmentCommunicator {

    @Inject
    SearchPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ResultsPreviewFragment mResultsPreviewFragment;

    private SearchResultsFragment mSearchResultsFragment;

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
                mResultsPreviewFragment, R.id.container, "results_preview_fragment");

    }

    @Override
    public void showSearchResults(ArrayList<SearchResultsListItem> results) {
        mResultsPreviewFragment.showSearchResults(results);
    }

    @Override
    public void showSongResults(TrackList trackList) {
        mSearchResultsFragment.showSongs(trackList);
    }

    @Override
    public void showAlbumSearchResults(AlbumList albumList) {
        mSearchResultsFragment.showAlbums(albumList);
    }

    @Override
    public void showArtistSearchResults(ArtistList artistList) {
        mSearchResultsFragment.showArtists(artistList);
    }

    @Override
    public void onSearchResultsPreviewItemClicked(SearchResultsListItem item, int position) {
        Log.d("owlcity", "Item clicked is a : " + item.itemType);
        if (item.viewType == SearchResultsListItem.ViewType.HEADER_VIEW){
            switch (item.title){
                case "Songs" :
                    mSearchResultsFragment = SearchResultsFragment.getInstance("songs");
                    break;
                case "Albums" :
                    mSearchResultsFragment = SearchResultsFragment.getInstance("albums");
                    break;
                case "Artists" :
                    mSearchResultsFragment = SearchResultsFragment.getInstance("artists");
                    break;
                default:
                    mSearchResultsFragment = SearchResultsFragment.getInstance("songs");
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //find the fragment by View or Tag
            ResultsPreviewFragment frag = (ResultsPreviewFragment)
                    fragmentManager.findFragmentByTag("results_preview_fragment");
            fragmentTransaction.hide(frag);
            fragmentTransaction.add(R.id.container, mSearchResultsFragment);
            fragmentTransaction.addToBackStack("show_details");
            fragmentTransaction.commit();
        }
        else if (item.viewType == SearchResultsListItem.ViewType.ITEM_VIEW){
            Intent intent;
            switch (item.itemType){
                case TRACK:
                    intent = new Intent(this, ShowTrackDetailsActivity.class);
                    intent.putExtra("track_id", item.itemId);
                    intent.putExtra("track_title", item.itemTitle);
                    startActivity(intent);
                    break;
                case ALBUM:
                    intent = new Intent(this, ShowAlbumDetailsActivity.class);
                    intent.putExtra("album_id", item.itemId);
                    intent.putExtra("album_title", item.itemTitle);
                    startActivity(intent);
                    break;
                case ARTIST:
                    intent = new Intent(this, ShowArtistDetailsActivity.class);
                    intent.putExtra("artist_id", item.itemId);
                    intent.putExtra("artist_title", item.itemTitle);
                    startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public void onArtistItemClicked(ArtistItem item, int position) {
        Intent intent = new Intent(this, ShowArtistDetailsActivity.class);
        intent.putExtra("artist_id", item.id);
        intent.putExtra("artist_title", item.name);
        startActivity(intent);
    }

    @Override
    public void onTrackItemClicked(TrackItem item, int position) {
        Intent intent = new Intent(this, ShowTrackDetailsActivity.class);
        intent.putExtra("track_id", item.id);
        intent.putExtra("track_title", item.name);
        startActivity(intent);
    }

    @Override
    public void onAlbumItemClicked(AlbumItem item, int position) {
        Intent intent = new Intent(this, ShowAlbumDetailsActivity.class);
        intent.putExtra("album_id", item.id);
        intent.putExtra("album_title", item.name);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String query = getIntent().getStringExtra(SearchManager.QUERY);
        toolbar.setTitle(query);
    }

    @Override
    public SearchComponent getSearchComponent() {
        return mComponent;
    }

    @Override
    public void loadSongsForSearch() {
        toolbar.setTitle(toolbar.getTitle() + " - Songs");
        String query = getIntent().getStringExtra(SearchManager.QUERY);
        mPresenter.getSongResults(query);
    }

    @Override
    public void loadAlbumsForSearch() {
        toolbar.setTitle(toolbar.getTitle() + " - Albums");
        String query = getIntent().getStringExtra(SearchManager.QUERY);
        mPresenter.getAlbumResults(query);
    }

    @Override
    public void loadArtistsForSearch() {
        toolbar.setTitle(toolbar.getTitle() + " - Artists");
        String query = getIntent().getStringExtra(SearchManager.QUERY);
        mPresenter.getArtistResults(query);
    }

    private void setUpToolbar(String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
