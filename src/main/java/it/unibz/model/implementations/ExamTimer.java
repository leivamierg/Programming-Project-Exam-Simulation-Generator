package it.unibz.model.implementations;

import java.util.concurrent.TimeUnit;

/**
 * The ExamTimer class represents a timer for an exam simulation.
 * It implements the Runnable interface to allow it to be executed in a separate thread.
 */
public class ExamTimer implements Runnable {
    private boolean running;
    private int remainingTime;

    // Duration of the simulation in seconds
    public final int DURATION_SIMULATION = 60 * 30;

    /**
     * Constructs a new ExamTimer object.
     * The timer is initially set to running and the remaining time is set to the duration of the simulation.
     */
    public ExamTimer() {
        // this.simulation = simulation;
        this.running = true;
        this.remainingTime = DURATION_SIMULATION;
    }

    /**
     * Gets the remaining time of the exam simulation.
     * @return The remaining time in seconds.
     */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * Gets the running status of the timer.
     * @return true if the timer is running, false otherwise.
     */
    public boolean getRunning() {
        return running;
    }

    /**
     * Sets the remaining time of the exam simulation.
     * @param remainingTime The remaining time in seconds.
     */
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    /**
     * Executes the timer logic.
     * Decrements the remaining time every second and prints the time in minutes and seconds.
     * If the remaining time reaches 0, the simulation is terminated.
     */
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
            // simulation.terminate(new Stats());

        } catch (InterruptedException e) {
            System.out.println("\nTimer stopped");
        }
    }

    /**
     * Stops the timer.
     * Sets the running status to false.
     */
    public void stopTimer() {
        running = false;
    }

    /**
     * Calculates the time taken for the exam simulation as a string.
     * @return The time taken in seconds as a string.
     */
    public String calculateTimeTaken_String() {
        return String.valueOf(DURATION_SIMULATION - getRemainingTime());
    }

}
