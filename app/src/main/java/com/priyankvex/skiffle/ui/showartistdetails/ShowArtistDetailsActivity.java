package com.priyankvex.skiffle.ui.showartistdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.SkiffleApp;
import com.priyankvex.skiffle.component.DaggerShowArtistDetailsComponent;
import com.priyankvex.skiffle.component.ShowArtistDetailsComponent;
import com.priyankvex.skiffle.model.ArtistDetails;
import com.priyankvex.skiffle.model.TrackItem;
import com.priyankvex.skiffle.model.TrackList;
import com.priyankvex.skiffle.module.ShowArtistDetailsModule;
import com.priyankvex.skiffle.ui.showalbumdetails.ShowAlbumDetailsActivity;
import com.priyankvex.skiffle.ui.showtrackdetails.ShowTrackDetailsActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankvex on 3/5/17.
 */

public class ShowArtistDetailsActivity extends AppCompatActivity implements ShowArtistDetailsMvp.ShowArtistDetailsView,
        ShowArtistDetailsFragment.ShowArtistDetailsFragmentCommunicator, ShowArtistTracksFragment.ShowArtistsTracksCommunicator{

    @Inject
    ShowArtistDetailsPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private ShowArtistDetailsFragment mDetailsFragment;

    private ShowArtistTracksFragment mArtistTracksFragment;

    private ShowArtistDetailsComponent mComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_artist_details);
        ButterKnife.bind(this);
        setUpToolbar();
        setUpTabLayout();
        setUpViewPager();
        mComponent = DaggerShowArtistDetailsComponent.builder()
                .showArtistDetailsModule(new ShowArtistDetailsModule(this))
                .skiffleApplicationComponent(SkiffleApp.getInstance().getComponent())
                .build();
        mComponent.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onStop();
    }

    private void setUpToolbar(){
        String title = getIntent().getStringExtra("artist_title");
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpTabLayout(){
        mTabLayout.addTab(mTabLayout.newTab().setText("Info"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tracks"));
    }

    private void setUpViewPager(){
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void showArtistDetails(ArtistDetails artistDetails) {
        if (mDetailsFragment != null){
            mDetailsFragment.showArtistDetails(artistDetails);
        }
    }

    @Override
    public void showArtistDetailsErrorUi() {
        if (mDetailsFragment != null){
            mDetailsFragment.showErrorUi();
        }
    }

    @Override
    public void showArtistTopTracks(ArrayList<TrackItem> trackList) {
        if (mArtistTracksFragment != null){
            mArtistTracksFragment.showArtistTopTracks(trackList);
        }
    }

    @Override
    public void showArtistTopTrackErrorUi() {
        if (mArtistTracksFragment != null){
            mArtistTracksFragment.showErrorUi();
        }
    }

    @Override
    public void onArtistTrackClicked(TrackItem trackItem, int position) {
        Intent intent = new Intent(this, ShowTrackDetailsActivity.class);
        intent.putExtra("track_id", trackItem.id);
        intent.putExtra("track_title", trackItem.name);
        startActivity(intent);
    }

    @Override
    public void getArtistDetails() {
        String artistId = getIntent().getStringExtra("artist_id");
        mPresenter.getArtistDetails(artistId);
    }

    @Override
    public ShowArtistDetailsComponent getComponent() {
        return mComponent;
    }

    @Override
    public void getArtistsTopTracks() {
        String artistId = getIntent().getStringExtra("artist_id");
        mPresenter.getArtistTopTracks(artistId);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0 :
                    if (mDetailsFragment == null){
                        mDetailsFragment = ShowArtistDetailsFragment.getInstance();
                    }
                    return mDetailsFragment;
                case 1 :
                    if (mArtistTracksFragment == null){
                        mArtistTracksFragment = ShowArtistTracksFragment.getInstance();
                    }
                    return mArtistTracksFragment;
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
                    return "Info";
                case 1:
                    return "Top Tracks";
                default:
                    return "Info";
            }
        }
    }
}
