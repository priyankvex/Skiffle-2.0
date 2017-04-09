package com.priyankvex.skiffle.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by @priyankvex on 6/4/17.
 */

public class Album {

    public String id;
    public String name;
    @SerializedName("album_type")
    public String albumType;
    public ArrayList<Artist> artists;
    public String label;
    @SerializedName("release_date")
    public String releaseDate;
    public ArrayList<Image> images;
    public Track tracks;

    public static class Artist {
        public String name;
        public String id;
    }

    public static class Image {
        public String url;
        public int height;
        int width;
    }

    public static class Track {

        public int total;
        public ArrayList<Item> items;

        public static class Item {
            public String name;
            @SerializedName("preview_url")
            public String previewUrl;
        }
    }
}
