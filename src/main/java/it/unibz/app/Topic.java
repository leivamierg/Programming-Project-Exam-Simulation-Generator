package it.unibz.app;

import java.util.List;

public interface Topic {
    // :p
    // setters
    /**
     * Sets the name attribute of the topic to a specific String
     * 
     * @param name the given name of the Topic
     */
    /* public */ void setName(String name);

    /**
     * Sets the contained subtopics of the topic to a list of subtopis
     * 
     * @param subtopics list of the subtopics contained by the current Topic
     */
    /* public void setSubtopics(List<Subtopic> subtopics);// TODO: private */

    // getters
    String getName();

    List<Subtopic> getSubtopics();

    // toString and others
    public String toString();

    public void addSubTopic(Subtopic subtopic);// * */
}
