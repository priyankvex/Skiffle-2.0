package com.priyankvex.skiffle.ui.showalbumdetails;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by @priyankvex on 28/3/17.
 */

public interface ShowAlbumDetailsMvp {

    interface ShowAlbumDetailsView{
        void showErrorUi();
        void showAlbumDetails(JsonObject album);
        void showTracks(ArrayList<String> tracks);
    }

    interface ShowAldumDetailsPresenter{
        void getAlbumDetails(String albumId);
    }
}
