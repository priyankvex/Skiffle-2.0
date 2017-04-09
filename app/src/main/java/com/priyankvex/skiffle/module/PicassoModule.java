package com.priyankvex.skiffle.module;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.priyankvex.skiffle.scope.SkiffleApplicationScope;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by @priyankvex on 24/3/17.
 */

@Module( includes = {NetworkModule.class} )
public class PicassoModule {

    @Provides
    @SkiffleApplicationScope
    Picasso getPicasso(Context context, OkHttp3Downloader downloader){
        return new Picasso.Builder(context)
                .downloader(downloader)
                .build();
    }

    @Provides
    @SkiffleApplicationScope
    OkHttp3Downloader getOkHttp3Downloader(OkHttpClient okHttpClient){
        return new OkHttp3Downloader(okHttpClient);
    }


}
