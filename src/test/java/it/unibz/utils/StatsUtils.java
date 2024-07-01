package it.unibz.utils;

import it.unibz.model.implementations.Score;
import it.unibz.model.implementations.Simulation;
import it.unibz.model.implementations.Stats;
import it.unibz.model.implementations.Subtopic;

import static it.unibz.utils.SimulationUtils.*;
import static it.unibz.utils.SubtopicUtils.*;

import java.util.*;

public class StatsUtils {
    public static Stats stats;
    public static Stats nullStats;

    public static void init () {
        SimulationUtils.init();
        // simulations
        List<Simulation> simulations = List.of(new Simulation[]{simulationT1});
        // topicToStats
        Map<String, List<Score>> topicToStats = new HashMap<>();
        topicToStats.put(simulationT1.getTopicName(),
                List.of(new Score(1, 4, 1, 6, 7, 16.66)));
        // subtopicToStats
        Map<String, List<Score>> subtopicToStats = new HashMap<>();
        subtopicToStats.put(subtopic1_1.getSubtopicName(),
                List.of(new Score(1, 1, 0, 2, 2, 50)));

        subtopicToStats.put(subtopic1_2.getSubtopicName(),
                List.of(new Score[]{new Score(0,2, 0, 2, 2, 0)}));

        subtopicToStats.put(subtopic1_3.getSubtopicName(),
                List.of(new Score[]{new Score(0, 1, 1, 2, 3, 0)}));
        // generalStats
        Score generalStats = new Score(1, 4, 1, 6, 7, 16.66);
        stats = new Stats(simulations, topicToStats, subtopicToStats, generalStats);

        nullStats = null;
    }
}
