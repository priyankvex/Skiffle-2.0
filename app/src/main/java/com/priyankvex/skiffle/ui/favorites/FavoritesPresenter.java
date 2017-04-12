package com.priyankvex.skiffle.ui.favorites;

import android.util.Log;

import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.model.AlbumItem;

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

    @Inject
    FavoritesPresenter(DataSourceContract dataSource,
                       FavoritesMvp.FavoritesView view){
        this.mDataSource = dataSource;
        this.mView = view;
    }

    @Override
    public void loadFavoriteTracks() {

    }

    @Override
    public void loadFavoriteAlbums() {
        Log.d("owlcity", "Loading albums from database");

        Log.d("owlcity", "Loading tracks from database");

        mDisposableObserver = new DisposableObserver<ArrayList<AlbumItem>>() {
            @Override
            public void onNext(ArrayList<AlbumItem> value) {
                Log.d("owlcity", "No. of favorite albums : " + value.size());
                mView.showFavoriteAlbums(value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mDataSource.loadFavoriteAlbums()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(mDisposableObserver);
    }


}
