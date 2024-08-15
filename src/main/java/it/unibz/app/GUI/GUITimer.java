package it.unibz.app.GUI;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GUITimer {
    static long mins, secs, hrs, totalSecs = 0;

    private static String format(long value) {
        if (value < 10) {
            return "0" + value;
        } else {
            return Long.toString(value);
        }
    }

    public static void convertTime() {
        mins = TimeUnit.SECONDS.toMinutes(totalSecs);
        secs = totalSecs - (mins * 60);

        hrs = TimeUnit.MINUTES.toHours(mins);
        mins = mins - (hrs * 60);

        System.out.println(format(hrs) + ":" + format(mins) + ":" + format(secs));
        totalSecs--;
    }

    public static void main(String[] args) {

        totalSecs = 12;

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                convertTime();
                if (totalSecs <= 0) {
                    System.exit(0);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

}
