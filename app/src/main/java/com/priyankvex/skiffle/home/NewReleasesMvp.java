package com.priyankvex.skiffle.home;

/**
 * Created by @priyankvex on 25/3/17.
 */

public interface NewReleasesMvp {

    interface NewReleasesView{
        void showErrorUi(String errorMessage);
    }

    interface NewReleasesPresenter{
        void getPosts();
    }
}
