package it.unibz.controller;
import it.unibz.model.implementations.*;
import it.unibz.model.interfaces.ModelInt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    private ModelInt model;
    private static final Scanner scanner = new Scanner(System.in);
    private User user;
    private PdfGenerator pdfGenerator;

    public Controller(ModelInt model, User user) {
        this.model = model;
        this.user = user;
        this.pdfGenerator = new PdfGenerator();
    }

    public void elaborateArgs(String[] arguments) {
        if (arguments.length == 0) {
            System.out.println("Tester needs arguments to work. Pass as help to see the list of commands.");
            return;
        }

        String input = String.join(" ", arguments).strip();

        Pattern listTopicsPattern = Pattern.compile("^(-t|--topics)$");
        Pattern listSubtopicsPattern = Pattern.compile("^([A-Za-z\\s]+)\\s+(-s|--subtopics)$");
        Pattern startTestPattern = Pattern.compile("^([A-Za-z\\s]+)$");
        Pattern selectSubtopicsPattern = Pattern.compile("^([A-Za-z\\s]+)\\s+(--select)$");
        Pattern historyPattern = Pattern.compile("(?i)--history");
        Pattern statsPattern = Pattern.compile("(?i)--stats");
        Pattern compareTopicStatsPattern = Pattern.compile("^(?i)(topic)\\s+([A-Za-z\\s]+)\\s+(\\d+)\\s+(\\d+)\\s+--compareStats$");
        Pattern compareTopicStatsWithStartPattern = Pattern.compile("^(?i)(topic)\\s+([A-Za-z\\s]+)\\s+(\\d+)\\s+--compareStats$");
        Pattern compareSubtopicStatsWithStartPattern = Pattern.compile("^(?i)(subtopic)\\s+([A-Za-z\\s]+)\\s+(\\d+)\\s+--compareStats$");
        Pattern compareSubtopicStatsPattern = Pattern.compile("^(?i)(subtopic)\\s+([A-Za-z\\s]+)\\s+(\\d+)\\s+(\\d+)\\s+--compareStats$");
        Pattern showTopicStatsPattern = Pattern.compile("^(?i)(topic)\\s+([A-Za-z\\s]+)\\s+(\\d+)\\s+--showStats$");
        Pattern showSubtopicStatsPattern = Pattern.compile("^(?i)(subtopic)\\s+([A-Za-z\\s]+)\\s+(\\d+)\\s+--showStats$");
        Pattern startDailyChallengePattern = Pattern.compile("^(-d)$");
        Pattern showProfile = Pattern.compile("(?i)--profile");
        Pattern downloadPDF = Pattern.compile("^([A-Za-z\\s]+)\\s+(--download)$");

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
            String topic = matcher.group(1);
            model.testSubtopics(topic);
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
                System.out.println("The starting simulation number or the topic name is invalid!");
            }
        } else if ((matcher = compareSubtopicStatsWithStartPattern.matcher(input)).find()) {
            Subtopic subtopic = getSubtopicFromName(matcher.group(2));
            int start = Integer.parseInt(matcher.group(3));
            try {
                System.out.println(Model.getLoadedStats().compareStats(subtopic, start));
            } catch (Exception e) {
                System.out.println("The starting simulation number or the subtopic name is invalid!");
            }
        } else if ((startDailyChallengePattern.matcher(input)).find()) {
            String challengeDate = user.getChallengeDate();

            if(challengeDate == null || challengeDate.isEmpty())
            {
                user.setChallengeDate(LocalDate.now().toString());
                user.resetStreak();
                List<Question> questions = model.getRandomQuestions(5);
                DailyChallenge dailyChallenge = new DailyChallenge(questions, user, model);
                dailyChallenge.startDailyChallenge();
            } else {
                try {
                    LocalDate lastChallengeDate = LocalDate.parse(user.getChallengeDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                    if (lastChallengeDate.equals(LocalDate.now())) {
                        System.out.println("You've already completed the daily challenge for today. Please come back tomorrow.");
                        return;
                    } else {
                        List<Question> questions = model.getRandomQuestions(5);
                        DailyChallenge dailyChallenge = new DailyChallenge(questions, user, model);
                        dailyChallenge.startDailyChallenge();
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("No date parsed");
                    return;
                }
            }


        } else if((showProfile.matcher(input)).find()) {
            System.out.println(user.toString());
        } else if((matcher = downloadPDF.matcher(input)).find()) {
            String topicName = matcher.group(1);
            Topic topic = getTopicFromName(topicName);
            if (topic != null) {
                String jsonFilePath = "src/main/resources/bank/" + topicName.replaceAll("\\s+", "_") + ".json";
                String filePath = "src/main/resources/pdf/" + topicName.replaceAll("\\s+", "_") + ".pdf";
                pdfGenerator.transformJsonIntoPDF(jsonFilePath, filePath);
            } else {
                System.out.println("Invalid topic name.");
            }
        } else {
            System.out.println("Invalid command. Please check your input.");
        }
    }

    private Topic getTopicFromName(String name) {
        for (Topic topic : FileLoader.getTopics()) {
            if (name.equalsIgnoreCase(topic.getTopicName()))
                return topic;
        }
        return null;
    }

    private Subtopic getSubtopicFromName(String name) {
        for (Topic topic : FileLoader.getTopics()) {
            for (Subtopic subtopic : topic.getSubtopics()) {
                if (name.equalsIgnoreCase(subtopic.getSubtopicName()))
                    return subtopic;
            }
        }
        return null;
    }

}
