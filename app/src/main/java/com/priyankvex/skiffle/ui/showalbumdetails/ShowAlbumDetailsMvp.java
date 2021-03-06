package com.priyankvex.skiffle.ui.showalbumdetails;

import com.priyankvex.skiffle.model.AlbumDetails;
import com.priyankvex.skiffle.model.TrackItem;

/**
 * Created by @priyankvex on 28/3/17.
 */

public interface ShowAlbumDetailsMvp {

    interface ShowAlbumDetailsView{
        void showErrorUi();
        void showAlbumDetails(AlbumDetails album);
        void reenableLikeButton();
        void setLikedButtonStatus(boolean likedStatus);
        void onAlbumTrackClicked(TrackItem trackItem, int position);
    }

    interface ShowAldumDetailsPresenter{
        void getAlbumDetails(String albumId);
        void saveAlbumTOFavorite();
        void setSavedAlbum(boolean status);
        void deleteAlbumFromFavorites();
        AlbumDetails getAlbumDetails();
        void onStop();
    }
}
