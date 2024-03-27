package it.unibz.model;

import java.util.ArrayList;

public interface Question {
    // methods
    String toString();
    // only the question along with its answer must be printed
    // the order in which the answers are printed is random

    /**
     * 
     * @return all the answers
     */
    public ArrayList<String> getAnswers();

    /**
     * 
     * @return the correct answer
     */
    public String getRightAnswer();

    /**
     * 
     * @return the question statement
     */
    public String getQuestion();


}
