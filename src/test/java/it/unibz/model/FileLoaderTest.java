package it.unibz.model;


import it.unibz.model.implementations.FileLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import static it.unibz.utils.TopicUtils.topic1_CSA_FL;
import static it.unibz.utils.TopicUtils.topic2_LA_FL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FileLoaderTest {
    private final String inputBank = "src/test/resources/";
    @DisplayName("loadFile(CSA bank) should transform the input json file into the Topic object CSA")
    @Test
    public void loadCSABank() {
        FileLoader fileLoader = new FileLoader();
        Topic producedTopic = fileLoader.loadFile(inputBank + "input_csa_bank_test.json");
        assertEquals(topic1_CSA_FL, producedTopic);
    }
    @DisplayName("loadFile(LA bank) should transform the input json file into the Topic object LA")
    @Test
    public void loadLABank() {
        FileLoader fileLoader = new FileLoader();
        Topic producedTopic = fileLoader.loadFile(inputBank + "input_la_bank_test.json");
        assertEquals(topic2_LA_FL, producedTopic);
    }

    @DisplayName("loadBank() should transform the whole input bank into Topic objects")
    @Test
    public void loadBank() {
        FileLoader fileLoader = new FileLoader();
        Set<Topic> producedBank = fileLoader.loadBank(inputBank);
        Set<Topic> expectedBank = new HashSet<>();
        expectedBank.add(topic1_CSA_FL);
        expectedBank.add(topic2_LA_FL);
        assertEquals(expectedBank, producedBank);
    }
    @DisplayName("loadBank(invalid bank) should throw a NullPointerException")
    @Test
    public void loadInvalidBank() {
        FileLoader fileLoader = new FileLoader();
        assertThrows(fileLoader.loadBank("abc"));
    }

}

