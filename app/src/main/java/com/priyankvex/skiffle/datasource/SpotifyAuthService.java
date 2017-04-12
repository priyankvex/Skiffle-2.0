package com.priyankvex.skiffle.datasource;

import com.priyankvex.skiffle.model.AuthToken;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by @priyankvex on 26/3/17.
 */

public interface SpotifyAuthService {

    @FormUrlEncoded
    @POST("api/token")
    Observable<AuthToken> getAuthToken(
            @Field("grant_type") String grantType,
            @HeaderMap Map<String, String> headers
    );
}
