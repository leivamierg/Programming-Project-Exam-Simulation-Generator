package it.unibz.model;

import it.unibz.model.Question;

import java.util.List;

public interface Subtopic {
    // methods

    /**
     *
     * @return a list containing all the questions of the subtopic
     */
    List<Question> getQuestions();

    /**
     *
     * @return an list containing all available questions for the next sim
     */
    List<Question> getAvailableQuestions();

    /**
     *
     * @return a list containing all those questions that must be part of the next sim
     */
    List<Question> getWrongQuestions();

    /**
     *
     * @return some information about the subtopic, such as name, number of available, wrong and total questions
     */
    String toString();

    /**
     *
     * @return the number of available questions
     */
    int getNumberOfAvbQuestions();

    /**
     *
     * @return the number of wrong answered questions
     */
    int getNumberOfWrongQuestions();

    /**
     *
     * @return the number of questions
     */
    int getNumberOfQuestions();

    /**
     *
     * @return the name of the subtopic
     */
    String getName();
}
