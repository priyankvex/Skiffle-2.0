package com.priyankvex.skiffle.module;

import com.priyankvex.skiffle.scope.FavoritesScope;
import com.priyankvex.skiffle.ui.favorites.FavoritesMvp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by priyankvex on 9/4/17.
 */
@Module
public class FavoritesModule {

    private FavoritesMvp.FavoritesView mView;

    public FavoritesModule(FavoritesMvp.FavoritesView view){
        this.mView = view;
    }

    @Provides
    @FavoritesScope
    FavoritesMvp.FavoritesView getView(){
        return mView;
    }
}
