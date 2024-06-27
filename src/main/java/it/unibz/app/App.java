package it.unibz.app;

import it.unibz.controller.Controller;
import it.unibz.model.implementations.Model;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws InterruptedException {
        Controller controller = new Controller(new Model());
        Scanner scanner = new Scanner(System.in);

        String input;
        while (true) {
            System.out.println("Welcome to the exam simulation: ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the simulation");
                break;
            }
            controller.elaborateArgs(input.split("\\s+"));
        }

        scanner.close();
    }
}
