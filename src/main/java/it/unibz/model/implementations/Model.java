package it.unibz.model.implementations;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import it.unibz.model.interfaces.ModelInt;

public class Model implements ModelInt {

    private String RESOURCES_PATH = System.getProperty("user.dir") + "/src/main/resources/bank/";
    private Set<Topic> topics = null;

    private static Stats stats;
    private static History history;
    private final int DURATION_SIMULATION = 60 * 30;

    public Model() {

        try {
            stats = HistoryStatsLoader.loadStats("src/main/resources/h_s/stats.json");
        } catch (Exception e) {
            stats = new Stats();
        }

        try {
            history = HistoryStatsLoader.loadHistory("src/main/resources/h_s/history.json");
        } catch (Exception e) {
            history = new History();
        }

        try {
            topics = FileLoader.loadBank(RESOURCES_PATH);
        } catch (IOException e) {
            System.out.println("We couldn't find any topics for the test in " + RESOURCES_PATH);
            e.printStackTrace();
        }
    }

    @Override
    public void list() {
        for (Topic topic : this.topics)
            System.out.println(topic.getTopicName());
    }

    @Override
    public void listSubtopics(String topicString) {
        String stdTopic = topicString.toLowerCase().strip();
        Topic selectedTopic = topics.stream()
                .filter(t -> t.getTopicName().equalsIgnoreCase(stdTopic))
                .findFirst()
                .orElse(null);

        if (selectedTopic == null) {
            System.out.println("The topic you are looking for doesn't exist.");
            return;
        }

        for (Subtopic subtopic : selectedTopic.getSubtopics())
            System.out.println(subtopic.getSubtopicName());

    }

    @Override
    public void test(String topic, String subtopic) {
        Topic selectedTopic = topics.stream()
                .filter(t -> t.getTopicName().equalsIgnoreCase(topic.strip()))
                .findFirst()
                .orElse(null);

        if (selectedTopic == null) {
            System.out.println("Invalid command or the topic you are looking for doesn't exist.");
            return;
        }

        if (subtopic == null) {
            testAllSubtopics(selectedTopic);
            return;
        }

        Subtopic selectedSubtopic = selectedTopic.getSubtopics().stream()
                .filter(s -> s.getSubtopicName().equalsIgnoreCase(subtopic.strip()))
                .findFirst()
                .orElse(null);

        if (selectedSubtopic == null) {
            System.out.println("The subtopic you are looking for doesn't exist.");
            return;
        }

        testSubtopic(selectedSubtopic);
    }

    private void testAllSubtopics(Topic selectedTopic) {
        Simulation simulation = new Simulation(selectedTopic);

        Scanner scanner = new Scanner(System.in);
        int numberOfQuestionsSubtopic = 0;

        while (true) {
            try {
                System.out.println("How many questions per subtopic?");
                numberOfQuestionsSubtopic = scanner.nextInt();
                break;
            } catch (Exception e)
            {
                scanner.nextLine();
            }
        }

        scanner.nextLine();
        simulation.select(selectedTopic, numberOfQuestionsSubtopic);
        simulation.start();

        int remainingTime = DURATION_SIMULATION;

        while (remainingTime > 0) {
            long questionStartTime = System.currentTimeMillis();

            clearConsole();
            System.out.println("Timer: " + formatTime(remainingTime));

            Question currentQuestion = simulation.getCurrentQuestion();
            int indexCurrentQuestion = simulation.getAllQuestions().indexOf(currentQuestion) + 1;
            int totalNumberQuestions = simulation.getAllQuestions().size();

            System.out.println("Index question: " + indexCurrentQuestion + "/" + totalNumberQuestions);
            System.out.println("Previous answer: " + simulation.getAnswer(currentQuestion));
            System.out.println(currentQuestion.getQuestionAndAnswers());
            System.out.print("Select an answer: ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("terminate")) {
                System.out.println(simulation.terminate(stats, history));
                break;
            }

            long questionEndTime = System.currentTimeMillis();
            int timeSpentOnQuestion = (int) ((questionEndTime - questionStartTime) / 1000);
            remainingTime -= timeSpentOnQuestion;

            if (remainingTime <= 0) {
                break;
            }

            try {
                simulation.insertCommand(sanitizeAnswer(input));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }

        System.out.println("Test completed.");

    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private String formatTime(int seconds) {
        int mins = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }

    private void testSubtopic(Subtopic selectedSubtopic) {

    }

    private String sanitizeAnswer(String answer) {
        String res = answer.toUpperCase();
        if (!res.matches("[A-D\\d\\+\\-]")) {
            return "n";
        }

        return res;
    }

    @Override
    public void testSubtopics(String topic) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'testSubtopics'");
    }

    @Override
    public void transcribe(String path, boolean verbose) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'transcribe'");
    }

    @Override
    public void notes(String path, boolean verbose) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notes'");
    }

    public static Stats getLoadedStats()
    {
        return stats;
    }

    public static History getLoadedHistory()
    {
        return history;
    }
}
