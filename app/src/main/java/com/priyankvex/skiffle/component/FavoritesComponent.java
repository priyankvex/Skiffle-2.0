package com.priyankvex.skiffle.component;

import com.priyankvex.skiffle.module.FavoritesModule;
import com.priyankvex.skiffle.scope.FavoritesScope;
import com.priyankvex.skiffle.ui.favorites.FavoriteAlbumsFragment;
import com.priyankvex.skiffle.ui.favorites.FavoriteTracksFragment;
import com.priyankvex.skiffle.ui.favorites.FavoritesFragment;

import dagger.Component;

/**
 * Created by priyankvex on 9/4/17.
 */
@Component( modules = FavoritesModule.class, dependencies = SkiffleApplicationComponent.class)
@FavoritesScope
public interface FavoritesComponent {
    void inject(FavoritesFragment fragment);
    void inject(FavoriteAlbumsFragment fragment);
    void inject(FavoriteTracksFragment fragment);
}
