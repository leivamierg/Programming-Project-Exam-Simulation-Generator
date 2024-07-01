package it.unibz.model.interfaces;

import java.util.Set;
import it.unibz.model.implementations.Question;
import it.unibz.model.implementations.Topic;

/**
 * This interface represents a subtopic in a topic.
 */
public interface SubtopicInt {

    /**
     * Gets the topic of the subtopic.
     *
     * @return the topic of the subtopic
     */
    String getTopic();

    /**
     * Gets all the questions in the subtopic.
     *
     * @return a set of questions in the subtopic
     */
    Set<Question> getQuestions();

    /**
     * Gets the available questions in the subtopic.
     * A question is available if its priority is greater than 0.
     *
     * @return a set of available questions in the subtopic
     */
    Set<Question> getAvailableQuestions();

    /**
     * Chooses an arbitrary number of questions out of the available questions in the
     * current subtopic.
     *
     * @param numOfQuestions the number of questions to be picked for the current simulation
     * @return the set of randomly chosen questions according to their priority, it returns the questions with the highest priority level
     * @throws IllegalArgumentException if the number of questions is greater than the number of available ones
     */
    public Set<Question> pickQuestions(int numOfQuestions);

    /**
     * Returns a string representation of the subtopic.
     *
     * @return a string representation of the subtopic
     */
    public String toString();

    /**
     * Adds a question to the question list of the subtopic.
     *
     * @param question the question to be added
     */
    void addQuestion(Question question);

    /**
     * Sets the topic reference of the subtopic.
     *
     * @param topics all the current loaded topics
     */
    void linkSubtopicToTopic(Set<Topic> topics);
}
