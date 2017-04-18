package com.priyankvex.skiffle.component;

import com.priyankvex.skiffle.module.SearchModule;
import com.priyankvex.skiffle.scope.SearchScope;
import com.priyankvex.skiffle.ui.search.ResultsPreviewFragment;
import com.priyankvex.skiffle.ui.search.SearchActivity;

import dagger.Component;

/**
 * Created by priyankvex on 13/4/17.
 */
@Component( modules = SearchModule.class, dependencies = SkiffleApplicationComponent.class)
@SearchScope
public interface SearchComponent {

    void inject(SearchActivity activity);
    void inject(ResultsPreviewFragment fragment);
}
