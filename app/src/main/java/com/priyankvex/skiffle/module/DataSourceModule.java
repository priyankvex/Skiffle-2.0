package com.priyankvex.skiffle.module;

import com.google.gson.Gson;
import com.priyankvex.skiffle.datasource.DataSource;
import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.scope.SkiffleApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @priyankvex on 25/3/17.
 */

@Module( includes = SpotifyServiceModule.class)
public class DataSourceModule {

    @Provides
    @SkiffleApplicationScope
    DataSourceContract getDataSource(Gson gson){
        return DataSource.getDataSource(gson);
    }

}
