package it.unibz.model.interfaces;

import it.unibz.model.implementations.*;

import java.util.List;
import java.util.Set;
import java.util.Map;

public interface SimulationInt {

    /**
     *
     * @param question the current question
     * @return true if the user answered correctly, otherwise false
     * @throws NullPointerException if the given question isn't in this sim
     */
    boolean isCorrect(Question question);

    /**
     * add the questions for this topic to the map questions
     * @param topic selected topic
     * @param nrQuestionsPerSubtopic number of questions per subtopic
     * @throws NullPointerException if the given topic doesn't exist
     */

    void select(Topic topic, int nrQuestionsPerSubtopic);

    /**
     * add the questions for these subtopic to the map questions
     * @param subtopics selected subtopics
     * @param nrQuestionsPerSubtopic number of questions per subtopic
     * @throws NullPointerException if subtopics is null
     * @throws IllegalStateException if subtopics is empty
     * @throws IllegalArgumentException if at least one of the subtopic has a different topic
     */
    void select(Set<Subtopic> subtopics, int nrQuestionsPerSubtopic);

    /**
     * starts the sim, thus initialize the current question to the first one
     */
    void start();

    /**
     * it allows to answer to or change a question
     * @param command the input command (A, B, C, D, ' ', +, -, questionIdx)
     * @throws IllegalArgumentException if the command is invalid
     */
    void insertCommand(char command) throws IllegalArgumentException;

    /**
     * answers to the the given question -> only for debug purposes
     * @param answer the given answer (A, B, C, D, - -> no answer)
     * @param question the question you want to answer to
     * @throws IllegalArgumentException if the answer is invalid
     */

    void answer(Question question, char answer);


    /**
     * terminates the simulation, compute the stats, updates all parameters for the next simulation
     * @param stats the stats object needed to update the stats once the sim has terminated
     * @return a string containing the result of the sim
     */
    String terminate(Stats stats);

    /**
     *
     * @param subtopic the input subtopic
     * @return a set of correct questions for the input subtopic
     */

    Set<Question> getSubtopicCorrectQuestions(Subtopic subtopic);

    /**
     *
     * @param subtopic the input subtopic
     * @return a set of wrong questions for the input subtopic
     */

    Set<Question> getSubtopicWrongQuestions(Subtopic subtopic);
     /**
     *
     * @param subtopic the input subtopic
     * @return a set of blank questions for the input subtopic
     */

    Set<Question> getSubtopicBlankQuestions(Subtopic subtopic);

    /**
     *
     * @return a set of wrong questions for the whole sim
     */

    Set<Question> getAllWrongQuestions();

    /**
     *
     * @return a set of correct questions for the whole sim
     */

    Set<Question> getAllCorrectQuestions();
    /**
     *
     * @return a set of blank questions for the whole sim
     */

    Set<Question> getAllBlankQuestions();

    /**
     *
     * @return a set of non-selected question for the sim
     */

    Set<Question> getNonSelectedQuestions();

    /**
     *
     * @return a set containing both selected and non-selected questions for this sim
     */

    Set<Question> getAllSelected_NonSelectedQuestions();

    /**
     *
     * @param subtopic the input subtopic
     * @return a set containing both selected and non-selected questions for the given subtopic
     */

    Set<Question> getSubtopicSelected_NonSelectedQuestions(Subtopic subtopic);

    /**
     *
     * @param subtopic the subtopic you want to compute the stats on
     * @return a Score record which stores the number of correct, wrong, blank, selected, total questions
     * and the percentage of the given subtopic on the selected questions
     * @throws IllegalArgumentException if the given subtopic doesn't exist
     */

    Score computeSubtopicStats(Subtopic subtopic) throws IllegalArgumentException;

    /**
     *
     @return a Score record which stores the number of correct, wrong, blank, selected, total questions
      * and the percentage of the given subtopic on the selected questions
      */

    Score computeSimStats();

    /**
     *
     * @return a list of all questions (without the subtopic they belong to)
     */
    List<Question> getAllQuestions();

    /**
     *
     * @return a map with all questions for each subtopic
     */
    Map<Subtopic, Set<Question>> getSubtopicToQuestions();

    /**
     *
     * @return the current question
     */

    Question getCurrentQuestion();
    /**
     *
     * @return the reference to the topic the sim is about
     */

    Topic getTopicReference();

    /**
     *
     * @return a map that stores the answer of every question
     */
    Map<Question, Character> getQuestionToAnswer();

    /**
     *
     * @return a map mapping every question to its shuffled answers label (A, B, C, D)
     */
    Map<Question, Map<String, Character>> getQuestionToShuffledAnswers();

    /**
     *
     * @return the name of the topic the sim is about
     */

    String getTopic();
}
