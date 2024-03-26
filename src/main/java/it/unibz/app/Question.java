package it.unibz.app;

import java.util.ArrayList;

public interface Question {
    // attributes
    String question = null;
    ArrayList<String> answers = null;
    // by convention, the first answer is the correct one
    int appearencesNumber = 0;
    // the more a question appears, lower is its probability to be chosen
    boolean recentlyRightAnswered = false;
    // if a question was rightly answered in the last sim, it won't be available for the next sim
    // if it wasn't, it will be immediately asked in the next sim

    // methods
    String toString();
    // only the question along with its answer must be printed
    // the order in which the answers are printed is random
}
