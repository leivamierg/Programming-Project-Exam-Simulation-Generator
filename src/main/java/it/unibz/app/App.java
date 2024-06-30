package it.unibz.app;

import it.unibz.controller.Controller;
import it.unibz.model.implementations.Model;
//import it.unibz.model.implementations.Model;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws InterruptedException {
        //String GREEN_TEXT = "\u001B[32m";

        Controller controller = new Controller(new Model());
        Scanner scanner = new Scanner(System.in);

        String input;
        boolean showWelcomeMessage = true;

        while (true) {
            if (showWelcomeMessage)
            {
                System.out.println("Welcome to the exam simulation program! Click -h for commands");
            }

            input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the simulation");
                break;
            }

            if (input.equals("-h")) {
                System.out.println("-t or --topics to list all topics");
                System.out.println("<topic> -s, --subtopics to list all subtopics");
                System.out.println("<topic> to start the test");
                System.out.println("<topic> --select to list all subtopics to select from");
                showWelcomeMessage = false;
                continue;
            }
            controller.elaborateArgs(input.split("\\s+"));

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the simulation");
                break;
            }
        }

        scanner.close();
    }
}
