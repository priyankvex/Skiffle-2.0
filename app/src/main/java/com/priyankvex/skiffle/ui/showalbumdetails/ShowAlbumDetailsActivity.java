package com.priyankvex.skiffle.ui.showalbumdetails;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.SkiffleApp;
import com.priyankvex.skiffle.component.DaggerShowAlbumDetailsComponent;
import com.priyankvex.skiffle.component.ShowAlbumDetailsComponent;
import com.priyankvex.skiffle.model.AlbumDetails;
import com.priyankvex.skiffle.model.TrackItem;
import com.priyankvex.skiffle.module.ShowAlbumDetailsModule;
import com.priyankvex.skiffle.ui.showtrackdetails.ShowTrackDetailsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by @priyankvex on 28/3/17.
 */

public class ShowAlbumDetailsActivity extends AppCompatActivity implements ShowAlbumDetailsFragment.AlbumDetailsCommunicator,
        ShowAlbumTracksFragment.AlbumTracksCommunicator, ShowAlbumDetailsMvp.ShowAlbumDetailsView{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.button_like)
    LikeButton buttonLike;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.error_layout)
    RelativeLayout errorLayout;

    @Inject
    ShowAlbumDetailsPresenter mPresenter;

    private ShowAlbumDetailsFragment mDetailsFragment;

    private ShowAlbumTracksFragment mTracksFragment;

    private ShowAlbumDetailsComponent mComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_album_details);
        ButterKnife.bind(this);
        setUpToolbar();
        setUpTabLayout();
        mComponent = DaggerShowAlbumDetailsComponent.builder()
                .showAlbumDetailsModule(new ShowAlbumDetailsModule(this))
                .skiffleApplicationComponent(SkiffleApp.getInstance().getComponent())
                .build();
        mComponent.inject(this);

        //Log.d("owlcity", "in on create album details");

        if (savedInstanceState != null) {
            mDetailsFragment = (ShowAlbumDetailsFragment) getSupportFragmentManager().findFragmentByTag("customtag");
        }

        mPresenter.setSavedAlbum(getIntent().getBooleanExtra("is_saved", false));
        mPresenter.getAlbumDetails(getIntent().getStringExtra("album_id"));

        buttonLike.setLiked(getIntent().getBooleanExtra("is_saved", false));
        buttonLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                // disable button to prevent multiple clicked
                buttonLike.setEnabled(false);
                mPresenter.saveAlbumTOFavorite();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                buttonLike.setEnabled(false);
                mPresenter.deleteAlbumFromFavorites();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar(){
        String title = getIntent().getStringExtra("album_title");
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
    public void reenableLikeButton() {
        buttonLike.setEnabled(true);
    }

    @Override
    public void setLikedButtonStatus(boolean likedStatus) {
        buttonLike.setLiked(likedStatus);
    }

    @Override
    public void onAlbumTrackClicked(TrackItem trackItem, int position) {
        Intent intent = new Intent(this, ShowTrackDetailsActivity.class);
        intent.putExtra("track_id", trackItem.id);
        intent.putExtra("track_title", trackItem.name);
        startActivity(intent);
    }

    @Override
    public void showErrorUi() {
        progressBar.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
        buttonLike.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAlbumDetails(AlbumDetails album) {
        mViewPager.setVisibility(View.VISIBLE);
        buttonLike.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        //Log.d("owlcity", "in on show album details");
        setUpViewPager();
    }

    @Override
    public void getAlbumDetails(ShowAlbumDetailsFragment fragment) {
        if (mDetailsFragment == null)
            mDetailsFragment = fragment;
        mDetailsFragment.showAlbumDetails(mPresenter.getAlbumDetails());
    }

    @Override
    public void getAlbumTracks() {
        if (mTracksFragment != null){
            mTracksFragment.showTracks(mPresenter.getAlbumDetails());
        }
    }

    @Override
    public ShowAlbumDetailsComponent getComponent() {
        return mComponent;
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
                        mDetailsFragment = ShowAlbumDetailsFragment.getInstance();
                    }
                    return mDetailsFragment;
                case 1 :
                    if (mTracksFragment == null){
                        mTracksFragment = ShowAlbumTracksFragment.getInstance();
                    }
                    return mTracksFragment;
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
                    return "Tracks";
                default:
                    return "Info";
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("saved_instance", true);
    }
}
