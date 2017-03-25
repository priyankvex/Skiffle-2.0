package com.priyankvex.skiffle.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by @priyankvex on 25/3/17.
 */

public class ActivityUtil {

    public static void replaceFragmentInContainer(Fragment fragment, int containerId){
        FragmentManager fragmentManager = fragment.getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(containerId, fragment).commit();
    }
}
