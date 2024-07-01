package it.unibz.model.interfaces;

import java.util.Set;

import it.unibz.model.implementations.Question;
import it.unibz.model.implementations.Subtopic;
import it.unibz.model.implementations.Topic;

public interface SubtopicInt {
    // setters
    // void setTopicReference(Topic topic);

    /**
     */
    // void setTopic(String topic);


    // getters
    String getTopic();

    // String getTopicName();

    Set<Question> getQuestions();

    Set<Question> getAvailableQuestions();// Questions whose priority variable is not 0

    // List<Question> getWrongAnsweredQuestions();//might delete it

    // other methods
    /**
     * @param numOfQuestions number of questions to be picked for the current
     *                       simulation
     * @return the list of the randomly chosen questions according to their priority
     * @throws IllegalArgumentException if number of questions is greater than the
     *                                  number of available ones
     */
    public Set<Question> pickQuestions(int numOfQuestions);

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
     * 
     * @param topics all the current loaded topics
     */
    void linkSubtopicToTopic(Set<Topic> topics);

    // boolean equals(Subtopic subtopic);
}
