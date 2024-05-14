package it.unibz.model;

import java.util.List;

public interface FileLoaderInt {
    // methods
    /**
     * loads all files in the given bank
     * @param bankName path to the bank you want to load files from
     */
    List<Topic> loadBank(String bankName);
    /**
     * loads an input bank file -> transforms the input file into a Topic object
     * @param fileName path to the bank file you want to load
     * @return a Topic object that corresponds to the input file
     */
    Topic loadFile(String fileName);

    /**
     *
     * @return a list of all available topics for the user
     */
    List<Topic> getTopics();

}
