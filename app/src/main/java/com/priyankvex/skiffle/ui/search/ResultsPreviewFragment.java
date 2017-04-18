package com.priyankvex.skiffle.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.component.SearchComponent;
import com.priyankvex.skiffle.model.SearchResultsListItem;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankvex on 18/4/17.
 */

public class ResultsPreviewFragment extends Fragment{

    static ResultsPreviewFragment getInstance(){
        return new ResultsPreviewFragment();
    }

    @Inject
    SearchResultsAdapter mAdapter;

    private ResultsPreviewCommunicator mCommunicator;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchActivity){
            mCommunicator = (ResultsPreviewCommunicator) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results_preview, container, false);
        ButterKnife.bind(this, rootView);
        mCommunicator.getSearchComponent().inject(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    interface ResultsPreviewCommunicator {
        SearchComponent getSearchComponent();
    }

    void showSearchResults(ArrayList<SearchResultsListItem> results){
        mAdapter.swapData(results);
    }
}
