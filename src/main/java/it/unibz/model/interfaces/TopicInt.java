package it.unibz.model.interfaces;

import java.util.Set;

import it.unibz.model.implementations.Subtopic;

public interface TopicInt {

    // getters
    String getTopicName();

    Set<Subtopic> getSubtopics();

    // toString and others
    public String toString();

    /**
     * It adds the given subtopic to the set of subtopics of the topic
     * 
     * @param subtopic subtopic to be added to the subtopics set of the Topic
     */
    public void addSubTopic(Subtopic subtopic);// * */

}
