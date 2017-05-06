package com.priyankvex.skiffle.ui.favorites;

import com.priyankvex.skiffle.model.AlbumItem;
import com.priyankvex.skiffle.model.TrackItem;

import java.util.ArrayList;

/**
 * Created by priyankvex on 9/4/17.
 */

public interface FavoritesMvp {

    interface FavoritesView{
        void showFavoriteAlbums(ArrayList<AlbumItem> albums);
        void showFavoriteTracks(ArrayList<TrackItem> tracks);
        void showEmptyAlbumsUi();
        void showEmptyTracksUi();
        void onFavoriteAlbumItemClicked(int position, AlbumItem album);
        void onFavoriteTrackItemClicked(int position, TrackItem track);
    }

    interface FavoritesPresenter{
        void loadFavoriteTracks();
        void loadFavoriteAlbums();
    }
}
