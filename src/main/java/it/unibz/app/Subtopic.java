package it.unibz.app;

import java.util.List;

public interface Subtopic {
    // setters
    /**
     * 
     * @param name the name attribute of current the Subtopic
     */
    void setName(String name);

    /**
     * 
     * @param topic the Topic object containing the current Subtopic(reference)
     */
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
    String getName();

    Topic getTopicReference();

    String getTopic();

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
}
