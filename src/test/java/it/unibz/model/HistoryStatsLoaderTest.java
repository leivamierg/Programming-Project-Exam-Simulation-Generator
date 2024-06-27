package it.unibz.model;

import it.unibz.model.implementations.FileLoader;
import it.unibz.model.implementations.Topic;
import it.unibz.utils.StatsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static it.unibz.utils.TopicUtils.topic1_CSA_FL;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HistoryStatsLoaderTest {
    private final String io = "src/test/resources/io";
    private final String expected = "src/test/resources/expected";

    @BeforeEach
    void init() {
        // QuestionUtils.init();
        // SubtopicUtils.init();
        StatsUtils.init();
    }

    @DisplayName("saveStats(io, stats) should produce a json file equals to the expected one")
    @Test
    public void saveStats () {

    }
    @DisplayName("loadFile(CSA bank) should transform the input json file into the Topic object CSA")
    @Test
    public void loadStats() {
        try {
            Topic producedTopic = FileLoader.loadFile(inputBank + "io/input_csa_bank_test.json");
            // producedTopic.equals(topic1_CSA_FL);
            assertTrue(topic1_CSA_FL.equals(producedTopic));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
