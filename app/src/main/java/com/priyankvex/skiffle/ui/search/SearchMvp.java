package com.priyankvex.skiffle.ui.search;

/**
 * Created by priyankvex on 13/4/17.
 */

public interface SearchMvp {

    interface SearchView{

    }

    interface SearchPresenter{
        void getSearchResults(String query);
    }
}
