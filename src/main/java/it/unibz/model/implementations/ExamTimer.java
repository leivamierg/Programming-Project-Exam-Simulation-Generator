package it.unibz.model.implementations;

import java.util.concurrent.TimeUnit;

public class ExamTimer implements Runnable {

    private int seconds;
    private Simulation simulation;
    private volatile boolean running;

    public ExamTimer(int seconds, Simulation simulation) {
        this.seconds = seconds;
        this.simulation = simulation;
        this.running = true;
    }

    @Override
    public void run() {
        int i = seconds;
        try {
            while (i >= 0 && running) {
                int mins = i / 60;
                int remSecs = i % 60;

                System.out.printf("\r%02d:%02d", mins, remSecs);
                System.out.flush();

                TimeUnit.SECONDS.sleep(1);
                i--;
            }
            if (running) {
                System.out.println("\nSimulation ended due to timeout.");
                simulation.terminate(new Stats());
            }
        } catch (InterruptedException e) {
            System.out.println("\nTimer stopped");
        }
    }

    public void stopTimer() {
        running = false;
    }
}
