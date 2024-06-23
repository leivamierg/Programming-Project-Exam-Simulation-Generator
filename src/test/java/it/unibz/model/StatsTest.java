package it.unibz.model;

import it.unibz.model.implementations.Correct_Selected_TotalQuestionsAndPercentage;
import it.unibz.model.implementations.Stats;
import it.unibz.utils.SimulationUtils;
import static it.unibz.utils.SimulationUtils.*;
import static it.unibz.utils.SubtopicUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsTest {
    private final Stats stats = new Stats();

    @BeforeEach
    void init() {
        SimulationUtils.init();
        // SubtopicUtils.init();
        // QuestionUtils.init();
    }
    void check(Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> expectedTopicToStats,
               Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> expectedSubtopicToStats,
               Correct_Selected_TotalQuestionsAndPercentage expectedGeneralStats) {
        assertEquals(expectedTopicToStats, stats.getTopicToStats());
        assertEquals(expectedSubtopicToStats, stats.getSubtopicToStats());
        assertEquals(expectedGeneralStats, stats.updateGeneralStats());
    }

    @DisplayName("Check the stats after 1 simulation about topic 1")
    @Test
    void statsAfter1SimTest() {
        stats.updateStats(simulationT1);
        // expectedTopicToStats
        Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> expectedTopicToStats = new HashMap<>();
        expectedTopicToStats.put(simulationT1.getTopic(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(1, 6, 7, 16.6)}));
        // expectedSubtopicToStats
        Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> expectedSubtopicToStats = new HashMap<>();
        expectedSubtopicToStats.put(subtopic1_1.getSubtopicName(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(1, 2, 2, 50)}));

        expectedSubtopicToStats.put(subtopic1_2.getSubtopicName(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(0, 2, 2, 0)}));

        expectedSubtopicToStats.put(subtopic1_3.getSubtopicName(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(0, 2, 3, 0)}));
        // expectedGeneralStats
        int idxLastStats = expectedTopicToStats.get(simulationT1.getTopic()).size() - 1;
        Correct_Selected_TotalQuestionsAndPercentage expectedGeneralStats = expectedTopicToStats.get(
                simulationT1.getTopic()).get(idxLastStats);

        check(expectedTopicToStats, expectedSubtopicToStats, expectedGeneralStats);
    }

    @DisplayName("Check the stats after 2 simulations: the first one about topic 1, " +
            "the second one about subtopics 1.1 and 1.2")
    @Test
    void statsAfter2SimsTest() {
        stats.updateStats(simulationT1);
        stats.updateStats(simulationT1_S1_S2);
        // expectedTopicToStats
        Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> expectedTopicToStats = new HashMap<>();
        expectedTopicToStats.put(simulationT1.getTopic(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(2, 6, 7, 33.3)}));
        // expectedSubtopicToStats
        Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> expectedSubtopicToStats = new HashMap<>();
        expectedSubtopicToStats.put(subtopic1_1.getSubtopicName(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(1, 2, 2, 50)}));

        expectedSubtopicToStats.put(subtopic1_2.getSubtopicName(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(1, 2, 2, 50)}));

        expectedSubtopicToStats.put(subtopic1_3.getSubtopicName(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(0, 2, 3, 0)}));
        // expectedGeneralStats
        int idxLastStats = expectedTopicToStats.get(simulationT1.getTopic()).size() - 1;
        Correct_Selected_TotalQuestionsAndPercentage expectedGeneralStats = expectedTopicToStats.get(
                simulationT1.getTopic()).get(idxLastStats);

        check(expectedTopicToStats, expectedSubtopicToStats, expectedGeneralStats);
    }

    @DisplayName("Check the stats after 3 simulations: the first one about topic 1, " +
            "the second one about subtopics 1.1 and 1.2, " +
            "the third one about topic 2")
    @Test
    void statsAfter3SimsTest() {
        stats.updateStats(simulationT1);
        stats.updateStats(simulationT1_S1_S2);
        stats.updateStats(simulationT2);
        // expectedTopicToStats
        Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> expectedTopicToStats = new HashMap<>();
        expectedTopicToStats.put(simulationT1.getTopic(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(2, 6, 7, 33.3)}));

        expectedTopicToStats.put(simulationT2.getTopic(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(3, 8, 10, 37.5)}));
        // expectedSubtopicToStats
        Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> expectedSubtopicToStats = new HashMap<>();
        expectedSubtopicToStats.put(subtopic1_1.getSubtopicName(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(1, 2, 2, 50)}));

        expectedSubtopicToStats.put(subtopic1_2.getSubtopicName(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(1, 2, 2, 50)}));

        expectedSubtopicToStats.put(subtopic1_3.getSubtopicName(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(0, 2, 3, 0)}));


        expectedSubtopicToStats.put(subtopic2_1.getSubtopicName(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(1, 2, 3, 50)}));

        expectedSubtopicToStats.put(subtopic2_2.getSubtopicName(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(2, 2, 2, 100)}));

        expectedSubtopicToStats.put(subtopic2_3.getSubtopicName(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(0, 2, 3, 0)}));

        expectedSubtopicToStats.put(subtopic2_4.getSubtopicName(), List.of(new Correct_Selected_TotalQuestionsAndPercentage[]{
                new Correct_Selected_TotalQuestionsAndPercentage(0, 2, 2, 0)}));

        // expectedGeneralStats
        Correct_Selected_TotalQuestionsAndPercentage expectedGeneralStats = new Correct_Selected_TotalQuestionsAndPercentage(
                5, 14, 17, 35.7);

        check(expectedTopicToStats, expectedSubtopicToStats, expectedGeneralStats);
    }
}
