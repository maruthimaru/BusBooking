package com.jp.busbooking.pojo;

import java.io.Serializable;
import java.util.List;

public class BusListModel1 implements Serializable {

    String id,busName,seats,ratings,amount,startTiming,endTiming,ratingMember,acNon,from,to;
    List<ImagesModel> bus_images_list;

    public BusListModel1(String id, String busName, String seats, String ratings, String amount, String startTiming,
                         String endTiming, String ratingMember, String acNon, String from, String to, List<ImagesModel> bus_images_list) {
        this.id = id;
        this.busName = busName;
        this.seats = seats;
        this.ratings = ratings;
        this.amount = amount;
        this.startTiming = startTiming;
        this.endTiming = endTiming;
        this.ratingMember = ratingMember;
        this.acNon = acNon;
        this.from = from;
        this.to = to;
        this.bus_images_list = bus_images_list;
    }

    public List<ImagesModel> getBus_images_list() {
        return bus_images_list;
    }

    public void setBus_images_list(List<ImagesModel> bus_images_list) {
        this.bus_images_list = bus_images_list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStartTiming() {
        return startTiming;
    }

    public void setStartTiming(String startTiming) {
        this.startTiming = startTiming;
    }

    public String getEndTiming() {
        return endTiming;
    }

    public void setEndTiming(String endTiming) {
        this.endTiming = endTiming;
    }

    public String getRatingMember() {
        return ratingMember;
    }

    public void setRatingMember(String ratingMember) {
        this.ratingMember = ratingMember;
    }

    public String getAcNon() {
        return acNon;
    }

    public void setAcNon(String acNon) {
        this.acNon = acNon;
    }
}
