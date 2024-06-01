package it.unibz.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import static it.unibz.model.TopicUtils.topic1_CSA_FL;
import static it.unibz.model.TopicUtils.topic2_LA_FL;


/**
 * Unit test for FileLoader class.
 */
public class FileLoaderTest {
    private final String inputBank = "src/test/resources/";
    @Test
    public void checkLoadCSABank() {
        FileLoader fileLoader = new FileLoader();
        Topic producedTopic = fileLoader.loadFile(inputBank + "input_csa_bank_test.json");
        assertEquals(topic1_CSA_FL, producedTopic);
    }

    @Test
    public void checkLoadLABank() {
        FileLoader fileLoader = new FileLoader();
        Topic producedTopic = fileLoader.loadFile(inputBank + "input_la_bank_test.json");
        assertEquals(topic2_LA_FL, producedTopic);
    }

    @Test
    public void checkLoadBank() {
        FileLoader fileLoader = new FileLoader();
        List<Topic> producedBank = fileLoader.loadBank(inputBank);
        List<Topic> expectedBank = new ArrayList<>();
        expectedBank.add(topic1_CSA_FL);
        expectedBank.add(topic2_LA_FL);
        assertEquals(expectedBank, producedBank);
    }

}

