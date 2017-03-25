package com.priyankvex.skiffle.datasource;

import com.google.gson.Gson;

/**
 * Created by @priyankvex on 25/3/17.
 */

public class DataSource implements DataSourceContract{

    private Gson mGson;

    /**
     * Prevent direct instantiation
     */
    private DataSource(){
    }

    /**
     * Initialization on demand pattern to make the
     * instance creation thread safe as class initializations are serial in nature.
     * This method is more efficient than using synchronisation block.
     */
    private static class Holder {
        static final DataSource INSTANCE = new DataSource();
    }

    public static DataSourceContract getDataSource(Gson gson){
        Holder.INSTANCE.mGson = gson;
        return Holder.INSTANCE;
    }
}
