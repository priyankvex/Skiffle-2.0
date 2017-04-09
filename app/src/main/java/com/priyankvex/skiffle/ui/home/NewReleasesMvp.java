package com.priyankvex.skiffle.ui.home;

import com.priyankvex.skiffle.model.NewRelease;

/**
 * Created by @priyankvex on 25/3/17.
 */

public interface NewReleasesMvp {

    interface NewReleasesView{
        void showErrorUi(String errorMessage);
        void showNewReleases(NewRelease newRelease);
        void newReleaseItemClicked(int position, NewRelease.Album.Item item);
    }

    interface NewReleasesPresenter{
        void getNewReleases();
    }

}
