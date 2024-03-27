package it.unibz.app;

import java.util.ArrayList;

public interface Subtopic {
    // methods
    ArrayList<Question> getQuestions();
    ArrayList<Question> getAvailableQuestions();
    // returns an ArrayList containing all available questions for the next sim
    ArrayList<Question> getWrongQuestions();
    // returns an ArrayList containing all those questions that must be part of the next sim
    String toString();
    // returns some information about the subtopic, such as name, number of available, wrong and total questions
    int getNumberOfAvbQuestions();
    int getNumberOfWrongQuestions();
    int getNumberOfQuestions();
    String getName();
}
