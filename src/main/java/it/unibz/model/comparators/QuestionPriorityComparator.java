package it.unibz.model.comparators;

import java.util.Comparator;

import it.unibz.model.interfaces.QuestionInt;

/**
 * This class is a comparator for the priority of questions.
 */
public class QuestionPriorityComparator implements Comparator<QuestionInt> {
    /**
     * Compares the priority of two questions.
     * @param q1 the first question
     * @param q2 the second question
     */
    @Override
    public int compare(QuestionInt q1, QuestionInt q2) {
        return Integer.compare(q2.getPriorityLevel(), q1.getPriorityLevel());
    }
}
