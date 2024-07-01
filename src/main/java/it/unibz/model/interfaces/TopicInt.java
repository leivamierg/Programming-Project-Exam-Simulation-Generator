package it.unibz.model.interfaces;

import java.util.Set;

import it.unibz.model.implementations.Subtopic;

public interface TopicInt {

    /**
     * Sets the name attribute of the topic to a specific String
     * 
     * @param topicName the given name of the Topic
     */
    public void setTopicName(String topicName);

    // getters
    String getTopicName();

    Set<Subtopic> getSubtopics();

    // toString and others
    public String toString();

    public void addSubTopic(Subtopic subtopic);// * */

}
