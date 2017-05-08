package com.priyankvex.skiffle.ui.recommendations;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.SkiffleApp;
import com.priyankvex.skiffle.component.DaggerRecommendationsComponent;
import com.priyankvex.skiffle.component.RecommendationsComponent;
import com.priyankvex.skiffle.model.TrackItem;
import com.priyankvex.skiffle.model.TrackList;
import com.priyankvex.skiffle.module.RecommendationsModule;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankvex on 8/5/17.
 */

public class RecommendationsFragment extends Fragment implements RecommendationsMvp.View{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;

    @BindView(R.id.error_layout)
    RelativeLayout errorLayout;

    @Inject
    RecommendationsPresenter mPresenter;

    @Inject
    RecommendationsAdapter mAdapter;

    public static RecommendationsFragment getInstance(){
        return new RecommendationsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recommendations, container, false);
        ButterKnife.bind(this, rootView);

        RecommendationsComponent component = DaggerRecommendationsComponent.builder()
                .recommendationsModule(new RecommendationsModule(this))
                .skiffleApplicationComponent(SkiffleApp.getInstance().getComponent())
                .build();
        component.inject(this);

        mPresenter.loadRecommendations();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void showRecommendations(ArrayList<TrackItem> recommendedTracks) {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        mAdapter.swapData(recommendedTracks );
    }

    @Override
    public void showNoRecommendationsUi() {
        progressBar.setVisibility(View.INVISIBLE);
        emptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorUi() {
        progressBar.setVisibility(View.INVISIBLE);
        errorLayout.setVisibility(View.VISIBLE);
    }
}
