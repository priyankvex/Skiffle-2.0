package com.priyankvex.skiffle.ui.search;

import android.util.Log;

import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.datasource.SpotifyService;
import com.priyankvex.skiffle.model.SearchResults;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by priyankvex on 13/4/17.
 */

public class SearchPresenter implements SearchMvp.SearchPresenter{

    private SpotifyService mSpotifyService;

    private SearchMvp.SearchView mView;

    private DisposableObserver<SearchResults> mDisposableObserver;

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

        mDisposableObserver = new DisposableObserver<SearchResults>() {
            @Override
            public void onNext(SearchResults value) {
                Log.d("owlcity", "Search results received : " + value.tracks.items.get(0).name);
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
                .subscribeWith(mDisposableObserver);
    }

}
