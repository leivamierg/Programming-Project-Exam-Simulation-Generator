package it.unibz.model.implementations;

import java.io.IOException;

import it.unibz.model.interfaces.ModelInt;

public class Model extends Simulation implements ModelInt {

    private String RESOURCES_PATH = System.getProperty("user.dir") + "/src/main/resources/";

    public Model() {
        super();
    }


    @Override
    public void list() {
        try {
            FileLoader.loadBank(RESOURCES_PATH);
            for (Topic topic : FileLoader.getTopics()) {
                System.out.println(topic.getTopicName());
            }
        } catch (IOException e) {
            System.out.println("We couldn't find any topics for the test. Please report this bug on GitHub.");
        }
    }

    @Override
    public void listSubtopics(String topic) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listSubtopics'");
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
