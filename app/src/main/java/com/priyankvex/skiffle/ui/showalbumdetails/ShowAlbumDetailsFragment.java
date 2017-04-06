package com.priyankvex.skiffle.ui.showalbumdetails;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.priyankvex.skiffle.R;

/**
 * Created by @priyankvex on 28/3/17.
 */

public class ShowAlbumDetailsFragment extends Fragment{

    private AlbumDetailsCommunicator mCommunicator;

    static ShowAlbumDetailsFragment getInstance(){
        return new ShowAlbumDetailsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ShowAlbumDetailsActivity){
            mCommunicator = (AlbumDetailsCommunicator) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_album_details, container, false);
    }

    interface AlbumDetailsCommunicator {

    }
}
