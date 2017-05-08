package com.priyankvex.skiffle.datasource;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.SkiffleApp;
import com.priyankvex.skiffle.model.AlbumDetails;
import com.priyankvex.skiffle.model.AlbumEntity;
import com.priyankvex.skiffle.model.AlbumEntityDao;
import com.priyankvex.skiffle.model.AlbumItem;
import com.priyankvex.skiffle.model.AuthToken;
import com.priyankvex.skiffle.model.DaoSession;
import com.priyankvex.skiffle.model.TrackDetails;
import com.priyankvex.skiffle.model.TrackEntity;
import com.priyankvex.skiffle.model.TrackEntityDao;
import com.priyankvex.skiffle.model.TrackItem;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by @priyankvex on 25/3/17.
 */

public class DataSource implements DataSourceContract{

    private Gson mGson;

    private DaoSession mDaoSession;

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

    public static DataSourceContract getDataSource(Gson gson, DaoSession daoSession){
        Holder.INSTANCE.mGson = gson;
        Holder.INSTANCE.mDaoSession = daoSession;
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

    @Override
    public Observable<Long> saveAlbumToFavorite(final AlbumDetails album) {

        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                Log.d("owlcity", "Saving " + album.name + " to the database");
                long count = mDaoSession.getAlbumEntityDao()
                        .queryBuilder()
                        .where(AlbumEntityDao.Properties.AlbumId.eq(album.id))
                        .count();
                if (count != 0){
                    // already saved in favorites
                    Log.d("owlcity", "Album with name " + album.name + " already present in favorites");
                    return (long)-1;
                }
                AlbumEntity entity = new AlbumEntity();
                entity.setAlbumId(album.id);
                entity.setAlbumJsonData(mGson.toJson(album));
                mDaoSession.getAlbumEntityDao().insert(entity);
                return entity.getId();
            }
        });

    }

    @Override
    public Observable<Long> saveTrackToFavorites(final TrackDetails track) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long count = mDaoSession.getTrackEntityDao()
                        .queryBuilder()
                        .where(TrackEntityDao.Properties.TrackId.eq(track.id))
                        .count();
                if (count != 0){
                    Log.d("owlcity", "Track with name " + track.name + " already present in favorites");
                    return (long)-1;
                }
                TrackEntity entity = new TrackEntity();
                entity.setTrackId(track.id);
                entity.setTrackJsonData(mGson.toJson(track));
                mDaoSession.getTrackEntityDao().insert(entity);
                return entity.getId();
            }
        });
    }

    @Override
    public Observable<Long> deleteTrackFromFavorites(final String trackId) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                mDaoSession.getTrackEntityDao().queryBuilder()
                        .where(TrackEntityDao.Properties.TrackId.eq(trackId))
                        .buildDelete()
                        .executeDeleteWithoutDetachingEntities();
                return (long)1;
            }
        });
    }

    @Override
    public Observable<ArrayList<AlbumItem>> loadFavoriteAlbums() {
        return Observable.fromCallable(new Callable<ArrayList<AlbumItem>>() {
            @Override
            public ArrayList<AlbumItem> call() throws Exception {
                List<AlbumEntity> albumEntities = mDaoSession.getAlbumEntityDao().loadAll();
                ArrayList<AlbumItem> albums = new ArrayList<>(5);
                for (AlbumEntity entity : albumEntities){
                    AlbumDetails album = mGson.fromJson(entity.getAlbumJsonData(), AlbumDetails.class);
                    // drop the tracks to trim the size of the object
                    AlbumItem item = new AlbumItem();
                    item.name = album.name;
                    item.id = album.id;
                    item.albumType = album.albumType;
                    item.artists = album.artists;
                    item.type = album.type;
                    item.images = album.images;
                    albums.add(item);
                }
                return albums;
            }
        });
    }

    @Override
    public Observable<ArrayList<TrackItem>> loadFavoriteTracks() {
        return Observable.fromCallable(new Callable<ArrayList<TrackItem>>() {
            @Override
            public ArrayList<TrackItem> call() throws Exception {
                List<TrackEntity> trackEntities = mDaoSession.getTrackEntityDao().loadAll();
                ArrayList<TrackItem> tracks= new ArrayList<>(5);
                for (TrackEntity entity : trackEntities){
                    TrackDetails track = mGson.fromJson(entity.getTrackJsonData(), TrackDetails.class);
                    // drop the tracks to trim the size of the object
                    TrackItem item = new TrackItem();
                    item.name = track.name;
                    item.id = track.id;
                    item.type = track.type;
                    item.artists = track.artists;
                    item.album = track.album;
                    tracks.add(item);
                }
                return tracks;
            }
        });
    }

    @Override
    public Observable<Long> deleteAlbumFromFavorites(final String albumId) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                mDaoSession.getAlbumEntityDao().queryBuilder()
                        .where(AlbumEntityDao.Properties.AlbumId.eq(albumId))
                        .buildDelete()
                        .executeDeleteWithoutDetachingEntities();
                return (long)1;
            }
        });
    }

    @Override
    public Observable<AlbumDetails> loadAlbumFromAlbumId(final String albumId) {
        return Observable.fromCallable(new Callable<AlbumDetails>() {
            @Override
            public AlbumDetails call() throws Exception {
                List<AlbumEntity> albums = mDaoSession.getAlbumEntityDao()
                        .queryBuilder().where(AlbumEntityDao.Properties.AlbumId.eq(albumId))
                        .limit(1)
                        .build()
                        .list();
                if (albums == null || albums.size() == 0){
                    // return a pseudo element representing that no element was found
                    // this has been done because RxJava 2.xx doesn't allow null as stream element
                    AlbumDetails pseudoAlbum = new AlbumDetails();
                    pseudoAlbum.id = "album_not_found";
                    return pseudoAlbum;
                }
                else{
                    return mGson.fromJson(albums.get(0).getAlbumJsonData(), AlbumDetails.class);
                }
            }
        });
    }

    @Override
    public Observable<TrackDetails> loadTrackDetailsFromTrackId(final String trackId) {
        return Observable.fromCallable(new Callable<TrackDetails>() {
            @Override
            public TrackDetails call() throws Exception {
                List<TrackEntity> tracks = mDaoSession.getTrackEntityDao()
                        .queryBuilder().where(TrackEntityDao.Properties.TrackId.eq(trackId))
                        .limit(1)
                        .build()
                        .list();
                if (tracks == null || tracks.size() == 0){
                    // return a pseudo element representing that no element was found
                    // this has been done because RxJava 2.xx doesn't allow null as stream element
                    TrackDetails pseudoTrack = new TrackDetails();
                    pseudoTrack.id = "track_not_found";
                    return pseudoTrack;
                }
                else{
                    return mGson.fromJson(tracks.get(0).getTrackJsonData(), TrackDetails.class);
                }
            }
        });
    }

    @Override
    public Observable<Boolean> isTrackSaved(final String trackId) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                List<TrackEntity> tracks = mDaoSession.getTrackEntityDao()
                        .queryBuilder().where(TrackEntityDao.Properties.TrackId.eq(trackId))
                        .limit(1)
                        .build()
                        .list();
                if (tracks == null || tracks.size() == 0){
                    return Boolean.FALSE;
                }
                else{
                    return Boolean.TRUE;
                }
            }
        });
    }

    @Override
    public Observable<Map<String, String>> getRecommendationsSeeds() {
        return Observable.fromCallable(new Callable<Map<String, String>>() {
            @Override
            public Map<String, String> call() throws Exception {
                // read the favorite songs
                StringBuilder trackSeeds = new StringBuilder();
                List<TrackEntity> tracks = mDaoSession.getTrackEntityDao()
                        .loadAll();
                for (TrackEntity trackEntity : tracks){
                    trackSeeds.append(trackEntity.getTrackId());
                    trackSeeds.append(",");
                }

                StringBuilder artistSeeds = new StringBuilder();
                List<AlbumEntity> albums = mDaoSession.getAlbumEntityDao()
                        .loadAll();
                AlbumDetails tempAlbumDetails;
                for (AlbumEntity entity : albums){
                    tempAlbumDetails = mGson.fromJson(entity.getAlbumJsonData(), AlbumDetails.class);
                    artistSeeds.append(tempAlbumDetails.artists.get(0).id);
                    artistSeeds.append(",");
                }

                Map<String, String> map = new HashMap<>();
                map.put("track_seeds", trackSeeds.toString());
                map.put("artist_seeds", artistSeeds.toString());
                return map;
            }
        });
    }
}
