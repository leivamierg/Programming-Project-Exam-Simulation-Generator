package it.unibz.model.interfaces;

import it.unibz.model.implementations.Question;
import it.unibz.model.implementations.Topic;

import java.util.Map;
import java.util.Set;

public interface QuestionInt {
    // setters
    /**
     * 
     * @param subtopic the name of the Subtopic object containing the current
     *                 Question
     */
    // void setSubtopic(String subtopic);

    /**
     * 
     * @param subtopic reference to the apparteining subtopic
     */
    // void setSubtopicReference(Subtopic subtopicReference);//

    /**
     * 
     * @param statement the String which can be understood as the question itself
     */
    // void setQuestionStatement(String statement);

    /**
     * 
     * @param rightAnswer the correct answer to the questionStatement
     */
    // void setRightAnswer(String rightAnswer);

    /**
     * 
     * @param wrongAnswers a 3 components array containing wrong answers to the
     *                     questionSatement
     */
    // void setWrongAnswers(List<String> wrongAnswers);

    /**
     * 
     * @param priorityLevel integer which denotes how likely is the question to be
     *                      picked on the next simulation
     */
    void setPriorityLevel(int priorityLevel);

    // getters

    String getSubtopic();

    String getQuestionStatement();

    String getRightAnswer();

    Set<String> getWrongAnswers();

    int getPriorityLevel();

    // other methods
    Map<String, Character> getShuffleMap();

    /**
     * set the subtopic reference of the question
     * 
     * @param topics all the current loaded topics
     */
    void linkQuestionToSubtopic(Set<Topic> topics);

    /**
     * @return a list of the 4 possible answers shuffled in a random order
     */

    String toString();

    boolean equals(Question question);

    // boolean equals(Question question);
}
