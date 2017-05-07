package com.priyankvex.skiffle.ui.showartistdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.component.ShowArtistDetailsComponent;
import com.priyankvex.skiffle.model.TrackItem;
import com.priyankvex.skiffle.model.TrackList;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankvex on 3/5/17.
 */

public class ShowArtistTracksFragment extends Fragment{

    private ShowArtistsTracksCommunicator mCommunicator;

    @Inject
    ArtistTracksAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.error_layout)
    RelativeLayout errorLayout;

    public static ShowArtistTracksFragment getInstance(){
        return new ShowArtistTracksFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ShowArtistDetailsActivity){
            mCommunicator = (ShowArtistsTracksCommunicator) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_tracks, container, false);
        ButterKnife.bind(this, rootView);
        mCommunicator.getComponent().inject(this);
        mCommunicator.getArtistsTopTracks();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }

    public void showArtistTopTracks(ArrayList<TrackItem> trackList){
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        mAdapter.swapData(trackList);
    }

    public void showErrorUi(){
        progressBar.setVisibility(View.INVISIBLE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    interface ShowArtistsTracksCommunicator{
        ShowArtistDetailsComponent getComponent();
        void getArtistsTopTracks();
    }
}
