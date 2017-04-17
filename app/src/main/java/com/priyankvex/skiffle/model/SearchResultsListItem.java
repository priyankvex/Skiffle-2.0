package com.priyankvex.skiffle.model;

/**
 * Created by priyankvex on 17/4/17.
 */

public class SearchResultsListItem {

    public enum ViewType{
        HEADER_VIEW, ITEM_VIEW
    }

    public enum ItemType{
        TRACK, ALBUM, ARTIST
    }

    public ViewType viewType;
    public String title;
    public String subTitle;
    public String thumbImageUrl;
    public ItemType itemType;
}
