package com.priyankvex.skiffle.module;

import com.priyankvex.skiffle.scope.RecommendationsScope;
import com.priyankvex.skiffle.ui.recommendations.RecommendationsMvp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by priyankvex on 8/5/17.
 */

@Module
public class RecommendationsModule {

    private RecommendationsMvp.View mView;

    public RecommendationsModule(RecommendationsMvp.View view){
        this.mView = view;
    }

    @Provides
    @RecommendationsScope
    RecommendationsMvp.View getView(){
        return this.mView;
    }
}
