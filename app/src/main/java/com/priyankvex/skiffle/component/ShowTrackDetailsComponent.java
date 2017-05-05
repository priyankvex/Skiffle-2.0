package com.priyankvex.skiffle.component;

import com.priyankvex.skiffle.module.ShowTrackDetailsModule;
import com.priyankvex.skiffle.scope.ShowTrackDetailsScope;
import com.priyankvex.skiffle.ui.showtrackdetails.ShowTrackDetailsActivity;

import dagger.Component;

/**
 * Created by priyankvex on 4/5/17.
 */

@Component (modules = ShowTrackDetailsModule.class, dependencies = SkiffleApplicationComponent.class)
@ShowTrackDetailsScope
public interface ShowTrackDetailsComponent {

    void inject(ShowTrackDetailsActivity activity);
}
