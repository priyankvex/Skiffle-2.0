package com.priyankvex.skiffle.datasource;

import com.priyankvex.skiffle.model.AlbumDetails;
import com.priyankvex.skiffle.model.AlbumItem;
import com.priyankvex.skiffle.model.TrackDetails;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by @priyankvex on 25/3/17.
 */

public interface DataSourceContract {

    Map<String, String> getAuthHeader();

    void renewAuthToken(AuthRenewCallback callback);

    Observable<Long> saveAlbumToFavorite(AlbumDetails album);

    Observable<Long> saveTrackToFavorites(TrackDetails track);

    Observable<Long> deleteTrackFromFavorites(String trackId);

    Observable<ArrayList<AlbumItem>> loadFavoriteAlbums();

    Observable<AlbumDetails> loadAlbumFromAlbumId(String albumId);

    Observable<TrackDetails> loadTrackDetailsFromTrackId(String trackId);

    Observable<Boolean> isTrackSaved(String trackId);

    Observable<Long> deleteAlbumFromFavorites(String albumId);

    interface AuthRenewCallback {
        void onAuthRenewed();
        void onAuthFailed();
    }
}
