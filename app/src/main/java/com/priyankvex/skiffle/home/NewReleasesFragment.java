package com.priyankvex.skiffle.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.SkiffleApp;
import com.priyankvex.skiffle.component.DaggerNewReleasesComponent;
import com.priyankvex.skiffle.component.NewReleasesComponent;
import com.priyankvex.skiffle.module.NewReleasesModule;

import javax.inject.Inject;

/**
 * Created by @priyankvex on 25/3/17.
 */

public class NewReleasesFragment extends Fragment implements NewReleasesMvp.NewReleasesView{

    @Inject
    NewReleasesPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_releases, container, false);
        NewReleasesComponent component = DaggerNewReleasesComponent.builder()
                .newReleasesModule(new NewReleasesModule(this))
                .skiffleApplicationComponent(SkiffleApp.getInstance().getComponent())
                .build();
        component.inject(this);

        mPresenter.testMethod();

        mPresenter.getNewReleases();

        return rootView;
    }

    @Override
    public void showErrorUi(String errorMessage) {
        Log.d("owlcity", "New releases show error ui called");
    }

}
