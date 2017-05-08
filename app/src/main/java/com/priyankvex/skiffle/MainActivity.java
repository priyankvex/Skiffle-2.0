package com.priyankvex.skiffle;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.priyankvex.skiffle.ui.favorites.FavoritesFragment;
import com.priyankvex.skiffle.ui.home.NewReleasesFragment;
import com.priyankvex.skiffle.ui.recommendations.RecommendationsFragment;
import com.priyankvex.skiffle.util.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpDrawerLayout();
        setUpToolbar("Skiffle");
    }

    protected void setUpDrawerLayout(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        // always start with new release fragment
        ActivityUtil.replaceFragmentInContainer(getSupportFragmentManager(),
                new NewReleasesFragment(), R.id.container, "nav_frag");
    }

    protected void setUpToolbar(String title){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item_home:
                ActivityUtil.replaceFragmentInContainer(getSupportFragmentManager(),
                        new NewReleasesFragment(), R.id.container, "nav_frag");
                break;

            case R.id.item_favorites:
                ActivityUtil.replaceFragmentInContainer(getSupportFragmentManager(),
                        new FavoritesFragment(), R.id.container, "nav_frag");
                break;

            case R.id.item_recommendations:
                ActivityUtil.replaceFragmentInContainer(getSupportFragmentManager(),
                        RecommendationsFragment.getInstance(), R.id.container, "nav_frag");
                break;
        }
        item.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        //searchView.setQueryHint(getResources().getString(R.string.search_hint));
        return true;

    }
}
