package com.priyankvex.skiffle.module;

import com.priyankvex.skiffle.scope.ShowAlbumDetailsScope;
import com.priyankvex.skiffle.ui.showalbumdetails.ShowAlbumDetailsMvp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @priyankvex on 6/4/17.
 */

@Module
public class ShowAlbumDetailsModule {

    private ShowAlbumDetailsMvp.ShowAlbumDetailsView mView;

    public ShowAlbumDetailsModule(ShowAlbumDetailsMvp.ShowAlbumDetailsView view){
        this.mView = view;
    }

    @Provides
    @ShowAlbumDetailsScope
    ShowAlbumDetailsMvp.ShowAlbumDetailsView getView(){
        return mView;
    }
}
