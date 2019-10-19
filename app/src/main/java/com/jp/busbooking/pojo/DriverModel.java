package com.jp.busbooking.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class DriverModel implements Serializable {
    String name,id,busid,lat,lng;

    public DriverModel(String name, String id, String busid, String lat, String lng) {
        this.name = name;
        this.id = id;
        this.busid = busid;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
