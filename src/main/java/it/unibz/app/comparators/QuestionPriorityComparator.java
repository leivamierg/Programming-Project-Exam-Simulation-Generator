package it.unibz.app.comparators;

import java.util.Comparator;

import it.unibz.app.Question;

public class QuestionPriorityComparator implements Comparator<Question> {
    @Override
    public int compare(Question q1, Question q2) {
        return Integer.compare(q1.getPriorityLevel(), q2.getPriorityLevel());// might be wrong
    }
}
