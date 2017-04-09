package com.priyankvex.skiffle.ui.showalbumdetails;

import com.google.gson.JsonObject;
import com.priyankvex.skiffle.model.Album;

import java.util.ArrayList;

/**
 * Created by @priyankvex on 28/3/17.
 */

public interface ShowAlbumDetailsMvp {

    interface ShowAlbumDetailsView{
        void showErrorUi();
        void showAlbumDetails(Album album);
        void showTracks(Album album);
    }

    interface ShowAldumDetailsPresenter{
        void getAlbumDetails(String albumId);
        void saveAlbumTOFavorite();
    }
}
