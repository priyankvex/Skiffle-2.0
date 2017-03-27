package com.priyankvex.skiffle.util;

import android.util.Log;

import com.priyankvex.skiffle.SkiffleApp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by @priyankvex on 26/3/17.
 */

public class CachingControlInterceptor {

    public static class RewriteResponseInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");
            Log.d("owlcity", cacheControl + " cache header");
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age")) {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 10)
                        .build();
            } else {
                return originalResponse;
            }
        }
    }

    public static class OfflineInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();

            if (!ConnectionUtil.isNetworkAvailable(SkiffleApp.getInstance()
                    .getApplicationContext())) {
                Log.d("owlcity", "rewriting request");

                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }

            return chain.proceed(request);
        }
    }

}
