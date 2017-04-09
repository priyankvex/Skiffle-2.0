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

    interface AuthRenewCallback {
        void onAuthRenewed();
        void onAuthFailed();
    }
}
