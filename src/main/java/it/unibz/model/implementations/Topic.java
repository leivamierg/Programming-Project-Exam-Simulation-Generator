package it.unibz.model.implementations;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibz.model.interfaces.TopicInt;

public class Topic implements TopicInt {

    private String topicName;
    private Set<Subtopic> subtopics;

    @JsonCreator
    public Topic(@JsonProperty("topicName") String topicName, @JsonProperty("subtopics") Set<Subtopic> subtopics) {
        setTopicName(topicName);
        setSubtopics(subtopics);

    }

    // setters
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    private void setSubtopics(Set<Subtopic> subtopics) {
        this.subtopics = subtopics;
    }

    // getters
    @Override
    public String getTopicName() {
        return this.topicName;
    }

    @Override
    public Set<Subtopic> getSubtopics() {
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

    @Override
    public boolean equals(Object topic) {
        if (topic == null || !(topic instanceof Topic)) {
            return false;
        } else if (this == topic) {
            return true;
        }

        return (getTopicName().equals(((Topic) topic).getTopicName())
                && getSubtopics().equals(((Topic) topic).getSubtopics()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicName, subtopics);
    }
}
