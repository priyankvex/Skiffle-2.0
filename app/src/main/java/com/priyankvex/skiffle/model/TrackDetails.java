package com.priyankvex.skiffle.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by priyankvex on 13/4/17.
 */

public class TrackDetails {

    public AlbumItem album;
    public ArrayList<ArtistItem> artists;
    public String id;
    public String name;
    @SerializedName("preview_url")
    public String previewUrl;
    public String type;
}
