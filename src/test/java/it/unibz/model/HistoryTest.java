package it.unibz.model;

import static it.unibz.utils.SimulationUtils.simulationT1;
import static it.unibz.utils.SimulationUtils.simulationT1_S1_S2;
import static it.unibz.utils.SimulationUtils.simulationT2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.unibz.model.implementations.History;
import it.unibz.utils.SimulationUtils;

public class HistoryTest {
    private History history = new History();

    @BeforeEach
    void init() {
        SimulationUtils.init();
        // SubtopicUtils.init();
        // QuestionUtils.init();
    }

    @AfterEach
    private void showStats() {
        System.out.println(history.showHistory());
    }

    private void updateHistoryAfter1Sim() {
        history.updateHistory(simulationT1);
    }

    private void updateHistoryAfter2Sim() {
        updateHistoryAfter1Sim();
        history.updateHistory(simulationT1_S1_S2);
    }

    private void updateHistoryAfter3Sim() {
        updateHistoryAfter2Sim();
        history.updateHistory(simulationT2);
    }

    @DisplayName("Check the the history after 1 simulation")
    @Test
    void historyAfter1SimTest() {
        updateHistoryAfter1Sim();
    }

    @DisplayName("Check the the history after 2 simulations")
    @Test
    void historyAfter2SimTest() {
        updateHistoryAfter2Sim();
    }

    @DisplayName("Check the the history after 3 simulations")
    @Test
    void historyAfter3SimTest() {
        updateHistoryAfter3Sim();
    }

}
