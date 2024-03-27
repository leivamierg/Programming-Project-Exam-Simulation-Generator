package it.unibz.model;

import java.util.ArrayList;

public interface FileLoading {
    // methods
    boolean loadFile(String fileName);
    // loads a file and returns false if it fails
    void load(ArrayList<String> files);
    // loads all files in the bank package
}
