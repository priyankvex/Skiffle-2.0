package com.priyankvex.skiffle.ui.showalbumdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.priyankvex.skiffle.R;

import java.util.ArrayList;

/**
 * Created by @priyankvex on 28/3/17.
 */

public class ShowAlbumTracksFragment extends Fragment {

    private AlbumTracksCommunicator mCommunicator;

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
        return inflater.inflate(R.layout.fragment_show_album_tracks, container, false);
    }

    void showErrorUi(){

    }

    void showTracks(ArrayList<String> tracks){

    }

    interface AlbumTracksCommunicator {

    }
}
