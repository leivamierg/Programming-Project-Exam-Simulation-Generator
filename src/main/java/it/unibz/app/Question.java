package it.unibz.app;

import java.util.List;

public interface Question {
    // setters
    /**
     * 
     * @param subtopic the Subtopic object containing the current Question
     */
    void setSubtopic(Subtopic subtopic);

    /**
     * 
     * @param statement the String which can be understood as the question itself
     */
    void setQuestionStatement(String statement);

    /**
     * 
     * @param rightAnswer the correct answer to the questionStatement
     */
    void setRightAnswer(String rightAnswer);

    /**
     * 
     * @param wrongAnswers a 3 components array containing wrong answers to the
     *                     questionSatement
     */
    void setWrongAnswers(List<String> wrongAnswers);

    /**
     * 
     * @param WasAnsweredRight a boolean value specifying if the question was
     *                         answered right or not
     *                         the last time it was shown on a simulation
     * 
     *                         void setWasAnsweredRight(boolean WasAnsweredRight);
     */

    /**
     * 
     * @param priorityLevel integer which denotes how likely is the question to be
     *                      picked on the next simulation
     */
    void setPriorityLevel(int priorityLevel);

    // getters

    Subtopic getSubtopic();

    String getQuestionStatement();

    String getRightAnswer();

    List<String> getWrongAnswers();

    /* boolean getWasAnsweredRight();********* */

    int getPriorityLevel();

    // other methods
    List<String> shuffle();

    /**
     * @return a list of the 4 possible answers shuffled in a random order
     */

    String toString();

}
