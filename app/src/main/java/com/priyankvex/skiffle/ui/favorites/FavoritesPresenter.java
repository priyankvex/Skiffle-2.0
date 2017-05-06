package com.priyankvex.skiffle.ui.favorites;

import android.util.Log;

import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.model.AlbumItem;
import com.priyankvex.skiffle.model.TrackItem;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by priyankvex on 9/4/17.
 */

class FavoritesPresenter implements FavoritesMvp.FavoritesPresenter{

    private DataSourceContract mDataSource;

    private FavoritesMvp.FavoritesView mView;

    private DisposableObserver<ArrayList<AlbumItem>> mDisposableObserver;

    private DisposableObserver<ArrayList<TrackItem>> mTracksDisposableObserver;

    @Inject
    FavoritesPresenter(DataSourceContract dataSource,
                       FavoritesMvp.FavoritesView view){
        this.mDataSource = dataSource;
        this.mView = view;
    }

    @Override
    public void loadFavoriteTracks() {
        mTracksDisposableObserver = new DisposableObserver<ArrayList<TrackItem>>() {
            @Override
            public void onNext(ArrayList<TrackItem> value) {
                if (value.size() == 0){
                    mView.showEmptyTracksUi();
                }
                else{
                    mView.showFavoriteTracks(value);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("owlcity", e.getLocalizedMessage());
                mView.showEmptyTracksUi();
            }

            @Override
            public void onComplete() {
                Log.d("owlcity", "Loading favorite tracks completed");
            }
        };

        mDataSource.loadFavoriteTracks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mTracksDisposableObserver);
    }

    @Override
    public void loadFavoriteAlbums() {

        mDisposableObserver = new DisposableObserver<ArrayList<AlbumItem>>() {
            @Override
            public void onNext(ArrayList<AlbumItem> value) {
                if (value.size() == 0){
                    mView.showEmptyAlbumsUi();
                }
                else{
                    mView.showFavoriteAlbums(value);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("owlcity", e.getLocalizedMessage());
                mView.showEmptyAlbumsUi();
            }

            @Override
            public void onComplete() {
                Log.d("owlcity", "Loading favorite albums finished");
            }
        };

        mDataSource.loadFavoriteAlbums()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(mDisposableObserver);
    }


}
