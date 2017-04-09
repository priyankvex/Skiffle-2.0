package com.priyankvex.skiffle.module;

import android.content.Context;

import com.google.gson.Gson;
import com.priyankvex.skiffle.datasource.DataSource;
import com.priyankvex.skiffle.datasource.DataSourceContract;
import com.priyankvex.skiffle.model.DaoMaster;
import com.priyankvex.skiffle.model.DaoSession;
import com.priyankvex.skiffle.scope.SkiffleApplicationScope;

import org.greenrobot.greendao.database.Database;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @priyankvex on 25/3/17.
 */

@Module( includes = {SpotifyServiceModule.class, ContextModule.class} )
public class DataSourceModule {

    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = false;

    @Provides
    @SkiffleApplicationScope
    DataSourceContract getDataSource(Gson gson, DaoSession daoSession){
        return DataSource.getDataSource(gson, daoSession);
    }

    @Provides
    @SkiffleApplicationScope
    DaoSession getDaoSession(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, ENCRYPTED ? "users-db-encrypted" : "users-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        return daoSession;
    }

}
