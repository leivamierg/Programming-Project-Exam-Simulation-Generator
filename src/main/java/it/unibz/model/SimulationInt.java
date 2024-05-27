package it.unibz.model;

import java.util.List;
import java.util.Map;

public interface SimulationInt {

    /**
     *
     * @param question the current question
     * @return true if the user answered correctly, otherwise false
     */
    // boolean isCorrect(Question question);

    /**
     * add the questions for this topic to the map questions
     * @param topic selected topic
     * @param nrQuestionsPerSubtopic number of questions per subtopic
     */

    void select(Topic topic, int nrQuestionsPerSubtopic);

    /**
     * add the questions for these subtopic to the map questions
     * @param subtopics selected subtopics
     * @param nrQuestionsPerSubtopic number of questions per subtopic
     */
    void select(List<Subtopic> subtopics, int nrQuestionsPerSubtopic);

    /**
     * answers to the current question and moves to the next one
     * @param answer the given answer (A, B, C, D)
     */
    void answer(char answer);

    /**
     * moves either to the previous question or to the next one
     * @param prevOrNext '+' for the next question, '-' for the previous one
     */
    void changeQuestion(char prevOrNext);

    /**
     * moves to the desired question (if it exists)
     * @param idxQuestion index of the question you want to move to (from 1 to the total number of questions)
     */
    void changeQuestion(int idxQuestion);

    /**
     * terminates the simulation, prints the results and updates all parameters for the next simulation
     */
    void terminate();

    /**
     *
     * @return a map with all questions for each subtopic
     */
    Map<Subtopic, List<Question>> getQuestionsPerSubtopic();

    /**
     *
     * @return the current question
     */

    Question getCurrentQuestion();
}
