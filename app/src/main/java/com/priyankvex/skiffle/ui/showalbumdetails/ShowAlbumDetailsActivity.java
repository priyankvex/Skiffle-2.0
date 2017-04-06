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

import com.priyankvex.skiffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by @priyankvex on 28/3/17.
 */

public class ShowAlbumDetailsActivity extends AppCompatActivity implements ShowAlbumDetailsFragment.AlbumDetailsCommunicator,
        ShowAlbumTracksFragment.AlbumTracksCommunicator{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_album_details);
        ButterKnife.bind(this);
        setUpToolbar();
        setUpTabLayout();
        setUpViewPager();
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

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0 : return ShowAlbumDetailsFragment.getInstance();
                case 1 : return ShowAlbumTracksFragment.getInstance();
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
