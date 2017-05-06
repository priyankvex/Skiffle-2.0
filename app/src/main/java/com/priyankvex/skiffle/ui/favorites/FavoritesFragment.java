package com.priyankvex.skiffle.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.SkiffleApp;
import com.priyankvex.skiffle.component.DaggerFavoritesComponent;
import com.priyankvex.skiffle.component.FavoritesComponent;
import com.priyankvex.skiffle.model.AlbumItem;
import com.priyankvex.skiffle.model.TrackItem;
import com.priyankvex.skiffle.module.FavoritesModule;
import com.priyankvex.skiffle.ui.showalbumdetails.ShowAlbumDetailsActivity;
import com.priyankvex.skiffle.ui.showtrackdetails.ShowTrackDetailsActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankvex on 9/4/17.
 */

public class FavoritesFragment extends Fragment implements FavoritesMvp.FavoritesView,
        FavoriteAlbumsFragment.FavoriteAlbumsCommunicator,
        FavoriteTracksFragment.FavoriteTracksCommunicator {

    @Inject
    FavoritesPresenter mPresenter;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private FavoriteAlbumsFragment mFavoriteAlbumsFragment;

    private FavoriteTracksFragment mFavoriteTracksFragment;

    private FavoritesComponent mComponent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, rootView);
        setUpTabLayout();
        setUpViewPager();

        mComponent = DaggerFavoritesComponent.builder()
                .favoritesModule(new FavoritesModule(this))
                .skiffleApplicationComponent(SkiffleApp.getInstance().getComponent())
                .build();
        mComponent.inject(this);

        return rootView;
    }

    @Override
    public void showFavoriteAlbums(ArrayList<AlbumItem> albums) {
        mFavoriteAlbumsFragment.showFavoriteAlbums(albums);
    }

    @Override
    public void showFavoriteTracks(ArrayList<TrackItem> trackItems) {
        mFavoriteTracksFragment.showTracks(trackItems);
    }

    @Override
    public void showEmptyAlbumsUi() {
        mFavoriteAlbumsFragment.showEmptyUi();
    }

    @Override
    public void showEmptyTracksUi() {
        mFavoriteTracksFragment.showEmptyUi();
    }

    @Override
    public void onFavoriteAlbumItemClicked(int position, AlbumItem album) {
        Intent i = new Intent(getActivity(), ShowAlbumDetailsActivity.class);
        i.putExtra("album_id", album.id);
        i.putExtra("album_title", album.name);
        i.putExtra("is_saved", true);
        startActivity(i);
    }

    @Override
    public void onFavoriteTrackItemClicked(int position, TrackItem track) {
        Intent i = new Intent(getActivity(), ShowTrackDetailsActivity.class);
        i.putExtra("track_id", track.id);
        i.putExtra("track_title", track.name);
        i.putExtra("is_saved", true);
        startActivity(i);
    }

    private void setUpTabLayout(){
        mTabLayout.addTab(mTabLayout.newTab().setText("Info"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tracks"));
    }

    private void setUpViewPager(){
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void loadFavoriteTracks() {
        mPresenter.loadFavoriteTracks();
    }

    @Override
    public FavoritesComponent getComponent() {
        return mComponent;
    }

    @Override
    public void loadFavoriteAlbums() {
        mPresenter.loadFavoriteAlbums();
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 1 :
                    if (mFavoriteAlbumsFragment == null){
                        mFavoriteAlbumsFragment = FavoriteAlbumsFragment.getInstance();
                        mFavoriteAlbumsFragment.setCommunicator(FavoritesFragment.this);
                    }
                    return mFavoriteAlbumsFragment;
                case 0 :
                    if (mFavoriteTracksFragment == null){
                        mFavoriteTracksFragment = FavoriteTracksFragment.getInstance();
                        mFavoriteTracksFragment.setCommunicator(FavoritesFragment.this);
                    }
                    return mFavoriteTracksFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Tracks";
                case 1:
                    return "Albums";
                default:
                    return "Info";
            }
        }
    }
}
