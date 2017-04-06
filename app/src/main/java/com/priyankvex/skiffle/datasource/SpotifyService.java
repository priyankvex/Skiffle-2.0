package com.priyankvex.skiffle.datasource;

import com.google.gson.JsonObject;
import com.priyankvex.skiffle.model.NewRelease;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by @priyankvex on 25/3/17.
 */

public interface SpotifyService {

    @GET("browse/new-releases")
    Observable<NewRelease> getNewReleases(
            @HeaderMap Map<String, String> headers
    );

    @GET("albums/{id}")
    Observable<JsonObject> getAlbum(
            @Path("id") String id,
            @HeaderMap Map<String, String> headers
    );

}
