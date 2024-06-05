package it.unibz.app.comparators;

import java.util.Comparator;

import it.unibz.app.QuestionInt;

public class QuestionPriorityComparator implements Comparator<QuestionInt> {
    @Override
    public int compare(QuestionInt q1, QuestionInt q2) {
        return Integer.compare(q1.getPriorityLevel(), q2.getPriorityLevel());// might be wrong
    }
}
