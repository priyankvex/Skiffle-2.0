package com.priyankvex.skiffle.module;

import com.google.gson.Gson;
import com.priyankvex.skiffle.datasource.SpotifyAuthService;
import com.priyankvex.skiffle.datasource.SpotifyService;
import com.priyankvex.skiffle.scope.SkiffleApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by @priyankvex on 25/3/17.
 */

@Module(includes = NetworkModule.class)
public class SpotifyServiceModule {

    private final String BASE_URL = "https://api.spotify.com/v1/";
    private final String AUTH_BASE_URL = "https://accounts.spotify.com/";

    @Provides
    @SkiffleApplicationScope
    public SpotifyService spotifyService(@Named("SpotifyApi") Retrofit retrofit) {
        return retrofit.create(SpotifyService.class);
    }

    @Provides
    @SkiffleApplicationScope
    public SpotifyAuthService spotifyAuthService(@Named("SpotifyAccountsApi") Retrofit retrofit){
        return retrofit.create(SpotifyAuthService.class);
    }

    @Provides
    @SkiffleApplicationScope
    public Gson gson() {
        return new Gson();
    }

    @Provides
    @SkiffleApplicationScope
    @Named("SpotifyApi")
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build();
    }

    @Provides
    @SkiffleApplicationScope
    @Named("SpotifyAccountsApi")
    public Retrofit retrofitAuth(OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(AUTH_BASE_URL)
                .build();
    }
}
