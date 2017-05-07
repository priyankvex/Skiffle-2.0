package com.priyankvex.skiffle.ui.showartistdetails;

import com.priyankvex.skiffle.model.ArtistDetails;
import com.priyankvex.skiffle.model.TrackItem;

import java.util.ArrayList;

/**
 * Created by priyankvex on 3/5/17.
 */

public interface ShowArtistDetailsMvp {

    interface ShowArtistDetailsView{
        void showArtistDetails(ArtistDetails artistDetails);
        void showArtistDetailsErrorUi();
        void showArtistTopTracks(ArrayList<TrackItem> trackList);
        void showArtistTopTrackErrorUi();
        void onArtistTrackClicked(TrackItem trackItem, int position);
    }

    interface ShowArtistDetailsPresenter{
        void getArtistDetails(String artistId);
        void getArtistTopTracks(String artistId);
        void onStop();
    }
}
