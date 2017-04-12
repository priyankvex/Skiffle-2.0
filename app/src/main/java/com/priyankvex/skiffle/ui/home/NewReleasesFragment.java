package com.priyankvex.skiffle.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.SkiffleApp;
import com.priyankvex.skiffle.component.DaggerNewReleasesComponent;
import com.priyankvex.skiffle.component.NewReleasesComponent;
import com.priyankvex.skiffle.model.AlbumItem;
import com.priyankvex.skiffle.model.NewReleases;
import com.priyankvex.skiffle.module.NewReleasesModule;
import com.priyankvex.skiffle.ui.showalbumdetails.ShowAlbumDetailsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by @priyankvex on 25/3/17.
 */

public class NewReleasesFragment extends Fragment implements NewReleasesMvp.NewReleasesView {

    @Inject
    NewReleasesPresenter mPresenter;

    @Inject
    NewReleasesAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_releases, container, false);
        ButterKnife.bind(this, rootView);

        NewReleasesComponent component = DaggerNewReleasesComponent.builder()
                .newReleasesModule(new NewReleasesModule(this))
                .skiffleApplicationComponent(SkiffleApp.getInstance().getComponent())
                .build();
        component.inject(this);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter.getNewReleases();

        return rootView;
    }

    @Override
    public void showErrorUi(String errorMessage) {
        Log.d("owlcity", "New releases show error ui called");
    }

    @Override
    public void showNewReleases(NewReleases newRelease) {
        mAdapter.swapData(newRelease.albums.items);
    }

    @Override
    public void newReleaseItemClicked(int position, AlbumItem item) {
        Log.d("owlcity", item.name + " clicked");
        Intent i = new Intent(getActivity(), ShowAlbumDetailsActivity.class);
        i.putExtra("album_id", item.id);
        i.putExtra("album_title", item.name);
        startActivity(i);
    }
}
