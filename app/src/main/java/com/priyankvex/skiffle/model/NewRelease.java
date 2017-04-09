package com.priyankvex.skiffle.model;

import java.util.ArrayList;

/**
 * Created by @priyankvex on 26/3/17.
 */

public class NewRelease {

    public Album albums;

    public static class Album {

        public ArrayList<Item> items;

        public static class Item {
            public String id;
            public String name;
            public String type;
            public ArrayList<Artist> artists;
            public ArrayList<Image> images;

            public static class Artist {
                public String name;
                public String id;
            }

            public static class Image {
                public String url;
                public int height;
                int width;
            }
        }
    }

}
