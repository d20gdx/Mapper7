package com.roderick.steve.mapper7;

/**
 * Created by SRoderick on 27/01/2017.
 */

public class Row implements Comparable<Row>{

    private String time;
    private String counter;
    private String distance;
    private String lsnr;
    private String datarate;


    public Row(String time, String counter, String distance, String lsnr, String datarate) {
        this.time = time;
        this.counter = counter;
        this.distance = distance;
        this.lsnr = lsnr;
        this.datarate = datarate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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

    public int compareTo(Row o) {
        int i;
        try {
            i = Helper.transformDate(time).getTime() < Helper.transformDate(o.time).getTime() ? -1 : 1;
        } catch (Exception e) {
            e.printStackTrace();
            i = 1;
        }

        return i;
    }


}
