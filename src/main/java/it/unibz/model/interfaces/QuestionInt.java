package it.unibz.model.interfaces;

import it.unibz.model.implementations.Topic;

import java.util.Map;
import java.util.Set;

public interface QuestionInt {
    // setters

    /**
     * 
     * @param priorityLevel integer which denotes how likely is the question to be
     *                      picked on the next simulation
     */
    void setPriorityLevel(int priorityLevel);

    String getSubtopic();

    String getQuestionStatement();

    String getRightAnswer();

    Set<String> getWrongAnswers();

    int getPriorityLevel();

    /**
     * Returns a map containing the three possible answers to the question with a
     * character from A to D, chosen randomly
     *
     * @return a map where the keys are the shuffled options and the values are the
     *         corresponding characters.
     */
    Map<String, Character> getShuffleMap();

    /**
     * set the subtopic reference of the question
     * 
     * @param topics all the current loaded topics
     */
    void linkQuestionToSubtopic(Set<Topic> topics);

    String toString();

    /**
     * Returns the character linked to the correct answer, no matter how the map was
     * shuffled
     * 
     * @param shuffleMap a shufflemap generated through the getShuffleMap method
     * @return the caracter linked to the correct answer
     */

    public char getCorrectAnswerLabel(Map<String, Character> shuffleMap);

    /**
     * Returns the string containing the different answers with it corresponding
     * characters, obtained through the shufflemap method
     * 
     * @return (A-D) + string of possible answers
     */
    public String getQuestionAndAnswers();

}
