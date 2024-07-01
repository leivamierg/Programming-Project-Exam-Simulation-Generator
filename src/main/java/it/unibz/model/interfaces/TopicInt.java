package it.unibz.model.interfaces;

import java.util.Set;
import it.unibz.model.implementations.Subtopic;

/**
 * This interface represents a topic.
 */
public interface TopicInt {

    /**
     * Returns the name of the topic.
     * 
     * @return the name of the topic
     */
    String getTopicName();

    /**
     * Returns the set of subtopics associated with the topic.
     * 
     * @return the set of subtopics
     */
    Set<Subtopic> getSubtopics();

    /**
     * Adds the given subtopic to the set of subtopics of the topic.
     * 
     * @param subtopic the subtopic to be added
     */
    void addSubTopic(Subtopic subtopic);

    /**
     * Returns a string representation of the topic.
     * 
     * @return a string representation of the topic
     */
    String toString();

}
