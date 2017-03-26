package com.priyankvex.skiffle.home;

import android.util.Log;

import com.google.gson.JsonObject;
import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.datasource.SpotifyService;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


/**
 * Created by @priyankvex on 25/3/17.
 */

public class NewReleasesPresenter implements NewReleasesMvp.NewReleasesPresenter{

    private SpotifyService mSpotifyService;

    private DataSourceContract mDataSource;

    private NewReleasesMvp.NewReleasesView mView;

    private DisposableObserver<JsonObject> mDisposableObserver;

    @Inject
    NewReleasesPresenter(SpotifyService spotifyService,
                         NewReleasesMvp.NewReleasesView view,
                         DataSourceContract dataSource){
        this.mSpotifyService = spotifyService;
        this.mView = view;
        this.mDataSource = dataSource;
        mView.showErrorUi("");
    }

    void testMethod(){
        Log.d(getClass().getName(), "New Release Presenter Test method");
    }

    /**
     * Gets new releases from the API and updates the view accordingly
     */
    @Override
    public void getNewReleases() {
        mDisposableObserver = new DisposableObserver<JsonObject>() {
            @Override
            public void onNext(JsonObject value) {
                Log.d(getClass().getName(), value.toString());
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException){
                    int code = ((HttpException) e).response().code();
                    Log.d(getClass().getName(), "Http Exception code : " + code);
                    if (code == 401){
                        mDataSource.renewAuthToken(new DataSourceContract.AuthRenewCallback() {
                            @Override
                            public void onAuthRenewed() {
                                getNewReleases();
                            }

                            @Override
                            public void onAuthFailed() {
                                Log.e(getClass().getName(), "Failed to renew auth token");
                                mView.showErrorUi("Authentication Failure. Please try again.");
                            }
                        });
                    }
                }
                Log.d(getClass().getName(), e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        Map<String, String> headers = mDataSource.getAuthHeader();
        mSpotifyService.getNewReleases(headers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mDisposableObserver);
    }

    void cancelSubscriptions(){
        mDisposableObserver.dispose();
    }


}
