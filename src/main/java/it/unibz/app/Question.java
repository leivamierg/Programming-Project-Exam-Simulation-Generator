package it.unibz.app;

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
    void setWrongAnswers(String[] wrongAnswers);

    /**
     * 
     * @param WasAnsweredRight a boolean value specifying if the question was
     *                         answered right or not
     *                         the last time it was shown on a simulation
     */
    void setWasAnsweredRight(boolean WasAnsweredRight);

    // getters

    Subtopic getSubtopic();

    String getQuestionStatement();

    String getRightAnswer();

    String[] getWrongAnswers();

    boolean getWasAnsweredRight();

    // other methods
    /**
     * Updates the priority value of the current Question object
     */
    void updatePriority();

    String toString();

}
