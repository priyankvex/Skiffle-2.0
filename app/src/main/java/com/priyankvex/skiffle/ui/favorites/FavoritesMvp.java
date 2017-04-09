package com.priyankvex.skiffle.ui.favorites;

import com.priyankvex.skiffle.model.Album;

import java.util.ArrayList;

/**
 * Created by priyankvex on 9/4/17.
 */

public interface FavoritesMvp {

    interface FavoritesView{
        void showFavoriteAlbums(ArrayList<Album> albums);
        void showFavoriteTracks();
        void showEmptyAlbumsUi();
        void onFavoriteAlbumItemClicked(int position, Album album);
    }

    interface FavoritesPresenter{
        void loadFavoriteTracks();
        void loadFavoriteAlbums();
    }
}
