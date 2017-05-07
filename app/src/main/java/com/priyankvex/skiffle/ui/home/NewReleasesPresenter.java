package com.priyankvex.skiffle.ui.home;

import android.util.Log;

import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.datasource.SpotifyService;
import com.priyankvex.skiffle.model.NewReleases;

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

    private DisposableObserver<NewReleases> mDisposableObserver;

    @Inject
    NewReleasesPresenter(SpotifyService spotifyService,
                         NewReleasesMvp.NewReleasesView view,
                         DataSourceContract dataSource){
        this.mSpotifyService = spotifyService;
        this.mView = view;
        this.mDataSource = dataSource;
    }

    /**
     * Gets new releases from the API and updates the view accordingly
     */
    @Override
    public void getNewReleases() {
        mDisposableObserver = new DisposableObserver<NewReleases>() {
            @Override
            public void onNext(NewReleases newReleases) {
                Log.d(getClass().getName(), newReleases.albums.items + " releases received");
                mView.showNewReleases(newReleases);
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
                else{
                    Log.d(getClass().getName(), e.getLocalizedMessage());
                    mView.showErrorUi("");
                }
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

    @Override
    public void onStop() {
        mDisposableObserver.dispose();
    }
}
