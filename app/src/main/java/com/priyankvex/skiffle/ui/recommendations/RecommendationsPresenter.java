package com.priyankvex.skiffle.ui.recommendations;

import android.util.Log;

import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.datasource.SpotifyService;
import com.priyankvex.skiffle.model.ArtistTopTracks;
import com.priyankvex.skiffle.model.SearchResults;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by priyankvex on 8/5/17.
 */

public class RecommendationsPresenter implements RecommendationsMvp.Presenter{

    private DataSourceContract mDataSource;

    private SpotifyService mSpotifyService;

    private RecommendationsMvp.View mView;

    private DisposableObserver<Map<String, String>> mSeedsDisposableObserver;

    private DisposableObserver<ArtistTopTracks> mRecommendationsDisposableObserver;

    @Inject
    RecommendationsPresenter(SpotifyService spotifyService,
                             DataSourceContract dataSource,
                             RecommendationsMvp.View view){
        this.mDataSource = dataSource;
        this.mView = view;
        this.mSpotifyService = spotifyService;
    }

    @Override
    public void loadRecommendations() {

        mSeedsDisposableObserver = new DisposableObserver<Map<String, String>>() {
            @Override
            public void onNext(Map<String, String> value) {
                if (value.get("track_seeds").equals("") &&
                        value.get("artist_seeds").equals("")){
                    mView.showNoRecommendationsUi();
                }
                else{
                    makeRecommendationsRequest(value);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("owlcity", e.getLocalizedMessage());
                mView.showErrorUi();
            }

            @Override
            public void onComplete() {
                Log.d("owlcity", "Loading recommendations seeds completed");
            }
        };

        mDataSource.getRecommendationsSeeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mSeedsDisposableObserver);
    }

    private void makeRecommendationsRequest(Map<String, String> seeds){

        mRecommendationsDisposableObserver = new DisposableObserver<ArtistTopTracks>() {
            @Override
            public void onNext(ArtistTopTracks value) {
                mView.showRecommendations(value.tracks);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("owlcity", e.getLocalizedMessage());
                mView.showErrorUi();
            }

            @Override
            public void onComplete() {
                Log.d("owlcity", "loading recommendations completed");
            }
        };

        Map<String, String> queryParams = new HashMap<>();
        if (!seeds.get("track_seeds").equals("")){
            queryParams.put("seed_tracks", seeds.get("track_seeds"));
        }
        if (!seeds.get("artist_seeds").equals("")){
            queryParams.put("seed_artists", seeds.get("artist_seeds"));
        }
        mSpotifyService.getRecommendations(queryParams,
                mDataSource.getAuthHeader())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mRecommendationsDisposableObserver);
    }
}
