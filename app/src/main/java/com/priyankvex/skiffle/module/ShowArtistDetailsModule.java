package com.priyankvex.skiffle.module;

import com.priyankvex.skiffle.scope.ShowArtistDetailsScope;
import com.priyankvex.skiffle.ui.showartistdetails.ShowArtistDetailsMvp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by priyankvex on 3/5/17.
 */
@Module
public class ShowArtistDetailsModule {

    private ShowArtistDetailsMvp.ShowArtistDetailsView mView;

    public ShowArtistDetailsModule(ShowArtistDetailsMvp.ShowArtistDetailsView view){
        this.mView = view;
    }

    @Provides
    @ShowArtistDetailsScope
    ShowArtistDetailsMvp.ShowArtistDetailsView getView(){
        return this.mView;
    }
}
