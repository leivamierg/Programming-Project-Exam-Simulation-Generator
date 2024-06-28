package it.unibz.model.implementations;

import java.util.Scanner;
import java.util.Set;

import it.unibz.controller.Controller;
import it.unibz.model.interfaces.ModelInt;

public class Model extends Simulation implements ModelInt {

    private String RESOURCES_PATH = System.getProperty("user.dir") + "/src/main/resources/";
    private Set<Topic> topics = null;
    private final int DURATION_SIMULATION = 60 * 30;
    private int remainingTime;

    public Model() {
        super();
        try {
            topics = FileLoader.loadBank(RESOURCES_PATH);
        } catch (Exception e) {
            System.out.println("We couldn't find any topics for the test. Please report this bug on GitHub.");
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
            System.out.println("The topic you are looking for doesn't exist.");
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
        Simulation simulation = new Simulation();
        simulation.select(selectedTopic, 1);
        simulation.start();

        int remainingTime = DURATION_SIMULATION;
        Scanner scanner = new Scanner(System.in);

        while (remainingTime > 0) {
            long questionStartTime = System.currentTimeMillis();

            clearConsole();
            System.out.println("Timer: " + formatTime(remainingTime));

            Question currentQuestion = simulation.getCurrentQuestion();
            System.out.println(currentQuestion.getQuestionAndAnswers());
            System.out.print("Select an answer: ");
            String input = scanner.nextLine().trim();

            long questionEndTime = System.currentTimeMillis();
            int timeSpentOnQuestion = (int) ((questionEndTime - questionStartTime) / 1000);
            remainingTime -= timeSpentOnQuestion;

            if (input.equals("") || remainingTime <= 0) {
                break;
            }

            simulation.answer(sanitizeAnswer(input));
        }

        System.out.println("Test completed.");
        scanner.close();

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

    private char sanitizeAnswer(String answer) {
        char res = Character.toUpperCase(answer.toUpperCase().charAt(0)) ;
        if (!Character.toString(res).matches("[A-D\\+\\-]")) {
            return 'n';
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

}
