package com.priyankvex.skiffle.home;

import com.priyankvex.skiffle.model.NewRelease;

/**
 * Created by @priyankvex on 25/3/17.
 */

public interface NewReleasesMvp {

    interface NewReleasesView{
        void showErrorUi(String errorMessage);
        void showNewReleases(NewRelease newRelease);
    }

    interface NewReleasesPresenter{
        void getNewReleases();
    }
}
