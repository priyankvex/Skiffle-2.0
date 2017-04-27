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
import com.priyankvex.skiffle.model.TrackList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankvex on 27/4/17.
 */

public class SearchResultsFragment extends Fragment{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private SearchResultsFragmentCommunicator mCommunicator;

    static SearchResultsFragment getInstance(String resultType){
        Bundle b = new Bundle();
        b.putString("result_type", resultType);
        SearchResultsFragment fragment = new SearchResultsFragment();
        fragment.setArguments(b);
        return fragment;
    }
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchActivity){
            mCommunicator = (SearchResultsFragmentCommunicator) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);
        ButterKnife.bind(this, rootView);
        mCommunicator.loadSongsForSearch();
        return rootView;
    }

    public void showSongs(TrackList trackList){
        SongResultsAdapter adapter =
                new SongResultsAdapter((SearchMvp.SearchView)getActivity(), getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.swapData(trackList.items);
    }

    interface SearchResultsFragmentCommunicator {
        void loadSongsForSearch();
        void loadAlbumsForSearch();
        void loadArtistsForSearch();
    }
}
