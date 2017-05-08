package com.priyankvex.skiffle.component;

import com.priyankvex.skiffle.module.RecommendationsModule;
import com.priyankvex.skiffle.scope.RecommendationsScope;
import com.priyankvex.skiffle.ui.recommendations.RecommendationsFragment;

import dagger.Component;

/**
 * Created by priyankvex on 8/5/17.
 */

@RecommendationsScope
@Component( modules = RecommendationsModule.class, dependencies = SkiffleApplicationComponent.class)
public interface RecommendationsComponent {

    void inject(RecommendationsFragment fragment);
}
