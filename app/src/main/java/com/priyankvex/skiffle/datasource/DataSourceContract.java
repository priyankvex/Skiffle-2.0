package com.priyankvex.skiffle.datasource;

import com.priyankvex.skiffle.model.Album;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by @priyankvex on 25/3/17.
 */

public interface DataSourceContract {

    Map<String, String> getAuthHeader();

    void renewAuthToken(AuthRenewCallback callback);

    Observable<Long> saveAlbumToFavorite(Album album);

    Observable<ArrayList<Album>> loadFavoriteAlbums();

    Observable<Album> loadAlbumFromAlbumId(String albumId);

    Observable<Long> deleteAlbumFromFavorites(String albumId);

    interface AuthRenewCallback {
        void onAuthRenewed();
        void onAuthFailed();
    }
}
