package com.priyankvex.skiffle.ui.showtrackdetails;

import com.priyankvex.skiffle.model.TrackDetails;

/**
 * Created by priyankvex on 4/5/17.
 */

public interface ShowTrackDetailsMvp {

    interface ShowTrackDetailsView {
        void showTrackDetails(TrackDetails trackDetails);
        void showErrorUi();
        void toggleLikeButton(boolean status);
        void reneableLikeButton();
    }

    interface ShowTrackDetailsPresenter {
        void loadTrackDetails(String trackId);
        void setSavedStatus(boolean status);
        void saveTrackToFavorites();
        void deleteTrackFromFavorites();
    }
}
