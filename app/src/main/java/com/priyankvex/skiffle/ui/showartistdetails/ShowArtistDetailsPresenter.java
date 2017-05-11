package com.priyankvex.skiffle.ui.showartistdetails;

import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.datasource.SpotifyService;
import com.priyankvex.skiffle.model.ArtistDetails;
import com.priyankvex.skiffle.model.ArtistTopTracks;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by priyankvex on 3/5/17.
 */

public class ShowArtistDetailsPresenter implements ShowArtistDetailsMvp.ShowArtistDetailsPresenter{

    private SpotifyService mSpotifyService;

    private DataSourceContract mDataSource;

    private ShowArtistDetailsMvp.ShowArtistDetailsView mView;

    private ArtistDetails mArtistDetails;

    private ArtistTopTracks mArtistTopTracks;

    private DisposableObserver<ArtistDetails> mDisposableArtistDetails;

    private DisposableObserver<ArtistTopTracks> mDisposableArtistTopTracks;

    @Inject
    ShowArtistDetailsPresenter(ShowArtistDetailsMvp.ShowArtistDetailsView view,
                               DataSourceContract dataSource,
                               SpotifyService spotifyService){
        this.mSpotifyService = spotifyService;
        this.mDataSource = dataSource;
        this.mView = view;
    }


    @Override
    public void getArtistDetails(String artistId) {
        mDisposableArtistDetails = new DisposableObserver<ArtistDetails>() {
            @Override
            public void onNext(ArtistDetails value) {
                mArtistDetails = value;
                mView.showArtistDetails(value);
            }

            @Override
            public void onError(Throwable e) {
                ////Log.e("owlcity", e.getLocalizedMessage());
                mView.showArtistDetailsErrorUi();
            }

            @Override
            public void onComplete() {
                //Log.d("owlcity", "Loading artist details completed");
            }
        };

        mSpotifyService.getArtist(artistId, mDataSource.getAuthHeader())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mDisposableArtistDetails);
    }

    @Override
    public void getArtistTopTracks(String artistId) {
        mDisposableArtistTopTracks = new DisposableObserver<ArtistTopTracks>() {
            @Override
            public void onNext(ArtistTopTracks value) {
                mArtistTopTracks = value;
                mView.showArtistTopTracks(value.tracks);
            }

            @Override
            public void onError(Throwable e) {
                //Log.e("owlcity", e.getLocalizedMessage());
                mView.showArtistTopTrackErrorUi();
            }

            @Override
            public void onComplete() {
                //Log.d("owlcity", "Loading artist tracks completed");
            }
        };

        mSpotifyService.getArtistTopTracks(artistId, mDataSource.getAuthHeader())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mDisposableArtistTopTracks);
    }

    @Override
    public void onStop() {
        mDisposableArtistDetails.dispose();
        mDisposableArtistTopTracks.dispose();
    }
}
