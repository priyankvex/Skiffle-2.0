package com.priyankvex.skiffle.component;

import com.priyankvex.skiffle.ui.home.NewReleasesFragment;
import com.priyankvex.skiffle.module.NewReleasesModule;
import com.priyankvex.skiffle.scope.NewReleasesScope;

import dagger.Component;

/**
 * Created by @priyankvex on 25/3/17.
 */

@Component( modules = NewReleasesModule.class, dependencies = SkiffleApplicationComponent.class)
@NewReleasesScope
public interface NewReleasesComponent {

    void inject(NewReleasesFragment fragment);

}
