package it.unibz.model;

import java.util.List;

public interface Question {

    // methods

    /**
     *
     * @return a string containing the question statement along with its answers in a random order
     */
    String toString();
    /**
     *
     * @return all the answers
     */
    public List<String> getAnswers();

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
