package com.priyankvex.skiffle.datasource;

import android.content.Context;

import com.priyankvex.skiffle.model.AuthToken;

import java.util.Map;

/**
 * Created by @priyankvex on 25/3/17.
 */

public interface DataSourceContract {

    Map<String, String> getAuthHeader();

    void renewAuthToken(AuthRenewCallback callback);

    interface AuthRenewCallback {
        void onAuthRenewed();
        void onAuthFailed();
    }
}
