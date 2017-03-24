package com.priyankvex.skiffle.component;

import com.priyankvex.skiffle.module.PicassoModule;
import com.priyankvex.skiffle.scope.SkiffleApplicationScope;
import com.squareup.picasso.Picasso;

import dagger.Component;

/**
 * Created by @priyankvex on 24/3/17.
 */

@Component( modules = {PicassoModule.class} )
@SkiffleApplicationScope
public interface SkiffleApplicationComponent {

    Picasso getPicasso();

}
