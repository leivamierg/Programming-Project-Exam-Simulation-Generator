package it.unibz.model.interfaces;


import it.unibz.model.implementations.Topic;

import java.util.Set;

public interface FileLoaderInt {
    // methods
    /**
     * loads all files in the given bank
     * @param bankName path to the bank you want to load files from
     * @throws NullPointerException if the given bank doesn't exist
     */
    Set<Topic> loadBank(String bankName) throws NullPointerException;
    /**
     * loads an input bank file -> transforms the input file into a Topic object
     * @param fileName path to the bank file you want to load
     * @return a Topic object that corresponds to the input file
     */
    Topic loadFile(String fileName);

    /**
     *
     * @return a set of all available topics for the user
     */
    Set<Topic> getTopics();

}
