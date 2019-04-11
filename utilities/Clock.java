package utilities;

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
       this.cancel();
    }
    public void updateTimer() {
        String secondsView, minutesView; // left/right side of clock divided by ':'
        if (time < 10) secondsView = "0"; //put '0' before seconds value to make it look more natural (00:00)
        else secondsView = "";
        if (minutes < 10) minutesView = "0";//put '0' before minutes value to make it look more natural (00:00)
        else minutesView = "";
        timeValue = minutesView + minutes + ":" + secondsView + time;
        time++;
        if (time % 60 == 0) {
            minutes++;
            time = 0;
        }
    }
}
