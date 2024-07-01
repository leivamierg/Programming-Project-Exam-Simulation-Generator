package it.unibz.app;

import it.unibz.controller.Controller;
import it.unibz.model.implementations.FileLoader;
import it.unibz.model.implementations.Model;
import it.unibz.model.implementations.Topic;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) throws InterruptedException {

        Controller controller = new Controller(new Model());
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
                System.out.println("Exiting the simulation");
                break;
            }

            if (input.equals("-h")) {
                System.out.println("-t or --topics to list all topics");
                System.out.println("<topic> -s, --subtopics to list all subtopics");
                System.out.println("<topic> to start the test");
                //System.out.println("<topic> --select to list all subtopics to select from");
                showWelcomeMessage = false;
                continue;
            }
            controller.elaborateArgs(input.split("\\s+"));

        }

        scanner.close();
    }
}
