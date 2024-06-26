package it.unibz.controller;

import it.unibz.model.interfaces.ModelInt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String input = String.join(" ", args);
        input = input.strip();

        Pattern listTopicsPattern = Pattern.compile("^tester\\s+-t|--topics$");
        Pattern listSubtopicsPattern = Pattern.compile("^tester\\s+([^\\s]+)\\s+-s|--subtopics$");
        Pattern startTestPattern = Pattern.compile("^tester\\s+([^\\s]+)$");
        Pattern selectSubtopicsPattern = Pattern.compile("^tester\\s+([^\\s]+)\\s+--select$");

        Matcher matcher;

        if ((matcher = listTopicsPattern.matcher(input)).find())
        {
            model.list();
        } else if ((matcher = listSubtopicsPattern.matcher(input)).find())
        {
            String topic = matcher.group(1);
            model.listSubtopics(topic);
        } else if ((matcher = startTestPattern.matcher(input)).find())
        {
            String topic = matcher.group(1);
            model.test(topic, null);
        } else if ((matcher = selectSubtopicsPattern.matcher(input)).find())
        {
            String topic = matcher.group(1);
            //Selection of subtopic from Model to  be implemented
            System.out.println("Subtopic selection feature not implemented.");
        } else {
            System.out.println("Invalid command. Please check your input.");
        }
    }

    public static String takeInput(String message) {
        System.out.print(message);

        if (System.console() == null) {
            System.out.println("No console available. Exiting.");
            System.exit(1);
        }

        return System.console().readLine();
    }
}
