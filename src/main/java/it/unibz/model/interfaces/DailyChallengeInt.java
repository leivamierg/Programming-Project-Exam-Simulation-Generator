package it.unibz.model.interfaces;

import java.util.List;

public interface DailyChallengeInt {

    /**
     * Checks the user's answers and if they are correct
     *
     * @param userAnswers list of user's answers
     * @return true if the user inserted at least 3 correct answers
     */
    boolean checkAnswers(List<String> userAnswers);

    /**
     * It initiates the daily challenge with questions and processes the ansers.
     * It updates the streak and badge based on the outcome.
     */
    void startDailyChallenge();

}
