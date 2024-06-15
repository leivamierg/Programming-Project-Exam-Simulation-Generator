package it.unibz.model.interfaces;

import java.util.Set;

import it.unibz.model.implementations.Subtopic;
import it.unibz.model.implementations.Topic;

public interface TopicInt {
    // :p
    // setters
    /**
     * Sets the name attribute of the topic to a specific String
     * 
     * @param name the given name of the Topic
     */
    /* public */ void setTopicName(String topicName);

    /**
     * Sets the contained subtopics of the topic to a list of subtopis
     * 
     * @param subtopics list of the subtopics contained by the current Topic
     */
    /* public void setSubtopics(List<Subtopic> subtopics) */

    // getters
    String getTopicName();

    Set<Subtopic> getSubtopics();

    // toString and others
    public String toString();

    public void addSubTopic(Subtopic subtopic);// * */

    boolean equals(Topic topic);
}
