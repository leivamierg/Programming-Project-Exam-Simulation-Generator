package it.unibz.utils;

import java.util.ArrayList;
import java.util.List;

import it.unibz.model.implementations.History;
import it.unibz.model.implementations.TestRegister;

public class HistoryUtils {
    public static History nullHistory;
    public static History history;

    public static void init() {
        //
        List<TestRegister> testRegisters = new ArrayList<>();
        String[] randomSubtopics = { "math", "moreMath", "evenMoreMath" };

        testRegisters.add(new TestRegister(1, "22:30 out of 50:00", 6, 1, 4, 1, 16.66, "hardMath", randomSubtopics));

        history = new History(testRegisters);

        nullHistory = null;
    }
}
