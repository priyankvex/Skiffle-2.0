package com.priyankvex.skiffle.component;

import com.priyankvex.skiffle.module.ShowAlbumDetailsModule;
import com.priyankvex.skiffle.scope.ShowAlbumDetailsScope;
import com.priyankvex.skiffle.ui.showalbumdetails.ShowAlbumDetailsActivity;

import dagger.Component;

/**
 * Created by @priyankvex on 6/4/17.
 */

@Component( modules = ShowAlbumDetailsModule.class, dependencies = SkiffleApplicationComponent.class)
@ShowAlbumDetailsScope
public interface ShowAlbumDetailsComponent {

    void inject(ShowAlbumDetailsActivity activity);
}
