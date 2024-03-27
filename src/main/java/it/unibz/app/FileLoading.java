package it.unibz.app;

import java.io.File;
import java.util.List;

public interface FileLoading {
    // methods

    /**
     * loads an input bank file
     * @param path path to the input bank file
     * @return true if it succeeds, false if it fails
     */
    boolean loadFile(String path);
    /**
     * loads all input bank files
     * @param files list of all input banks files
     */
    void load(List<String> files);
}
