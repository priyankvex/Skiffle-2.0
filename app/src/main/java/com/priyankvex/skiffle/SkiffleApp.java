package com.priyankvex.skiffle;

import android.app.Application;

import com.priyankvex.skiffle.component.DaggerSkiffleApplicationComponent;
import com.priyankvex.skiffle.component.SkiffleApplicationComponent;
import com.priyankvex.skiffle.datasource.SpotifyAuthService;
import com.priyankvex.skiffle.module.ContextModule;

/**
 * Created by @priyankvex on 23/3/17.
 */

public class SkiffleApp extends Application{

    private static SkiffleApp mInstance;

    private SkiffleApplicationComponent mComponent;

    private SpotifyAuthService mSpotifyAuthService;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mComponent = DaggerSkiffleApplicationComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();
    }

    public static SkiffleApp getInstance() {
        return mInstance;
    }

    public SkiffleApplicationComponent getComponent() {
        return mComponent;
    }
}
