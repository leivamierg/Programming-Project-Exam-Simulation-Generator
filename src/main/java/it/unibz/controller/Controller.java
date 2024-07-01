package it.unibz.controller;

import it.unibz.model.implementations.FileLoader;
import it.unibz.model.implementations.Model;
import it.unibz.model.implementations.Subtopic;
import it.unibz.model.implementations.Topic;
import it.unibz.model.interfaces.HistoryInt;
import it.unibz.model.interfaces.ModelInt;
import it.unibz.model.interfaces.StatsInt;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    private ModelInt model;
    //private HistoryInt history;
    //private StatsInt stats;
    private static final Scanner scanner = new Scanner(System.in);

    public Controller(ModelInt model/*, HistoryInt history, StatsInt stats*/) {
        this.model = model;
        //this.history = history;
        //this.stats = stats;
    }

    public void elaborateArgs(String[] arguments) {
        if (arguments.length == 0) {
            System.out.println("Tester needs arguments to work. Pass as help to see the list of commands.");
            return;
        }

        String input = String.join(" ", arguments);
        input = input.strip();

        Pattern listTopicsPattern = Pattern.compile("^(-t|--topics)$");
        Pattern listSubtopicsPattern = Pattern.compile("^([A-Za-z\\s]+)\\s+(-s|--subtopics)$");
        Pattern startTestPattern = Pattern.compile("^([A-Za-z\\s]+)$");
        Pattern selectSubtopicsPattern = Pattern.compile("^([A-Za-z\\s]+)\\s+--select$");
        Pattern historyPattern = Pattern.compile("(?i)--history");
        Pattern statsPattern = Pattern.compile("(?i)--stats");
        Pattern compareTopicStatsPattern = Pattern.compile("^(?i)(topic)\\s+([A-Za-z\\s]+)\\s+(\\d+)\\s+(\\d+)\\s+--compareStats$");
        Pattern compareTopicStatsWithStartPattern = Pattern.compile("^(?i)(topic)\\s+([A-Za-z\\s]+)\\s+(\\d+)\\s+--compareStats$");
        Pattern compareSubtopicStatsWithStartPattern = Pattern.compile("^(?i)(subtopic)\\s+([A-Za-z\\s]+)\\s+(\\d+)\\s+--compareStats$");
        Pattern compareSubtopicStatsPattern = Pattern.compile("^(?i)(subtopic)\\s+([A-Za-z\\s]+)\\s+(\\d+)\\s+(\\d+)\\s+--compareStats$");
        Pattern showTopicStatsPattern = Pattern.compile("^(?i)(topic)\\s+([A-Za-z\\s]+)\\s+(\\d+)\\s+--showStats$");
        Pattern showSubtopicStatsPattern = Pattern.compile("^(?i)(subtopic)\\s+([A-Za-z\\s]+)\\s+(\\d+)\\s+--showStats$");


        Matcher matcher;

        if (listTopicsPattern.matcher(input).find()) {
            model.list();
        } else if ((matcher = listSubtopicsPattern.matcher(input)).find()) {
            String topic = matcher.group(1);
            model.listSubtopics(topic);
        } else if ((matcher = startTestPattern.matcher(input)).find()) {
            String topic = matcher.group(1);
            model.test(topic, null);
        } else if ((matcher = selectSubtopicsPattern.matcher(input)).find()) {
            // String topic = matcher.group(1);
            //Selection of subtopic from Model to  be implemented (not in the first version)
            System.out.println("Subtopic selection feature not implemented.");
        } else if ((historyPattern.matcher(input)).find()) {
            System.out.println(Model.getLoadedHistory().showHistory());
        } else if ((statsPattern.matcher(input)).find()) {
            System.out.println(Model.getLoadedStats().showGeneralStats());
        } else if ((matcher = compareTopicStatsPattern.matcher(input)).find()) {
            Topic topic = getTopicFromName(matcher.group(2));
            int start = Integer.parseInt(matcher.group(3));
            int end = Integer.parseInt(matcher.group(4));
            try {
                System.out.println(Model.getLoadedStats().compareStats(topic, start, end));
            } catch (Exception e) {
                System.out.println("The ending simulation number is smaller or equals the start one, or the topic name is invalid!");
            }
        } else if ((matcher = compareSubtopicStatsPattern.matcher(input)).find()) {
            Subtopic subtopic = getSubtopicFromName(matcher.group(2));
            int start = Integer.parseInt(matcher.group(3));
            int end = Integer.parseInt(matcher.group(4));
            try {
                System.out.println(Model.getLoadedStats().compareStats(subtopic, start, end));

            } catch (Exception e) {
                System.out.println("The ending simulation number is smaller or equals the start one, or the subtopic name is invalid!");
            }
        } else if ((matcher = showTopicStatsPattern.matcher(input)).find()) {
            Topic topic = getTopicFromName(matcher.group(2));
            int simNumber = Integer.parseInt(matcher.group(3));
            try {
                System.out.println(Model.getLoadedStats().showTopicStats(topic, simNumber));
            } catch (Exception e) {
                System.out.println("The simulation number or the topic name is invalid!");
            }
        } else if ((matcher = showSubtopicStatsPattern.matcher(input)).find()) {
            Subtopic subtopic = getSubtopicFromName(matcher.group(2));
            int simNumber = Integer.parseInt(matcher.group(3));
            try {
                System.out.println(Model.getLoadedStats().showSubtopicStats(subtopic, simNumber));

            } catch (Exception e) {
                System.out.println("The simulation number or the subtopic name is invalid!");
            }
        } else if ((matcher = compareTopicStatsWithStartPattern.matcher(input)).find()) {
            Topic topic = getTopicFromName(matcher.group(2));
            int start = Integer.parseInt(matcher.group(3));
            try {
                System.out.println(Model.getLoadedStats().compareStats(topic, start));

            } catch (Exception e) {
                System.out.println("The starting simulation number or the subtopic name is invalid!");
            }
        } else if ((matcher = compareSubtopicStatsWithStartPattern.matcher(input)).find()) {
            Subtopic subtopic = getSubtopicFromName(matcher.group(2));
            int start = Integer.parseInt(matcher.group(3));
            try {
                System.out.println(Model.getLoadedStats().compareStats(subtopic, start));

            } catch (Exception e) {
                System.out.println("The starting simulation number or the subtopic name is invalid!");
            }
        }
            else{
                System.out.println("Invalid command. Please check your input.");
            }
    }

    private Topic getTopicFromName (String name) {
        for (Topic topic : FileLoader.getTopics()) {
            if (name.toLowerCase().equals(topic.getTopicName().toLowerCase()))
                return topic;
        }
        return null;
    }
    private Subtopic getSubtopicFromName (String name) {
        for (Topic topic : FileLoader.getTopics()) {
            for (Subtopic subtopic : topic.getSubtopics()) {
                if (name.toLowerCase().equals(subtopic.getSubtopicName().toLowerCase()))
                    return subtopic;
            }

        }
        return null;
    }
    public static String takeInput(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}
