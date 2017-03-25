package com.priyankvex.skiffle.component;

import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.datasource.SpotifyService;
import com.priyankvex.skiffle.module.DataSourceModule;
import com.priyankvex.skiffle.module.PicassoModule;
import com.priyankvex.skiffle.scope.SkiffleApplicationScope;
import com.squareup.picasso.Picasso;

import dagger.Component;

/**
 * Created by @priyankvex on 24/3/17.
 */

@Component( modules = {PicassoModule.class, DataSourceModule.class} )
@SkiffleApplicationScope
public interface SkiffleApplicationComponent {

    Picasso getPicasso();

    DataSourceContract getDataSource();

    SpotifyService getSpotifyService();

}
