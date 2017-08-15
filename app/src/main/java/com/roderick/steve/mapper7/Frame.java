package com.roderick.steve.mapper7;

/**
 * Created by SRoderick on 25/01/2017.
 */

public class Frame {

    private String time;
    private String counter;
    private String devAdd;
    private String gateway_EUI;
    private String distance;
    private String longi;
    private String lat;
    private String lsnr;
    private String rssi;
    private String datarate;

    public Frame(String time, String counter, String devAdd, String gateway_EUI, String distance, String longi, String lat, String lsnr, String rssi, String datarate) {
        this.time = time;
        this.counter = counter;
        this.devAdd = devAdd;
        this.gateway_EUI = gateway_EUI;
        this.distance = distance;
        this.longi = longi;
        this.lat = lat;
        this.lsnr = lsnr;
        this.rssi = rssi;
        this.datarate = datarate;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDevAdd() {
        return devAdd;
    }

    public void setDevAdd(String devAdd) {
        this.devAdd = devAdd;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getLsnr() {
        return lsnr;
    }

    public void setLsnr(String lsnr) {
        this.lsnr = lsnr;
    }

    public String getDatarate() {
        return datarate;
    }

    public void setDatarate(String datarate) {
        this.datarate = datarate;
    }

    public String getGateway_EUI() {
        return gateway_EUI;
    }

    public void setGateway_EUI(String gateway_EUI) {
        this.gateway_EUI = gateway_EUI;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }


}
