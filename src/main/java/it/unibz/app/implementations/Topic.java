package it.unibz.app.implementations;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibz.app.TopicInt;

public class Topic implements TopicInt {

    private String topicName;
    private List<Subtopic> subtopics;

    @JsonCreator
    public Topic(@JsonProperty("topicName") String topicName, @JsonProperty("subtopics") List<Subtopic> subtopics) {
        setTopicName(topicName);
        setSubtopics(subtopics);

        for (Subtopic subtopic : subtopics) {
            subtopic.setTopicReference(this);
        }

    }

    // setters

    @Override
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    private void setSubtopics(List<Subtopic> subtopics) {
        this.subtopics = subtopics;
    }

    // getters
    @Override
    public String getTopicName() {
        return this.topicName;
    }

    @Override
    public List<Subtopic> getSubtopics() {
        return this.subtopics;
    }

    // other
    @Override
    public String toString() {
        return "Name: " + getTopicName() + System.lineSeparator() + "Nr. subtopics: " + getSubtopics().size();//
    }

    public void addSubTopic(Subtopic subtopic) {
        getSubtopics().add(subtopic);
    }
}
