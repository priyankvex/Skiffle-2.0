package com.priyankvex.skiffle.ui.showalbumdetails;

import android.util.Log;

import com.google.gson.JsonObject;
import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.datasource.SpotifyService;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by @priyankvex on 28/3/17.
 */

class ShowAlbumDetailsPresenter implements ShowAlbumDetailsMvp.ShowAldumDetailsPresenter{

    private SpotifyService mSpotifyService;

    private DataSourceContract mDataSource;

    private ShowAlbumDetailsMvp.ShowAlbumDetailsView mView;

    private DisposableObserver<JsonObject> mDisposableObserver;

    @Inject
    ShowAlbumDetailsPresenter(SpotifyService spotifyService,
                              ShowAlbumDetailsMvp.ShowAlbumDetailsView view,
                              DataSourceContract dataSource){
        this.mSpotifyService = spotifyService;
        this.mDataSource = dataSource;
        this.mView = view;
    }

    @Override
    public void getAlbumDetails(String albumId) {
        mDisposableObserver = new DisposableObserver<JsonObject>() {
            @Override
            public void onNext(JsonObject value) {
                Log.d("owlcity", "Album Details of" + value.get("name").getAsString());
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
        mSpotifyService.getAlbum(albumId, headers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mDisposableObserver);
    }
}
