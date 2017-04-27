package com.priyankvex.skiffle.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by @priyankvex on 25/3/17.
 */

public class ActivityUtil {

    public static void replaceFragmentInContainer(FragmentManager fragmentManager
            , Fragment fragment, int containerId, String tag){
        fragmentManager.beginTransaction().replace(containerId, fragment, tag).commit();
    }
}
