package com.priyankvex.skiffle.module;

import android.content.Context;

import com.priyankvex.skiffle.scope.SkiffleApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @SkiffleApplicationScope
    public Context context() {
        return context;
    }
}
