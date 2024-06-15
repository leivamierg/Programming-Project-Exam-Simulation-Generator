package it.unibz.model.interfaces;

import it.unibz.model.implementations.*;

import it.unibz.model.implementations.CorrectAnswersAndPercentage;

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
     */
    void select(Set<Subtopic> subtopics, int nrQuestionsPerSubtopic);

    /**
     * starts the sim, thus initialize the current question to the first one
     */
    void start();

    /**
     * answers to the current question and moves to the next one
     * @param answer the given answer (A, B, C, D, - -> no answer)
     * @throws IllegalArgumentException if the answer is invalid
     */
    void answer(char answer);

    /**
     * moves either to the previous question or to the next one
     * @param prevOrNext '+' for the next question, '-' for the previous one
     * @throws IllegalArgumentException if the given input is neither '+' nor '-'
     */
    void changeQuestion(char prevOrNext);

    /**
     * moves to the desired question (if it exists)
     * @param idxQuestion index of the question you want to move to (from 1 to the total number of questions)
     * @throws IndexOutOfBoundsException if the given index doesn't exist
     */
    void changeQuestion(int idxQuestion);

    /**
     * terminates the simulation, compute the stats, updates all parameters for the next simulation
     * @return a string containing the result of the sim
     */
    String terminate();

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
     * @return a set of non-selected question for the sim
     */

    Set<Question> getNonSelectedQuestions();

    /**
     *
     * @param subtopic the subtopic you want to compute the stats on
     * @return a CorrectAnswersAndPercentage record which stores the number of correct answers and the percentage of the given subtopic
     * @throws IllegalArgumentException if the given subtopic doesn't exist
     */

    CorrectAnswersAndPercentage computeSubtopicStats(Subtopic subtopic) throws IllegalArgumentException;

    /**
     *
     * @return a CorrectAnswersAndPercentage record which stores the number of correct answers and the percentage of the whole sim
     */

    CorrectAnswersAndPercentage computeSimStats();

    /**
     *
     * @return a list of all questions (without the subtopic they belong to)
     */
    List<Question> getAllQuestions();

    /**
     *
     * @return a map with all questions for each subtopic
     */
    Map<Subtopic, Set<Question>> getQuestionsPerSubtopic();

    /**
     *
     * @return the current question
     */

    Question getCurrentQuestion();

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
}
