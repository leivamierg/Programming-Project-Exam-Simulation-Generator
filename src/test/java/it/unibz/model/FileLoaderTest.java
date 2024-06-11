package it.unibz.model;


import it.unibz.model.implementations.FileLoader;
import it.unibz.model.implementations.Topic;
import it.unibz.utils.QuestionUtils;
import it.unibz.utils.SubtopicUtils;
import it.unibz.utils.TopicUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static it.unibz.utils.TopicUtils.topic1_CSA_FL;
import static it.unibz.utils.TopicUtils.topic2_LA_FL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FileLoaderTest {
    private final String inputBank = "src/test/resources/";
    FileLoader fileLoader = new FileLoader();
    @BeforeEach
    void init() {
        TopicUtils.init();
        SubtopicUtils.init();
    }
    @DisplayName("loadFile(CSA bank) should transform the input json file into the Topic object CSA")
    @Test
    public void loadCSABank() {
        try {
            Topic producedTopic = fileLoader.loadFile(inputBank + "input_csa_bank_test.json");
            assertEquals(topic1_CSA_FL, producedTopic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @DisplayName("loadFile(LA bank) should transform the input json file into the Topic object LA")
    @Test
    public void loadLABank() {
        try {
            Topic producedTopic = fileLoader.loadFile(inputBank + "input_la_bank_test.json");
            assertEquals(topic2_LA_FL, producedTopic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("loadFile(invalid file) should throw a IOException")
    @Test
    public void loadInvalidFile() {
        assertThrows(IOException.class, () -> fileLoader.loadFile("abc"));
    }

    @DisplayName("loadBank() should transform the whole input bank into Topic objects")
    @Test
    public void loadBank() {
        try {
            Set<Topic> producedBank = fileLoader.loadBank(inputBank);
            Set<Topic> expectedBank = new HashSet<>();
            expectedBank.add(topic1_CSA_FL);
            expectedBank.add(topic2_LA_FL);
            assertEquals(expectedBank, producedBank);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @DisplayName("loadBank(invalid bank) should throw a NullPointerException")
    @Test
    public void loadInvalidBank() {
        assertThrows(NullPointerException.class, () -> fileLoader.loadBank("abc"));
    }

}

