package it.unibz.model;

import java.util.List;

import it.unibz.app.Topic;

public interface FileLoadingInt {
    // methods
    /**
     * loads all files in the given bank
     * @param bankName path to the bank you want to load files from
     */
    public List<Topic> loadBank(String bankName);
    /**
     * loads an input bank file -> transforms the input file into a Topic object
     * @param fileName path to the bank file you want to load
     * @return a Topic object that corresponds to the input file
     */
    Topic loadFile(String fileName);

    /**
     * loads a subtopic of a given topic
     * @param fileName path to the input bank file that contains the subtopic
     * @param subtopicName name of the subtopic you want to load
     * @return a Subtopic object
     */
    Subtopic loadSubtopic(String fileName, String subtopicName);
    /**
     * loads a question of a given subtopic
     * @param fileName path to the input bank file that contains the question
     * @param subtopicName name of the subtopic that contains the question
     * @param questionStatement statement of the question you want to load
     * @return a Question object
     */
    Question loadQuestion (String fileName, String subtopicName, String questionStatement);

    /**
     *
     * @return a list of all available topics for the user
     */
    List<Topic> getTopics();

}
