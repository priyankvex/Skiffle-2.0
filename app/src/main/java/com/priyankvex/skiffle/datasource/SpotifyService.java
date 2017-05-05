package com.priyankvex.skiffle.datasource;

import com.priyankvex.skiffle.model.AlbumDetails;
import com.priyankvex.skiffle.model.ArtistDetails;
import com.priyankvex.skiffle.model.ArtistTopTracks;
import com.priyankvex.skiffle.model.NewReleases;
import com.priyankvex.skiffle.model.SearchResults;
import com.priyankvex.skiffle.model.TrackDetails;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by @priyankvex on 25/3/17.
 */

public interface SpotifyService {

    @GET("browse/new-releases")
    Observable<NewReleases> getNewReleases(
            @HeaderMap Map<String, String> headers
    );

    @GET("albums/{id}")
    Observable<AlbumDetails> getAlbum(
            @Path("id") String id,
            @HeaderMap Map<String, String> headers
    );

    @GET("tracks/{id}?market=US")
    Observable<TrackDetails> getTrack(
            @Path("id") String id,
            @HeaderMap Map<String, String> headers
    );

    @GET("artists/{id}")
    Observable<ArtistDetails> getArtist(
            @Path("id") String id,
            @HeaderMap Map<String, String> headers
    );

    @GET("artists/{id}/top-tracks?country=US")
    Observable<ArtistTopTracks> getArtistTopTracks(
            @Path("id") String id,
            @HeaderMap Map<String, String> headers
    );

    @GET("search?type=track,artist,album&limit=3")
    Observable<SearchResults> getSearchResults(
            @Query("q") String query,
            @HeaderMap Map<String, String> headers
    );

    @GET("search?type=track&limit=20")
    Observable<SearchResults> getSongResults(
            @Query("q") String query,
            @HeaderMap Map<String, String> headers
    );

    @GET("search?type=album&limit=20")
    Observable<SearchResults> getAlbumResults(
            @Query("q") String query,
            @HeaderMap Map<String, String> headers
    );

    @GET("search?type=artist&limit=20")
    Observable<SearchResults> getArtistResults(
            @Query("q") String query,
            @HeaderMap Map<String, String> headers
    );
}
