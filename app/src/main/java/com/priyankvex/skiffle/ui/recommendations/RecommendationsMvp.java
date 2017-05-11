package com.priyankvex.skiffle.ui.recommendations;

import com.priyankvex.skiffle.model.TrackItem;
import com.priyankvex.skiffle.model.TrackList;

import java.util.ArrayList;

/**
 * Created by priyankvex on 8/5/17.
 */

public interface RecommendationsMvp {

    interface View{
        void showRecommendations(ArrayList<TrackItem> recommendedTracks);
        void showNoRecommendationsUi();
        void showErrorUi();
        void onRecommendationItemClicked(TrackItem trackItem, int position);
    }

    interface Presenter{
        void loadRecommendations();
    }
}
