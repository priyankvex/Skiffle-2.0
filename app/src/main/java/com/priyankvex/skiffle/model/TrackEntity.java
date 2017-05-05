package com.priyankvex.skiffle.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by priyankvex on 5/5/17.
 */

@Entity
public class TrackEntity {

    @Id(autoincrement = true)
    private Long id;
    private String trackId;
    private String trackJsonData;
    @Generated(hash = 25897559)
    public TrackEntity(Long id, String trackId, String trackJsonData) {
        this.id = id;
        this.trackId = trackId;
        this.trackJsonData = trackJsonData;
    }
    @Generated(hash = 545233167)
    public TrackEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTrackId() {
        return this.trackId;
    }
    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
    public String getTrackJsonData() {
        return this.trackJsonData;
    }
    public void setTrackJsonData(String trackJsonData) {
        this.trackJsonData = trackJsonData;
    }

}
