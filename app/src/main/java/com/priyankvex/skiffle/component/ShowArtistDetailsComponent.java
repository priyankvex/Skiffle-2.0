package com.priyankvex.skiffle.component;

import com.priyankvex.skiffle.module.ShowAlbumDetailsModule;
import com.priyankvex.skiffle.module.ShowArtistDetailsModule;
import com.priyankvex.skiffle.scope.ShowArtistDetailsScope;
import com.priyankvex.skiffle.ui.showartistdetails.ShowArtistDetailsActivity;
import com.priyankvex.skiffle.ui.showartistdetails.ShowArtistDetailsFragment;
import com.priyankvex.skiffle.ui.showartistdetails.ShowArtistTracksFragment;

import dagger.Component;

/**
 * Created by priyankvex on 3/5/17.
 */

@Component(modules = ShowArtistDetailsModule.class, dependencies = SkiffleApplicationComponent.class)
@ShowArtistDetailsScope
public interface ShowArtistDetailsComponent {

    void inject(ShowArtistDetailsActivity activity);
    void inject(ShowArtistTracksFragment fragment);
}
