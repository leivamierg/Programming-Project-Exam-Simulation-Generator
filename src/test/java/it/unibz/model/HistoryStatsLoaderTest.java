package it.unibz.model;

import it.unibz.model.implementations.FileLoader;
import it.unibz.model.implementations.HistoryStatsLoader;
import it.unibz.model.implementations.Stats;
import it.unibz.utils.StatsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static it.unibz.utils.StatsUtils.*;

import java.io.IOException;

import static it.unibz.utils.TopicUtils.topic1_CSA_FL;
import static org.junit.jupiter.api.Assertions.*;

public class HistoryStatsLoaderTest {
    private final String io = "src/test/resources/";

    @Nested
    class StatsTest {
        @BeforeEach
        void init() {
            // QuestionUtils.init();
            // SubtopicUtils.init();
            StatsUtils.init();
        }

        @DisplayName("first I save the stats object into a file, then I deserialize it " +
                "and it should be equal to the initial one")
        @Test
        public void serializeAndDeserializeStatsTest() {
            try {
                HistoryStatsLoader.saveStats(io + "serializedStats.json", stats);
                Stats deserializedStats = HistoryStatsLoader.loadStats(io + "serializedStats.json");
                assertEquals(stats, deserializedStats);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @DisplayName("loadStats(invalid path) should throw a IOException")
        @Test
        public void loadInvalidStatsPath() {
            assertThrows(IOException.class, () -> HistoryStatsLoader.loadStats("abc"));
        }

        @DisplayName("saveStats(null stats) should throw a IllegalArgumentException")
        @Test
        public void saveNullStats() {
            assertThrows(IllegalArgumentException.class,
                    () -> HistoryStatsLoader.saveStats(io + "serializedStats.json", nullStats));
        }
        /*
         * @DisplayName("loadFile(CSA bank) should transform the input json file into the Topic object CSA"
         * )
         * 
         * @Test
         * public void loadStats() {
         * try {
         * Topic producedTopic = FileLoader.loadFile(inputBank +
         * "input_computer_system_architecture.json");
         * // producedTopic.equals(topic1_CSA_FL);
         * assertTrue(topic1_CSA_FL.equals(producedTopic));
         * } catch (IOException e) {
         * throw new RuntimeException(e);
         * }
         * 
         * }
         */

        @Nested
        class HistoryTest {
            private final String io = "src/test/resources/";

            @BeforeEach
            void init() {
                // QuestionUtils.init();
                // SubtopicUtils.init();
                HistoryUtils.init();
            }
        }
    }
}
