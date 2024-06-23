package it.unibz.model.implementations;

import java.io.IOException;
import java.util.Set;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'test'");
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
