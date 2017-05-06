package com.priyankvex.skiffle.ui.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.component.FavoritesComponent;
import com.priyankvex.skiffle.model.AlbumItem;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankvex on 9/4/17.
 */

public class FavoriteAlbumsFragment extends Fragment{

    @Inject
    FavoriteAlbumsAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FavoriteAlbumsCommunicator mCommunicator;

    static FavoriteAlbumsFragment getInstance(){
        return new FavoriteAlbumsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_albums, container, false);
        ButterKnife.bind(this, rootView);
        mCommunicator.getComponent().inject(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    void setCommunicator(FavoriteAlbumsCommunicator communicator){
        this.mCommunicator = communicator;
    }

    void showEmptyUi(){

    }

    void showFavoriteAlbums(ArrayList<AlbumItem> albums){
        mAdapter.swapData(albums);
    }

    interface FavoriteAlbumsCommunicator{
        FavoritesComponent getComponent();
        void loadFavoriteAlbums();
    }
}
