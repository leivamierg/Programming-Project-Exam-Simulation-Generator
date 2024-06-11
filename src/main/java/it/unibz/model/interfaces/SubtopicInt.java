package it.unibz.model.interfaces;

import java.util.List;
import java.util.Set;

import it.unibz.model.implementations.Question;
import it.unibz.model.implementations.Topic;

public interface SubtopicInt {
    // setters
    // void setTopicReference(Topic topic);

    /**
     * 
     * @param topic name of the referenced topic
     */
    // void setTopic(String topic);

    /**
     * 
     * @param questions the list of questions that the current Subtopic contains
     */
    // void setQuestions(List<Question> questions);// TODO: private

    // getters
    String getTopic();

    // String getTopicName();

    List<Question> getQuestions();

    List<Question> getAvailableQuestions();// Questions whose priority variable is not 0

    // List<Question> getWrongAnsweredQuestions();//might delete it

    // other methods
    /**
     * 
     * @param numOfQuestions number of questions to be picked for the current
     *                       simulation
     * @return the list of the randomly chosen questions according to their priority
     * 
     * @throws IllegalArgumentException if number of questions is greater than the
     *                                  number of available ones
     */
    public List<Question> pickQuestions(int numOfQuestions);

    /**
     * Updates the availableQuestions attribute, containing Question objects with a
     * priority attribute which is not 0
     */
    // public void updateAvbQuestions(); TODO

    /**
     * Updates the availableQuestions attribute, containing Question objects with a
     * high priority
     */

    // public void updateWrongAnsweredQuestions();//

    public String toString();

    /**
     * Adds a question to the question list of the subtopic
     * 
     * @param question the question to be added
     */
    void addQuestion(Question question);

    /**
     * set the topic reference of the subtopic
     */
    void linkSubtopicToTopic();
}
