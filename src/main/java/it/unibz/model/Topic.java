package it.unibz.model;

import it.unibz.model.Subtopic;

import java.util.List;

public interface Topic {
    // methods
    /**
     *
     * @return some information about the topic, such as the name, the number of subtopics along with their names
     */
    String toString();

    /**
     *
     * @return the name of the topic
     */
    String getName();

    /**
     *
     * @return a list of subtopics
     */
    List<Subtopic> getSubtopics();

    /**
     *
     * @return the number of subtopics
     */
    int getNumberOfSubtopics();
}
