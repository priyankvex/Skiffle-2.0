package com.priyankvex.skiffle.ui.favorites;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.component.FavoritesComponent;
import com.priyankvex.skiffle.model.TrackItem;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankvex on 9/4/17.
 */

public class FavoriteTracksFragment extends Fragment {

    static FavoriteTracksFragment getInstance(){
        return new FavoriteTracksFragment();
    }

    @Inject
    FavoriteTracksAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;

    FavoriteTracksCommunicator mCommunicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_tracks, container, false);
        ButterKnife.bind(this, rootView);
        mCommunicator.getComponent().inject(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCommunicator.loadFavoriteTracks();
    }

    void showTracks(ArrayList<TrackItem> tracks){
        mAdapter.swapData(tracks);
    }

    void showEmptyUi(){
        recyclerView.setVisibility(View.INVISIBLE);
        emptyLayout.setVisibility(View.VISIBLE);
    }

    void setCommunicator(FavoriteTracksCommunicator communicator){
        this.mCommunicator = communicator;
    }

    interface FavoriteTracksCommunicator{
        void loadFavoriteTracks();
        FavoritesComponent getComponent();
    }
}
