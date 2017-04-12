package com.priyankvex.skiffle.ui.favorites;

import com.priyankvex.skiffle.model.AlbumItem;

import java.util.ArrayList;

/**
 * Created by priyankvex on 9/4/17.
 */

public interface FavoritesMvp {

    interface FavoritesView{
        void showFavoriteAlbums(ArrayList<AlbumItem> albums);
        void showFavoriteTracks();
        void showEmptyAlbumsUi();
        void onFavoriteAlbumItemClicked(int position, AlbumItem album);
    }

    interface FavoritesPresenter{
        void loadFavoriteTracks();
        void loadFavoriteAlbums();
    }
}
