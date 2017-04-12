package com.priyankvex.skiffle.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by priyankvex on 12/4/17.
 */

public class AlbumDetails {

    @SerializedName("album_type")
    public String albumType;
    public ArrayList<ArtistItem> artists;
    public String id;
    public ArrayList<ImageItem> images;
    public String label;
    public String name;
    @SerializedName("release_date")
    public String releaseDate;
    public TrackList tracks;
    public String type;

}
