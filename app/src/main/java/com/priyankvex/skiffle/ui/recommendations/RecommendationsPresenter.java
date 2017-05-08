package com.priyankvex.skiffle.ui.recommendations;

import com.priyankvex.skiffle.datasource.DataSource;
import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.datasource.SpotifyService;

import javax.inject.Inject;

/**
 * Created by priyankvex on 8/5/17.
 */

public class RecommendationsPresenter implements RecommendationsMvp.Presenter{

    private DataSourceContract mDataSource;

    private SpotifyService mSpotifyService;

    private RecommendationsMvp.View mView;

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

    }
}
