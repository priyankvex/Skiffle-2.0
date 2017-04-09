package com.priyankvex.skiffle.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by priyankvex on 9/4/17.
 */
@Scope
@Retention(value = RetentionPolicy.CLASS)
public @interface FavoritesScope {
}
