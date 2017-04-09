package com.priyankvex.skiffle.ui.showalbumdetails;

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

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.component.ShowAlbumDetailsComponent;
import com.priyankvex.skiffle.model.Album;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by @priyankvex on 28/3/17.
 */

public class ShowAlbumTracksFragment extends Fragment {

    private AlbumTracksCommunicator mCommunicator;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    TracksAdapter mTracksAdapter;

    static ShowAlbumTracksFragment getInstance(){
        return new ShowAlbumTracksFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ShowAlbumDetailsActivity){
            mCommunicator = (AlbumTracksCommunicator) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_album_tracks, container, false);
        ButterKnife.bind(this, rootView);
        mCommunicator.getComponent().inject(this);
        recyclerView.setAdapter(mTracksAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    void showErrorUi(){

    }

    void showTracks(Album album){
        progressBar.setVisibility(View.INVISIBLE);
        mTracksAdapter.swapData(album);
    }

    interface AlbumTracksCommunicator {
        ShowAlbumDetailsComponent getComponent();
    }
}
