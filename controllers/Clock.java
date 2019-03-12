package controllers;

import java.util.Timer;


public class Clock extends Timer {
    private int time;
    private int minutes;
    private String timeValue;

    public Clock() {
        time = 1;
        minutes = 0;
    }

    public String getTimeValue() {
        return timeValue;
    }

    public void stopTime() {
        // time=-1;
        this.cancel();
    }

    public void updateTimer() {
        String single, doubl;
        if (time < 10) single = "0";
        else single = "";
        if (minutes < 10) doubl = "0";
        else doubl = "";
        timeValue = doubl + minutes + ":" + single + time;
        time++;
        if (time % 60 == 0) {
            minutes++;
            time = 0;
        }
    }
}
