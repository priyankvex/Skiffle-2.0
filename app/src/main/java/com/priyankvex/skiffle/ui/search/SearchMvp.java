package com.priyankvex.skiffle.ui.search;

import com.priyankvex.skiffle.model.AlbumItem;
import com.priyankvex.skiffle.model.AlbumList;
import com.priyankvex.skiffle.model.ArtistItem;
import com.priyankvex.skiffle.model.ArtistList;
import com.priyankvex.skiffle.model.SearchResultsListItem;
import com.priyankvex.skiffle.model.TrackItem;
import com.priyankvex.skiffle.model.TrackList;

import java.util.ArrayList;

/**
 * Created by priyankvex on 13/4/17.
 */

public interface SearchMvp {

    interface SearchView{
        void showSearchResults(ArrayList<SearchResultsListItem> results);
        void showSongResults(TrackList trackList);
        void showAlbumSearchResults(AlbumList albumList);
        void showArtistSearchResults(ArtistList artistList);
        void onSearchResultsPreviewItemClicked(SearchResultsListItem item, int position);
        void onArtistItemClicked(ArtistItem item, int position);
        void onTrackItemClicked(TrackItem item, int position);
        void onAlbumItemClicked(AlbumItem item, int position);
    }

    interface SearchPresenter{
        void getSearchResults(String query);
        void getSongResults(String query);
        void getAlbumResults(String query);
        void getArtistResults(String query);
    }
}
