package com.priyankvex.skiffle.ui.search;

import com.priyankvex.skiffle.model.SearchResultsListItem;

import java.util.ArrayList;

/**
 * Created by priyankvex on 13/4/17.
 */

public interface SearchMvp {

    interface SearchView{
        void showSearchResults(ArrayList<SearchResultsListItem> results);
        void onSearchResultsPreviewItemClicked(SearchResultsListItem item, int position);
    }

    interface SearchPresenter{
        void getSearchResults(String query);
    }
}
