package it.unibz.model.interfaces;


import it.unibz.model.implementations.Topic;

import java.io.IOException;
import java.util.Set;

public interface FileLoaderInt {
    // methods
    /**
     * loads all files in the given bank
     * @param bankName path to the bank you want to load files from
     * @throws NullPointerException if the given bank doesn't exist
     * @throws IOException
     * @return the set of loaded topics
     */
    Set<Topic> loadBank(String bankName) throws NullPointerException, IOException;
    /**
     * loads an input bank file -> transforms the input file into a Topic object
     * @param fileName path to the bank file you want to load
     * @return a Topic object that corresponds to the input file
     * @throws IOException if the input file doesn't exist
     */
    Topic loadFile(String fileName) throws IOException;

    /**
     *
     * @return a set of all available topics for the user
     */
    Set<Topic> getTopics();

}
