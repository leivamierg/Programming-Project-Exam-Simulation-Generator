package it.unibz.model.implementations;

import java.util.concurrent.TimeUnit;

public class ExamTimer implements Runnable {
//    private Simulation simulation;
    private boolean running;
    private int remainingTime;
    private final int DURATION_SIMULATION = 60*30;

    public ExamTimer() {
    //    this.simulation = simulation;
        this.running = true;
        this.remainingTime = DURATION_SIMULATION;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public boolean getRunning() {
        return running;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Override
    public void run() {
        int i = DURATION_SIMULATION;
        try {
            while (i >= 0) {
                int mins = i / 60;
                int remSecs = i % 60;

                System.out.printf("\r%02d:%02d", mins, remSecs);

                --i;

                setRemainingTime(i);
                TimeUnit.SECONDS.sleep(1);
            }

            System.out.println("\nSimulation ended due to timeout.");
//          simulation.terminate(new Stats());

        } catch (InterruptedException e) {
            System.out.println("\nTimer stopped");
        }
    }

    public void stopTimer() {
        running = false;
    }


    public String calculateTimeTaken_String()
    {
        return String.valueOf(DURATION_SIMULATION - getRemainingTime());
    }

}
