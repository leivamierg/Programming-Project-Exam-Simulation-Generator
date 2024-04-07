package it.unibz.controller;

import it.unibz.model.ModelInt;

import java.util.Scanner;

public class Controller {

    private ModelInt model;

    public Controller(ModelInt model) {
        this.model = model;
    }

    public void readInput() {
        Scanner scan = new Scanner(System.in);
        boolean run = true;

        while (run) {
            System.out.println("Choose what to do: ");
            System.out.println("Options: List, Subtopics, Test, Exit");
            String input = scan.nextLine().trim();

            String[] readInput = input.split(" ");

            switch (readInput[0]) {
                case "List":
                    model.list();
                    break;
                case "Subtopics":
                    if(readInput.length < 2)
                    {
                        System.out.println("Need to write: Subtopics <topic>");
                    } else {
                        model.listSubtopics(readInput[1]);
                    }
                    break;
                case "Test":
                    if(readInput.length < 3)
                    {
                        System.out.println("Need to write: Test <topic> <subtopic>");
                    } else {
                        model.test(readInput[1], readInput[2]);
                    }
                    break;
                case "Exit":
                    run = false;
                    break;
                default:
                    System.out.println("Invalid");
                    break;
            }
        }
        scan.close();
    }
}
