package com.priyankvex.skiffle.ui.search;

import android.util.Log;

import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.datasource.SpotifyService;
import com.priyankvex.skiffle.model.AlbumItem;
import com.priyankvex.skiffle.model.ArtistItem;
import com.priyankvex.skiffle.model.SearchResults;
import com.priyankvex.skiffle.model.SearchResultsListItem;
import com.priyankvex.skiffle.model.TrackItem;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by priyankvex on 13/4/17.
 */

public class SearchPresenter implements SearchMvp.SearchPresenter{

    private SpotifyService mSpotifyService;

    private SearchMvp.SearchView mView;

    private DisposableObserver<ArrayList<SearchResultsListItem>> mDisposableObserver;

    private DataSourceContract mDataSource;

    @Inject
    SearchPresenter(SpotifyService spotifyService,
                    SearchMvp.SearchView view,
                    DataSourceContract dataSource){
        this.mSpotifyService = spotifyService;
        this.mView = view;
        this.mDataSource = dataSource;
    }

    @Override
    public void getSearchResults(String query) {

        mDisposableObserver = new DisposableObserver<ArrayList<SearchResultsListItem>>() {
            @Override
            public void onNext(ArrayList<SearchResultsListItem> value) {
                Log.d("owlcity", value.get(1).title + " first song in search results");
                Log.d("owlcity", value.get(5).title + " first album in search results");
                Log.d("owlcity", value.get(9).title + " first artist in search results");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("owlcity", e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        Map<String, String> headers = mDataSource.getAuthHeader();
        mSpotifyService.getSearchResults(query, headers)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Function<SearchResults, ArrayList<SearchResultsListItem>>() {
                    @Override
                    public ArrayList<SearchResultsListItem> apply(SearchResults searchResults) throws Exception {

                        return getSearchResultItemsFromSearchResults(searchResults);
                    }
                })
                .subscribeWith(mDisposableObserver);
    }

    private ArrayList<SearchResultsListItem> getSearchResultItemsFromSearchResults
            (SearchResults searchResults){
        ArrayList<SearchResultsListItem> list = new ArrayList<>();
        // first songs header
        SearchResultsListItem songHeaderItem = new SearchResultsListItem();
        songHeaderItem.viewType = SearchResultsListItem.ViewType.HEADER_VIEW;
        songHeaderItem.title = "Songs";
        list.add(songHeaderItem);
        // add the songs search preview results
        for (TrackItem track : searchResults.tracks.items){
            SearchResultsListItem songItem = new SearchResultsListItem();
            songItem.viewType = SearchResultsListItem.ViewType.ITEM_VIEW;
            songItem.title = track.name;
            songItem.subTitle = track.album.name;
            songItem.itemType = SearchResultsListItem.ItemType.TRACK;
            list.add(songItem);
        }
        // second album header
        SearchResultsListItem albumHeaderItem = new SearchResultsListItem();
        songHeaderItem.viewType = SearchResultsListItem.ViewType.HEADER_VIEW;
        songHeaderItem.title = "Albums";
        list.add(albumHeaderItem);
        // add the albums search preview results
        for (AlbumItem album : searchResults.albums.items){
            SearchResultsListItem albumItem = new SearchResultsListItem();
            albumItem.viewType = SearchResultsListItem.ViewType.ITEM_VIEW;
            albumItem.title = album.name;
            albumItem.subTitle = album.artists.get(0).name;
            albumItem.itemType = SearchResultsListItem.ItemType.ALBUM;
            list.add(albumItem);
        }
        // third items header
        SearchResultsListItem artistsHeaderItem = new SearchResultsListItem();
        songHeaderItem.viewType = SearchResultsListItem.ViewType.HEADER_VIEW;
        songHeaderItem.title = "Artists";
        list.add(artistsHeaderItem);
        // add the albums search preview results
        for (ArtistItem artist : searchResults.artists.items){
            SearchResultsListItem artistItem = new SearchResultsListItem();
            artistItem.viewType = SearchResultsListItem.ViewType.ITEM_VIEW;
            artistItem.title = artist.name;
            artistItem.subTitle = artist.type;
            artistItem.itemType = SearchResultsListItem.ItemType.ARTIST;
            list.add(artistItem);
        }
        return list;
    }

}
