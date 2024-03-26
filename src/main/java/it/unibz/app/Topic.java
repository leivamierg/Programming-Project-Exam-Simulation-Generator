package it.unibz.app;

import java.util.ArrayList;

public interface Topic {
    // attributes
    String name = null;
    ArrayList<Subtopic> subtopics = null;

    // methods
    String toString();
    // returns some information about the topic, such as the name, the number of subtopics along with their names
    String getName();
    ArrayList<Subtopic> getSubtopics();
    int getNumberOfSubtopics();
}
