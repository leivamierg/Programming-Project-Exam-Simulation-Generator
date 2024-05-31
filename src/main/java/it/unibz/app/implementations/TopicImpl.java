package it.unibz.app.implementations;

import java.util.List;

import it.unibz.app.Subtopic;
import it.unibz.app.Topic;

public class TopicImpl implements Topic {

    private String name;
    private List<Subtopic> subtopics;

    public TopicImpl(String name, List<Subtopic> subtopics) {
        setName(name);
        setSubtopics(subtopics);
    }

    // setters

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSubtopics(List<Subtopic> subtopics) {
        this.subtopics = subtopics;
    }

    // getters
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Subtopic> getSubtopics() {
        return this.subtopics;
    }

    // other
    @Override
    public String toString() {
        return "Name: " + getName() + System.lineSeparator() + "Subtopics: " + getSubtopics();// might need to correct
                                                                                              // it
    }// TODO

    public void addSubTopic(Subtopic subtopic) {
        getSubtopics().add(subtopic);
    }
}
