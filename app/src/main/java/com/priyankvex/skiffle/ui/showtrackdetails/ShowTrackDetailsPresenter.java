package com.priyankvex.skiffle.ui.showtrackdetails;

import android.util.Log;

import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.datasource.SpotifyService;
import com.priyankvex.skiffle.model.TrackDetails;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by priyankvex on 4/5/17.
 */

public class ShowTrackDetailsPresenter implements ShowTrackDetailsMvp.ShowTrackDetailsPresenter{

    private SpotifyService mSpotifyService;

    private DataSourceContract mDataSource;

    private ShowTrackDetailsMvp.ShowTrackDetailsView mView;

    private boolean comingFromFavorites = false;

    private DisposableObserver<TrackDetails> mDisposableObserver;

    public TrackDetails getTrackDetails() {
        return mTrackDetails;
    }

    private TrackDetails mTrackDetails;

    @Inject
    ShowTrackDetailsPresenter(SpotifyService spotifyService,
                              DataSourceContract dataSource,
                              ShowTrackDetailsMvp.ShowTrackDetailsView view){
        this.mSpotifyService = spotifyService;
        this.mDataSource = dataSource;
        this.mView = view;
    }


    @Override
    public void loadTrackDetails(String trackId) {
        if (comingFromFavorites){
            loadTrackFromLocalStorage(trackId);
            mView.toggleLikeButton(true);
        }
        else{
            loadTrackDetailsFromRemoteApi(trackId);
            checkTrackPresentInFavorites(trackId);
        }
    }

    private void checkTrackPresentInFavorites(String trackId){
        mDataSource.isTrackSaved(trackId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean value) {
                        mView.toggleLikeButton(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("owlcity", e.getLocalizedMessage());
                        mView.toggleLikeButton(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadTrackDetailsFromRemoteApi(String trackId){
        mDisposableObserver = new DisposableObserver<TrackDetails>() {
            @Override
            public void onNext(TrackDetails value) {
                mTrackDetails = value;
                mView.showTrackDetails(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorUi();
                Log.e("owlcity", e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Log.d("owlcity", "Loading track details completed");
            }
        };

        mSpotifyService.getTrack(trackId, mDataSource.getAuthHeader())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mDisposableObserver);
    }

    private void loadTrackFromLocalStorage(final String trackId){
        mDisposableObserver = new DisposableObserver<TrackDetails>() {
            @Override
            public void onNext(TrackDetails value) {
                mTrackDetails = value;
                mView.showTrackDetails(value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("owlcity", e.getLocalizedMessage());
                mView.showErrorUi();
            }

            @Override
            public void onComplete() {
                Log.d("owlcity", "Loading track from local storage completed");
            }
        };

        mDataSource.loadTrackDetailsFromTrackId(trackId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mDisposableObserver);
    }

    @Override
    public void setSavedStatus(boolean status) {
        comingFromFavorites = status;
    }

    @Override
    public void saveTrackToFavorites() {
        mDataSource.saveTrackToFavorites(mTrackDetails)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long value) {
                        Log.d("owlcity", "Track " + mTrackDetails.name + " saved in favorites");
                        mView.reneableLikeButton();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.reneableLikeButton();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void deleteTrackFromFavorites() {
        mDataSource.deleteTrackFromFavorites(mTrackDetails.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long value) {
                        mView.reneableLikeButton();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.reneableLikeButton();
                        Log.e("owlcity", e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onStop() {
        mDisposableObserver.dispose();
    }
}
