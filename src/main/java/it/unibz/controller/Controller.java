package it.unibz.controller;

import it.unibz.model.interfaces.ModelInt;

public class Controller {

    private ModelInt model;

    public Controller(ModelInt model) {
        this.model = model;
    }

    public void elaborateArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Tester needs arguments to work. Pass as help to see the list of commands.");
            return;
        }

        String command = args[0].toLowerCase().strip();

        switch (command) {
            case "list":
                model.list();
                break;
            case "subtopics":
                if (args.length < 2) {
                    System.out.println("Need to write: Subtopics <topic>");
                } else {
                    model.listSubtopics(args[1]);
                }
                break;
            case "test":
                if (args.length < 3) {
                    System.out.println("Need to write: Test <topic> <subtopic>");
                } else {
                    model.test(args[1], args[2]);
                }
                break;
            default:
                System.out.println("Invalid command");
                break;
        }
    }
}
