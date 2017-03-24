package com.priyankvex.skiffle;

import android.app.Application;
import android.util.Log;

import com.priyankvex.skiffle.component.DaggerSkiffleApplicationComponent;
import com.priyankvex.skiffle.component.SkiffleApplicationComponent;
import com.priyankvex.skiffle.module.ContextModule;
import com.squareup.picasso.Picasso;

/**
 * Created by @priyankvex on 23/3/17.
 */

public class SkiffleApp extends Application{

    Picasso mPicasso;

    private SkiffleApp mInstance;

    private SkiffleApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mComponent = DaggerSkiffleApplicationComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();
        mPicasso = mComponent.getPicasso();

        Log.d(getClass().getName(), mPicasso.toString());
    }

    public Picasso getPicasso(){
        return mPicasso;
    }
}
