package com.priyankvex.skiffle.ui.showalbumdetails;

import android.util.Log;

import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.datasource.SpotifyService;
import com.priyankvex.skiffle.model.AlbumDetails;

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

    private DisposableObserver<AlbumDetails> mDisposableObserver;

    private AlbumDetails mAlbum;

    private boolean isSavedAlbum = false;

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
        if (isSavedAlbum){
            getAlbumDetailsFromLocalStorage(albumId);
        }
        else{
            getAlbumDetailsFromRemoteApi(albumId);
        }
    }

    private void getAlbumDetailsFromLocalStorage(String albumId){

        mDisposableObserver = new DisposableObserver<AlbumDetails>() {
            @Override
            public void onNext(AlbumDetails value) {
                if (value.id.equals("album_not_found")){
                    // couldn't find this album id in the database
                    mView.setLikedButtonStatus(false);
                }
                else{
                    mView.setLikedButtonStatus(true);
                    mAlbum = value;
                    mView.showAlbumDetails(value);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("owlcity", "Error loading album details from local storage : " + e.getLocalizedMessage());
                mView.showErrorUi();
            }

            @Override
            public void onComplete() {
                Log.d("owlcity", "Loading album from local storage completed");
            }
        };

        mDataSource.loadAlbumFromAlbumId(albumId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(mDisposableObserver);
    }

    private void getAlbumDetailsFromRemoteApi(final String albumId){

        mDisposableObserver = new DisposableObserver<AlbumDetails>() {
            @Override
            public void onNext(AlbumDetails value) {
                mAlbum = value;
                mView.showAlbumDetails(value);
                // to check whether this album is in favorites or not
                getAlbumDetailsFromLocalStorage(albumId);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("owlcity", "Error loading album details from API : " + e.getLocalizedMessage());
                mView.showErrorUi();
            }

            @Override
            public void onComplete() {
                Log.d("owlcity", "Loading album details completed");
            }
        };

        Map<String, String> headers = mDataSource.getAuthHeader();
        mSpotifyService.getAlbum(albumId, headers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mDisposableObserver);
    }

    @Override
    public void saveAlbumTOFavorite() {
        mDataSource.saveAlbumToFavorite(mAlbum)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long value) {
                        Log.d("owlcity", "Album saved with id : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("owlcity", "On error of save album subscriber\n" + e.getLocalizedMessage());
                        mView.reenableLikeButton();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("owlcity", "On complete of save album subscriber");
                        mView.reenableLikeButton();
                    }
                });

    }

    @Override
    public void deleteAlbumFromFavorites() {
        mDataSource.deleteAlbumFromFavorites(mAlbum.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long value) {
                        mView.reenableLikeButton();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.reenableLikeButton();
                    }

                    @Override
                    public void onComplete() {
                        mView.reenableLikeButton();
                    }
                });
    }

    @Override
    public void setSavedAlbum(boolean savedAlbum) {
        isSavedAlbum = savedAlbum;
    }

    @Override
    public AlbumDetails getAlbumDetails() {
        return mAlbum;
    }

    /**
     * Cancel the subscriptions
     */
    @Override
    public void onStop() {
        mDisposableObserver.dispose();
    }
}
