package it.unibz.app;

import java.io.File;
import java.util.ArrayList;

public interface FileLoading {
    // methods
    boolean loadFile(String fileName, String delimiter);
    // loads a file and returns false if it fails
    void load(ArrayList<String> files, String delimiter);
    // loads all files in the bank package
}
