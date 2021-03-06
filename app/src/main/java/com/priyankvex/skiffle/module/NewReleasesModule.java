package com.priyankvex.skiffle.module;

import com.priyankvex.skiffle.scope.NewReleasesScope;
import com.priyankvex.skiffle.ui.home.NewReleasesMvp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @priyankvex on 25/3/17.
 */

@Module
public class NewReleasesModule {

    private NewReleasesMvp.NewReleasesView mView;

    public NewReleasesModule(NewReleasesMvp.NewReleasesView view){
        this.mView = view;
    }

    @Provides
    @NewReleasesScope
    NewReleasesMvp.NewReleasesView getView(){
        return mView;
    }

}
