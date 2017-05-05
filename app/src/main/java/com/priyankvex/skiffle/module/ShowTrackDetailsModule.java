package com.priyankvex.skiffle.module;

import com.priyankvex.skiffle.scope.ShowTrackDetailsScope;
import com.priyankvex.skiffle.ui.showtrackdetails.ShowTrackDetailsMvp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by priyankvex on 4/5/17.
 */

@Module
public class ShowTrackDetailsModule {

    private ShowTrackDetailsMvp.ShowTrackDetailsView mView;

    public ShowTrackDetailsModule(ShowTrackDetailsMvp.ShowTrackDetailsView view){
        this.mView = view;
    }

    @Provides
    @ShowTrackDetailsScope
    ShowTrackDetailsMvp.ShowTrackDetailsView getView(){
        return mView;
    }
}
