package com.priyankvex.skiffle.datasource;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * Created by @priyankvex on 25/3/17.
 */

public interface SpotifyService {

    @GET("/posts/1")
    Observable<JsonObject> getPosts();
}
