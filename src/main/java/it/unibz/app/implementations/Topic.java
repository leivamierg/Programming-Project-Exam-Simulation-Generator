package it.unibz.app.implementations;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibz.app.TopicInt;

public class Topic implements TopicInt {

    private String topicName;
    private Set<Subtopic> subtopics;

    @JsonCreator
    public Topic(@JsonProperty("topicName") String topicName, @JsonProperty("subtopics") Set<Subtopic> subtopics) {
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

    public boolean equals(Topic topic) {
        if (topic == null || topic.getClass() != getClass()) {
            return false;
        } else if (this == topic) {
            return true;
        }

        return (getTopicName().equals(topic.getTopicName()) && equalsSubtopics(topic.getSubtopics()));
    }

    private boolean equalsSubtopics(Set<Subtopic> subtopics) {
        if (getSubtopics().size() != subtopics.size() || subtopics == null) {
            return false;
        } else {
            return subtopics.containsAll(getSubtopics());
        }
    }

    public int hashCode() {
        return Objects.hash(topicName, subtopics);
    }
}
