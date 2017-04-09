package com.priyankvex.skiffle.ui.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.priyankvex.skiffle.R;

/**
 * Created by priyankvex on 9/4/17.
 */

public class FavoriteTracksFragment extends Fragment {

    static FavoriteTracksFragment getInstance(){
        return new FavoriteTracksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_tracks, container, false);
        return rootView;
    }
}
