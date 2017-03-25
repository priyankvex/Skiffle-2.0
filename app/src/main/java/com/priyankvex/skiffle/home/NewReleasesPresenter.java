package com.priyankvex.skiffle.home;

import android.util.Log;

import com.google.gson.JsonObject;
import com.priyankvex.skiffle.datasource.SpotifyService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by @priyankvex on 25/3/17.
 */

public class NewReleasesPresenter implements NewReleasesMvp.NewReleasesPresenter{

    private SpotifyService mSpotifyService;

    private NewReleasesMvp.NewReleasesView mView;

    private DisposableObserver<JsonObject> mDisposableObserver;

    @Inject
    NewReleasesPresenter(SpotifyService spotifyService, NewReleasesMvp.NewReleasesView view){
        this.mSpotifyService = spotifyService;
        this.mView = view;
        mView.showErrorUi("");
    }

    void testMethod(){
        Log.d("owlcity", "New Release Presenter Test method");
    }

    @Override
    public void getPosts() {
        mDisposableObserver = new DisposableObserver<JsonObject>() {
            @Override
            public void onNext(JsonObject value) {
                Log.d("owlcity", value.get("title").getAsString());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("owlcity", e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        mSpotifyService.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mDisposableObserver);
    }

    void cancelSubscriptions(){
        mDisposableObserver.dispose();
    }


}
