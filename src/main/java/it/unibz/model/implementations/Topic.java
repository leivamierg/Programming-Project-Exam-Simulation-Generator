package it.unibz.model.implementations;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibz.model.interfaces.TopicInt;

/**
 * The Topic class represents a topic in the system.
 
 */
public class Topic implements TopicInt {

    private String topicName;
    private Set<Subtopic> subtopics;

    /**
     * Constructor for the Topic class.
     * 
     * @param topicName The name of the topic.
     * @param subtopics The set of subtopics associated with the topic.
     */
    @JsonCreator
    public Topic(@JsonProperty("topicName") String topicName, @JsonProperty("subtopics") Set<Subtopic> subtopics) {
        setTopicName(topicName);
        setSubtopics(subtopics);
    }

    /**
     * Sets the name of the topic.
     * 
     * @param topicName The name of the topic.
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    /**
     * Sets the set of subtopics associated with the topic.
     * 
     * @param subtopics The set of subtopics.
     */
    private void setSubtopics(Set<Subtopic> subtopics) {
        this.subtopics = subtopics;
    }

    // getters

    /**
     * Retrieves the name of the topic.
     * 
     * @return The name of the topic.
     */
    @Override
    public String getTopicName() {
        return this.topicName;
    }

    /**
     * Retrieves the set of subtopics associated with the topic.
     * 
     * @return The set of subtopics.
     */
    @Override
    public Set<Subtopic> getSubtopics() {
        return this.subtopics;
    }

    // other

    /**
     * Returns a string representation of the Topic object.
     * 
     * @return A string representation of the Topic object.
     */
    @Override
    public String toString() {
        return "Name: " + getTopicName() + System.lineSeparator() + "Nr. subtopics: " + getSubtopics().size();
    }

    /**
     * Adds a subtopic to the set of subtopics associated with the topic.
     * 
     * @param subtopic The subtopic to be added.
     */
    public void addSubTopic(Subtopic subtopic) {
        getSubtopics().add(subtopic);
    }

    /**
     * Checks if the given object is equal to this Topic object.
     * 
     * @param topic The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
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

    /**
     * Returns the hash code value for this Topic object.
     * 
     * @return The hash code value for this Topic object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(topicName, subtopics);
    }
}
