package com.priyankvex.skiffle.datasource;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.SkiffleApp;
import com.priyankvex.skiffle.model.AuthToken;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by @priyankvex on 25/3/17.
 */

public class DataSource implements DataSourceContract{

    private Gson mGson;

    private final String KEY_AUTH_TOKEN = "auth+token";

    /**
     * Prevent direct instantiation
     */
    private DataSource(){
    }

    /**
     * Initialization on demand pattern to make the
     * instance creation thread safe as class initializations are serial in nature.
     * This method is more efficient than using synchronisation block.
     */
    private static class Holder {
        static final DataSource INSTANCE = new DataSource();
    }

    public static DataSourceContract getDataSource(Gson gson){
        Holder.INSTANCE.mGson = gson;
        return Holder.INSTANCE;
    }

    /**
     * Returns auth header consisting of auth token that is required for every
     * request to API
     * @return map containing the required headers
     */
    @Override
    public Map<String, String> getAuthHeader() {
        Map<String, String> headers = new HashMap<>();
        String authToken = getAuthToken();
        String authValue = "Bearer " + authToken;
        headers.put("Authorization", authValue);
        return headers;
    }

    /**
     * Renews auth token and saves it to shared preferences
     * @param callback callback to presenter to notify about renewal status
     */
    @Override
    public void renewAuthToken(final AuthRenewCallback callback) {
        String grantType = "client_credentials";
        Map<String, String> header = new HashMap<>();
        String clientId = SkiffleApp.getInstance()
                .getApplicationContext().getResources().getString(R.string.client_id);
        String clientSecret = SkiffleApp.getInstance()
                .getApplicationContext().getResources().getString(R.string.client_secret);
        String temp = clientId + ":" + clientSecret;
        String clientHeader = Base64.encodeToString(temp.getBytes(), Base64.NO_WRAP);
        final String authHeader = "Basic " + clientHeader;
        header.put("Authorization", authHeader);
        SkiffleApp.getInstance().getComponent().getSpotifyAuthService()
                .getAuthToken(grantType, header)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AuthToken>() {
                    @Override
                    public void onNext(AuthToken authToken) {
                        // got the renewed auth token
                        Log.d(getClass().getName(), "Renewed thw auth token");
                        Log.d(getClass().getName(), authToken.toString());
                        saveAuthToken(authToken);
                        callback.onAuthRenewed();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(getClass().getName(), e.getLocalizedMessage());
                        callback.onAuthFailed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * Saves the values present in auth token to shared preferences
     * @param authToken object that contains auth token
     */
    private void saveAuthToken(AuthToken authToken) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(SkiffleApp.getInstance().getApplicationContext());
        prefs.edit().putString(KEY_AUTH_TOKEN, authToken.getAccessToken()).apply();
    }

    /**
     * Reads the auth token from the shared preferences ans returns it
     * @return auth token as string
     */
    private String getAuthToken(){
        return PreferenceManager
                .getDefaultSharedPreferences(SkiffleApp.getInstance().getApplicationContext())
                .getString(KEY_AUTH_TOKEN, null);
    }
}
