package com.priyankvex.skiffle.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by priyankvex on 12/4/17.
 */

public class AlbumItem {

    @SerializedName("album_type")
    public String albumType;
    public String id;
    public String name;
    public String type;
    public ArrayList<ArtistItem> artists;
    public ArrayList<ImageItem> images;
}
