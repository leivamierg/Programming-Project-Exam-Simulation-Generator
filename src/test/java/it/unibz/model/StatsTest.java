package it.unibz.model;

import it.unibz.model.implementations.Score;
import it.unibz.model.implementations.Stats;
import it.unibz.utils.SimulationUtils;
import static it.unibz.utils.SimulationUtils.*;
import static it.unibz.utils.SubtopicUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsTest {
        private final Stats stats = new Stats();
        private final Map<String, List<Score>> expectedTopicToStats = new HashMap<>();
        private final Map<String, List<Score>> expectedSubtopicToStats = new HashMap<>();
        private Score expectedGeneralStats;

        @BeforeEach
        void init() {
                SimulationUtils.init();
                // SubtopicUtils.init();
                // QuestionUtils.init();
        }

        private void check() {
                assertEquals(expectedTopicToStats, stats.getTopicToStats());
                assertEquals(expectedSubtopicToStats, stats.getSubtopicToStats());
                assertEquals(expectedGeneralStats, stats.getGeneralStats());
        }

        private void updateStatsAfter1Sim() {
                // expectedTopicToStats
                expectedTopicToStats.put(simulationT1.getTopic(), List.of(new Score[] {
                                new Score(1, 4, 1, 6, 7, 16.66) }));
                // expectedSubtopicToStats
                expectedSubtopicToStats.put(subtopic1_1.getSubtopicName(), List.of(new Score[] {
                                new Score(1, 1, 0, 2, 2, 50) }));

                expectedSubtopicToStats.put(subtopic1_2.getSubtopicName(), List.of(new Score[] {
                                new Score(0, 2, 0, 2, 2, 0) }));

                expectedSubtopicToStats.put(subtopic1_3.getSubtopicName(), List.of(new Score[] {
                                new Score(0, 1, 1, 2, 3, 0) }));
                // expectedGeneralStats
                int idxLastStats = expectedTopicToStats.get(simulationT1.getTopic()).size() - 1;
                expectedGeneralStats = expectedTopicToStats.get(simulationT1.getTopic()).get(idxLastStats);
        }

        private void updateStatsAfter2Sim() {
                updateStatsAfter1Sim();
                // expectedTopicToStats
                List<Score> temp = new ArrayList<>(expectedTopicToStats.get(simulationT1.getTopic()));
                temp.add(new Score(2, 3, 1, 6, 7, 33.33));
                expectedTopicToStats.put(simulationT1.getTopic(), temp);
                // expectedSubtopicToStats
                List<Score> temp1_1 = new ArrayList<>(expectedSubtopicToStats.get(subtopic1_1.getSubtopicName()));
                temp1_1.add(new Score(1, 1, 0, 2, 2, 50));
                expectedSubtopicToStats.put(subtopic1_1.getSubtopicName(), temp1_1);

                List<Score> temp1_2 = new ArrayList<>(expectedSubtopicToStats.get(subtopic1_2.getSubtopicName()));
                temp1_2.add(new Score(1, 1, 0, 2, 2, 50));
                expectedSubtopicToStats.put(subtopic1_2.getSubtopicName(), temp1_2);

                // expectedGeneralStats
                int idxLastStats = expectedTopicToStats.get(simulationT1.getTopic()).size() - 1;
                expectedGeneralStats = (expectedTopicToStats.get(simulationT1.getTopic()).get(idxLastStats));

        }

        @DisplayName("Check the stats after 1 simulation about topic 1")
        @Test
        void statsAfter1SimTest() {
                stats.updateStats(simulationT1);
                // expectedTopicToStats
                expectedTopicToStats.put(simulationT1.getTopic(), List.of(new Score[] {
                                new Score(1, 4, 1, 6, 7, 16.66) }));
                // expectedSubtopicToStats
                expectedSubtopicToStats.put(subtopic1_1.getSubtopicName(), List.of(new Score[] {
                                new Score(1, 1, 0, 2, 2, 50) }));

                expectedSubtopicToStats.put(subtopic1_2.getSubtopicName(), List.of(new Score[] {
                                new Score(0, 2, 0, 2, 2, 0) }));

                expectedSubtopicToStats.put(subtopic1_3.getSubtopicName(), List.of(new Score[] {
                                new Score(0, 1, 1, 2, 3, 0) }));
                // expectedGeneralStats
                int idxLastStats = expectedTopicToStats.get(simulationT1.getTopic()).size() - 1;
                expectedGeneralStats = expectedTopicToStats.get(simulationT1.getTopic()).get(idxLastStats);

                check();
        }

        @DisplayName("Check the stats after 2 simulations: the first one about topic 1, " +
                        "the second one about subtopics 1.1 and 1.2")
        @Test
        void statsAfter2SimsTest() {
                // updateStatsAfter1Sim();
                statsAfter1SimTest();
                stats.updateStats(simulationT1_S1_S2);
                // expectedTopicToStats
                List<Score> temp = new ArrayList<>(expectedTopicToStats.get(simulationT1_S1_S2.getTopic()));
                temp.add(new Score(2, 3, 1, 6, 7, 33.33));
                expectedTopicToStats.put(simulationT1.getTopic(), temp);
                // expectedSubtopicToStats
                List<Score> temp1_1 = new ArrayList<>(expectedSubtopicToStats.get(subtopic1_1.getSubtopicName()));
                temp1_1.add(new Score(1, 1, 0, 2, 2, 50));
                expectedSubtopicToStats.put(subtopic1_1.getSubtopicName(), temp1_1);

                List<Score> temp1_2 = new ArrayList<>(expectedSubtopicToStats.get(subtopic1_2.getSubtopicName()));
                temp1_2.add(new Score(1, 1, 0, 2, 2, 50));
                expectedSubtopicToStats.put(subtopic1_2.getSubtopicName(), temp1_2);

                // expectedGeneralStats
                int idxLastStats = expectedTopicToStats.get(simulationT1_S1_S2.getTopic()).size() - 1;
                expectedGeneralStats = expectedTopicToStats.get(simulationT1.getTopic()).get(idxLastStats);

                check();
        }

        @DisplayName("Check the stats after 3 simulations: the first one about topic 1, " +
                        "the second one about subtopics 1.1 and 1.2, " +
                        "the third one about topic 2")
        @Test
        void statsAfter3SimsTest() {
                // updateStatsAfter2Sim();
                statsAfter2SimsTest();
                stats.updateStats(simulationT2);
                // expectedTopicToStats
                expectedTopicToStats.put(simulationT2.getTopic(), List.of(new Score[] {
                                new Score(3, 4, 1, 8, 10, 37.5) }));
                // expectedSubtopicToStats
                expectedSubtopicToStats.put(subtopic2_1.getSubtopicName(), List.of(new Score[] {
                                new Score(1, 1, 0, 2, 3, 50) }));

                expectedSubtopicToStats.put(subtopic2_2.getSubtopicName(), List.of(new Score[] {
                                new Score(2, 0, 0, 2, 2, 100) }));

                expectedSubtopicToStats.put(subtopic2_3.getSubtopicName(), List.of(new Score[] {
                                new Score(0, 2, 0, 2, 3, 0) }));

                expectedSubtopicToStats.put(subtopic2_4.getSubtopicName(), List.of(new Score[] {
                                new Score(0, 1, 1, 2, 2, 0) }));

                // expectedGeneralStats
                expectedGeneralStats = new Score(5, 7, 2, 14, 17, 35.71);

                check();
        }

        @DisplayName("Show general stats after 3 sims")
        @Test
        public void showStatsTest() {
                statsAfter3SimsTest();
                System.out.println(stats.showStats());
        }
}
