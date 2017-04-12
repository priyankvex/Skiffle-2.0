package com.priyankvex.skiffle.ui.showalbumdetails;

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

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.SkiffleApp;
import com.priyankvex.skiffle.component.DaggerShowAlbumDetailsComponent;
import com.priyankvex.skiffle.component.ShowAlbumDetailsComponent;
import com.priyankvex.skiffle.model.AlbumDetails;
import com.priyankvex.skiffle.module.ShowAlbumDetailsModule;

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
        setUpViewPager();
        Log.d("owlcity", "Album id : " + getIntent().getStringExtra("album_id"));
        mComponent = DaggerShowAlbumDetailsComponent.builder()
                .showAlbumDetailsModule(new ShowAlbumDetailsModule(this))
                .skiffleApplicationComponent(SkiffleApp.getInstance().getComponent())
                .build();
        mComponent.inject(this);
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
    public void showErrorUi() {
        if (mDetailsFragment != null){
            mDetailsFragment.showErrorUi();
        }
        if (mTracksFragment != null){
            mTracksFragment.showErrorUi();
        }
    }

    @Override
    public void showAlbumDetails(AlbumDetails album) {
        if (mDetailsFragment != null){
            mDetailsFragment.showAlbumDetails(album);
        }
    }

    @Override
    public void showTracks(AlbumDetails album) {
        if (mTracksFragment != null){
            mTracksFragment.showTracks(album);
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

}
