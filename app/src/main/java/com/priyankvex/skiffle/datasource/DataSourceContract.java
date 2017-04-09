package com.priyankvex.skiffle.datasource;

import android.content.Context;

import com.priyankvex.skiffle.model.Album;
import com.priyankvex.skiffle.model.AuthToken;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by @priyankvex on 25/3/17.
 */

public interface DataSourceContract {

    Map<String, String> getAuthHeader();

    void renewAuthToken(AuthRenewCallback callback);

    Observable<Long> saveAlbumToFavorite(Album album);

    interface AuthRenewCallback {
        void onAuthRenewed();
        void onAuthFailed();
    }
}
