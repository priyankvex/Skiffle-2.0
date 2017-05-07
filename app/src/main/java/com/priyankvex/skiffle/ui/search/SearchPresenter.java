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
    private DisposableObserver<SearchResults> mSearchResultsObserver;

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
                mView.showSearchResults(value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("owlcity", e.getLocalizedMessage());
                mView.showErrorUi();
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

    @Override
    public void getSongResults(String query) {

        mSearchResultsObserver = new DisposableObserver<SearchResults>() {
            @Override
            public void onNext(SearchResults value) {
                mView.showSongResults(value.tracks);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("owlcity", e.getLocalizedMessage());
                mView.showErrorUi();
            }

            @Override
            public void onComplete() {

            }
        };

        Map<String, String> headers = mDataSource.getAuthHeader();
        mSpotifyService.getSongResults(query, headers)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(mSearchResultsObserver);
    }

    @Override
    public void getAlbumResults(String query) {

        mSearchResultsObserver = new DisposableObserver<SearchResults>() {
            @Override
            public void onNext(SearchResults value) {
                mView.showAlbumSearchResults(value.albums);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("owlcity", e.getLocalizedMessage());
                mView.showErrorUi();
            }

            @Override
            public void onComplete() {

            }
        };

        Map<String, String> headers = mDataSource.getAuthHeader();
        mSpotifyService.getAlbumResults(query, headers)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(mSearchResultsObserver);
    }

    @Override
    public void getArtistResults(String query) {

        mSearchResultsObserver = new DisposableObserver<SearchResults>() {
            @Override
            public void onNext(SearchResults value) {
                mView.showArtistSearchResults(value.artists);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("owlcity", e.getLocalizedMessage());
                mView.showErrorUi();
            }

            @Override
            public void onComplete() {

            }
        };

        Map<String, String> headers = mDataSource.getAuthHeader();
        mSpotifyService.getArtistResults(query, headers)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(mSearchResultsObserver);
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
            if (track.album.images.size() >= 3){
                songItem.thumbImageUrl = track.album.images.get(2).url;
            }
            songItem.itemId = track.id;
            songItem.itemTitle = track.name;
            list.add(songItem);
        }
        // second album header
        SearchResultsListItem albumHeaderItem = new SearchResultsListItem();
        albumHeaderItem.viewType = SearchResultsListItem.ViewType.HEADER_VIEW;
        albumHeaderItem.title = "Albums";
        list.add(albumHeaderItem);
        // add the albums search preview results
        for (AlbumItem album : searchResults.albums.items){
            SearchResultsListItem albumItem = new SearchResultsListItem();
            albumItem.viewType = SearchResultsListItem.ViewType.ITEM_VIEW;
            albumItem.title = album.name;
            albumItem.subTitle = album.artists.get(0).name;
            albumItem.itemType = SearchResultsListItem.ItemType.ALBUM;
            if (album.images.size() >= 3){
                albumItem.thumbImageUrl = album.images.get(2).url;
            }
            albumItem.itemId = album.id;
            albumItem.itemTitle = album.name;
            list.add(albumItem);
        }
        // third items header
        SearchResultsListItem artistsHeaderItem = new SearchResultsListItem();
        artistsHeaderItem.viewType = SearchResultsListItem.ViewType.HEADER_VIEW;
        artistsHeaderItem.title = "Artists";
        list.add(artistsHeaderItem);
        // add the albums search preview results
        for (ArtistItem artist : searchResults.artists.items){
            SearchResultsListItem artistItem = new SearchResultsListItem();
            artistItem.viewType = SearchResultsListItem.ViewType.ITEM_VIEW;
            artistItem.title = artist.name;
            artistItem.subTitle = artist.type;
            artistItem.itemType = SearchResultsListItem.ItemType.ARTIST;
            if (artist.images.size() >= 3){
                artistItem.thumbImageUrl = artist.images.get(2).url;
            }
            artistItem.itemId = artist.id;
            artistItem.itemTitle = artist.name;
            list.add(artistItem);
        }
        return list;
    }

}
