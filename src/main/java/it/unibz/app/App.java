package it.unibz.app;

import it.unibz.controller.Controller;
import it.unibz.model.implementations.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * The main class of the program.
 *
 */
public class App {

    /**
     * The main method of the program.
     * 
     * @param args The command-line arguments.
     */
    public static void main( String[] args ) throws IOException {

        Controller controller = new Controller(new Model()/*, new History(), new Stats()*/);
        Scanner scanner = new Scanner(System.in);

        String input;
        boolean showWelcomeMessage = true;

        while (true) {
            if (showWelcomeMessage)
                System.out.println("Welcome to the exam simulation program! Click -h for commands");

            input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                Set<Topic> loadedTopics = FileLoader.getTopics();
                FileLoader.saveBank(System.getProperty("user.dir") + "/src/main/resources/bank/", List.copyOf(loadedTopics));

                HistoryStatsLoader.saveStats("src/main/resources/h_s/stats.json", Model.getLoadedStats());
                HistoryStatsLoader.saveHistory("src/main/resources/h_s/history.json", Model.getLoadedHistory());

                System.out.println("Exiting the simulation");
                break;
            }

            if (input.equals("-h")) {
                System.out.println("-t or --topics to list all topics");
                System.out.println("<topic> -s, --subtopics to list all subtopics");
                System.out.println("<topic> to start the test");
                //System.out.println("<topic> --select to list all subtopics to select from");
                System.out.println("<history> to show the history of simulation");
                System.out.println("<stats> to show the general stats");
                System.out.println("<exit> to close the exam simulation program");
                showWelcomeMessage = false;
                continue;
            }
            controller.elaborateArgs(input.split("\\s+"));

        }

        scanner.close();
    }
}
