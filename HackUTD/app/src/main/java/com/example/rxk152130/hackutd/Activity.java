package com.example.rxk152130.hackutd;

import java.util.Date;

/**
 * Created by rxk152130 on 3/4/2017.
 */

public class Activity {
    String activateName;
    String userName;
    String msg;
    String number;
    double lat;
    double lng;
    double distance;
    Date startTime;
    Date endTime;

    public Activity(String activateName, String userName, String msg, String number, double lat, double lng, double distance, Date startTime, Date endTime) {
        this.activateName = activateName;
        this.userName = userName;
        this.msg = msg;
        this.number = number;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Activity(){

    }


}
