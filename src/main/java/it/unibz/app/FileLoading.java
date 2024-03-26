package it.unibz.app;

import java.io.File;
import java.util.ArrayList;

public interface FileLoading {
    // methods
    boolean loadFile(File file);
    // loads a file and returns false if it fails
    void load(ArrayList<File> files);
    // loads all files in the bank package
}
