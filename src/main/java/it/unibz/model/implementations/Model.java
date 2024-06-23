package it.unibz.model.implementations;

import java.util.Set;

import it.unibz.controller.Controller;
import it.unibz.model.interfaces.ModelInt;

public class Model extends Simulation implements ModelInt {

    private String RESOURCES_PATH = System.getProperty("user.dir") + "/src/main/resources/";
    private Set<Topic> topics = null;

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

        String input = Controller.takeInput("Use '+' to go to the next question and '-' for the previous. Press Enter to start the test.");
        Question currentQuestion;

        do {
            currentQuestion = simulation.getCurrentQuestion();
            System.out.println(currentQuestion.getQuestionAndAnswers());
            input = Controller.takeInput("Select an answer:");
            simulation.answer(sanitizeAnswer(input));
        } while (!input.equals(""));


        

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
