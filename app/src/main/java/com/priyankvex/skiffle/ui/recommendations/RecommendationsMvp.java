package com.priyankvex.skiffle.ui.recommendations;

import com.priyankvex.skiffle.model.TrackList;

/**
 * Created by priyankvex on 8/5/17.
 */

public interface RecommendationsMvp {

    interface View{
        void showRecommendations(TrackList recommendedTracks);
        void showNoRecommendationsUi();
        void showErrorUi();
    }

    interface Presenter{
        void loadRecommendations();
    }
}
