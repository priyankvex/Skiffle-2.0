package com.priyankvex.skiffle.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by @priyankvex on 7/4/17.
 */

@Entity
public class AlbumEntity {

    @Id(autoincrement = true)
    private Long id;
    private String albumId;
    private String albumJsonData;
    @Generated(hash = 1551636128)
    public AlbumEntity(Long id, String albumId, String albumJsonData) {
        this.id = id;
        this.albumId = albumId;
        this.albumJsonData = albumJsonData;
    }
    @Generated(hash = 239110058)
    public AlbumEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAlbumId() {
        return this.albumId;
    }
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
    public String getAlbumJsonData() {
        return this.albumJsonData;
    }
    public void setAlbumJsonData(String albumJsonData) {
        this.albumJsonData = albumJsonData;
    }
}
