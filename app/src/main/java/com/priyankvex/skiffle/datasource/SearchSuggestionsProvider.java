package com.priyankvex.skiffle.datasource;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by priyankvex on 10/4/17.
 */

public class SearchSuggestionsProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "com.priyankvex.skiffle.datasource.SearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }


}
