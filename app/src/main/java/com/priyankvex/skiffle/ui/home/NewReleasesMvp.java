package com.priyankvex.skiffle.ui.home;

import com.priyankvex.skiffle.model.AlbumItem;
import com.priyankvex.skiffle.model.NewReleases;

/**
 * Created by @priyankvex on 25/3/17.
 */

public interface NewReleasesMvp {

    interface NewReleasesView{
        void showErrorUi(String errorMessage);
        void showNewReleases(NewReleases newReleases);
        void newReleaseItemClicked(int position, AlbumItem item);
    }

    interface NewReleasesPresenter{
        void getNewReleases();
        void onStop();
    }

}
