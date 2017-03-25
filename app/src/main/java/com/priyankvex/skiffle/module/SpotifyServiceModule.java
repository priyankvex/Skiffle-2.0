package com.priyankvex.skiffle.module;

import com.google.gson.Gson;
import com.priyankvex.skiffle.datasource.SpotifyService;
import com.priyankvex.skiffle.scope.SkiffleApplicationScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by @priyankvex on 25/3/17.
 */

@Module(includes = NetworkModule.class)
public class SpotifyServiceModule {

    private final String BASE_URL = "";
    @Provides
    @SkiffleApplicationScope
    public SpotifyService gyanService(Retrofit gyanRetrofit) {
        return gyanRetrofit.create(SpotifyService.class);
    }

    @Provides
    @SkiffleApplicationScope
    public Gson gson() {
        return new Gson();
    }

    @Provides
    @SkiffleApplicationScope
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build();
    }
}
