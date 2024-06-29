package it.unibz.model;


import it.unibz.model.implementations.FileLoader;
import it.unibz.model.implementations.HistoryStatsLoader;
import it.unibz.model.implementations.Stats;
import it.unibz.model.implementations.Topic;
import it.unibz.utils.TopicUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static it.unibz.utils.StatsUtils.stats;
import static it.unibz.utils.TopicUtils.topic1_CSA_FL;
import static it.unibz.utils.TopicUtils.topic2_LA_FL;
import static org.junit.jupiter.api.Assertions.*;


public class FileLoaderTest {
    private final String inputBank = "src/test/resources/io/";
    @BeforeEach
    void init() {
        // QuestionUtils.init();
        // SubtopicUtils.init();
        TopicUtils.init();
    }
    @DisplayName("first I deserialize the original file, then I save the topic object into a file, then I deserialize it again" +
            "and it should be equal to the initial one")
    @Test
    public void loadSaveLoadFileTest() {
        try {
            Topic originalDes = FileLoader.loadFile(inputBank + "input_linear_algebra.json");
            assertEquals(topic2_LA_FL, originalDes);
            FileLoader.saveFile(topic2_LA_FL, inputBank + "input_linear_algebra.json");
            Topic deserializedTopic = FileLoader.loadFile(inputBank + "input_linear_algebra.json");
            assertEquals(topic2_LA_FL, deserializedTopic);
            assertEquals(originalDes, deserializedTopic);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @DisplayName("first I deserialize the original bank, then I save the bank object into a several files, then I deserialize them " +
            "and it should be equal to the initial one")
    @Test
    public void loadSaveLoadBankTest() {
        try {
            Set<Topic> originalDes = FileLoader.loadBank(inputBank);
            assertEquals(Set.of(topic1_CSA_FL, topic2_LA_FL), originalDes);
            FileLoader.saveBank(inputBank, List.of(topic1_CSA_FL, topic2_LA_FL));
            Set<Topic> deserializedBank = FileLoader.loadBank(inputBank);
            assertEquals(Set.of(topic1_CSA_FL, topic2_LA_FL), deserializedBank);
            assertEquals(originalDes, deserializedBank);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("loadFile(invalid file) should throw a IOException")
    @Test
    public void loadInvalidFile() {
        assertThrows(IOException.class, () -> FileLoader.loadFile("abc"));
    }

    @DisplayName("loadBank() should transform the whole input bank into Topic objects")
    @Test
    public void loadBank() {
        try {
            Set<Topic> producedBank = FileLoader.loadBank(inputBank);
            Set<Topic> expectedBank = new HashSet<>();
            expectedBank.add(topic1_CSA_FL);
            expectedBank.add(topic2_LA_FL);
            assertEquals(expectedBank, producedBank);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @DisplayName("loadBank(invalid bank) should throw a IOException")
    @Test
    public void loadInvalidBank() {
        assertThrows(IOException.class, () -> FileLoader.loadBank("abc"));
    }
}

