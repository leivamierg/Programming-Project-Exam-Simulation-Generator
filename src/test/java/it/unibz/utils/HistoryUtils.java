package it.unibz.utils;

import static it.unibz.utils.SimulationUtils.simulationT1;

import java.util.List;

import it.unibz.model.implementations.History;
import it.unibz.model.implementations.Simulation;

public class HistoryUtils {
    public static History emptyHistory;
    public static History smallHistory;
    public static History mediumHistory;

    public static void init() {
        SimulationUtils.init();
        // simulation
        List<Simulation> simulations = List.of(new Simulation[] { simulationT1 });
        //

    }
}
