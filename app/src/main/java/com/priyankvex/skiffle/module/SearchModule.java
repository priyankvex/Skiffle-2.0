package com.priyankvex.skiffle.module;

import com.priyankvex.skiffle.scope.SearchScope;
import com.priyankvex.skiffle.ui.search.SearchMvp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by priyankvex on 13/4/17.
 */
@Module
public class SearchModule {

    private SearchMvp.SearchView mView;

    public SearchModule(SearchMvp.SearchView view){
        this.mView = view;
    }

    @Provides
    @SearchScope
    SearchMvp.SearchView getView(){
        return mView;
    }
}
