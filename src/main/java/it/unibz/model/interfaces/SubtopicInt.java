package it.unibz.model.interfaces;

import java.util.Set;

import it.unibz.model.implementations.Question;
import it.unibz.model.implementations.Topic;

public interface SubtopicInt {

    // getters
    String getTopic();

    Set<Question> getQuestions();

    Set<Question> getAvailableQuestions();

    // other methods
    /**
     * It chooses an arbitraty number of questions out of the available in the
     * current subtopic
     * A question is available if its priority is greater than 0.
     * 
     * @param numOfQuestions number of questions to be picked for the current
     *                       simulation
     * @return the list of the randomly chosen questions according to their
     *         priority, it returns the questions with the highest priority level
     * @throws IllegalArgumentException if number of questions is greater than the
     *                                  number of available ones
     */
    public Set<Question> pickQuestions(int numOfQuestions);

    public String toString();

    /**
     * Adds a question to the question list of the subtopic
     * 
     * @param question the question to be added
     */
    void addQuestion(Question question);

    /**
     * sets the topic reference of the subtopic
     * 
     * @param topics all the current loaded topics
     */
    void linkSubtopicToTopic(Set<Topic> topics);

    // boolean equals(Subtopic subtopic);
}
