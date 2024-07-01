package it.unibz.model.comparators;

import java.util.Comparator;

import it.unibz.model.interfaces.QuestionInt;

public class QuestionPriorityComparator implements Comparator<QuestionInt> {
    @Override
    public int compare(QuestionInt q1, QuestionInt q2) {
        return Integer.compare(q2.getPriorityLevel(), q1.getPriorityLevel());
    }
}
