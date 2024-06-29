package it.unibz.model;


import it.unibz.model.implementations.FileLoader;
import it.unibz.model.implementations.Topic;
import it.unibz.utils.TopicUtils;
import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Set;

import static it.unibz.utils.TopicUtils.*;
import static org.junit.jupiter.api.Assertions.*;


public class FileLoaderTest {
    private final String inputBank = "src/test/resources/io/";

    @BeforeEach
    void init() {
        // QuestionUtils.init();
        // SubtopicUtils.init();
        TopicUtils.init();
        try {
            FileChannel src1 = new FileInputStream(
                    "src/test/resources/original/input_linear_algebra.json").getChannel();
            FileChannel dest1 = new FileOutputStream(
                    "src/test/resources/io/input_linear_algebra.json").getChannel();
            dest1.transferFrom(src1, 0, src1.size());

            FileChannel src2 = new FileInputStream(
                    "src/test/resources/original/input_computer_system_architecture.json").getChannel();
            FileChannel dest2 = new FileOutputStream(
                    "src/test/resources/io/input_computer_system_architecture.json").getChannel();
            dest2.transferFrom(src2, 0, src2.size());
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    @Nested
    class SuccessfullyLoadAndSave {
        @DisplayName("first I deserialize the original file, then I save the topic object into a file, then I deserialize it again" +
                "and it should be equal to the initial one")
        @Test
        void loadSaveLoadFileTest() {
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
        void loadSaveLoadBankTest() {
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
    }

    @Nested
    class FailedLoadAndSaveFile {
        @BeforeEach
         void load() {
            try {
                FileLoader.loadFile(inputBank + "input_linear_algebra.json");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @DisplayName("loadFile(invalid path) should throw an IOException")
        @Test
        void loadInvalidFilePath() {
            assertThrows(IOException.class, () -> FileLoader.loadFile("abc"));
        }

        @DisplayName("saveFile(valid topic, invalid path) should throw an IllegalArgumentException")
        @Test
        void saveValidTopicInvalidFilePath() {
            assertThrows(IllegalArgumentException.class, () -> FileLoader.saveFile(topic1_CSA_FL, inputBank + "abc"));
        }

        @DisplayName("saveFile(invalid topic, valid path) should throw an IllegalArgumentException")
        @Test
        void saveInvalidTopicValidFilePath() {
            assertThrows(IllegalArgumentException.class, () -> FileLoader.saveFile(topic1, inputBank + "input_linear_algebra.json"));
        }

        @DisplayName("saveFile(null topic, valid path) should throw an IllegalArgumentException")
        @Test
        void saveNullTopicValidFilePath() {
            assertThrows(IllegalArgumentException.class, () -> FileLoader.saveFile(nullTopic, inputBank + "input_linear_algebra.json"));
        }
    }

    @Nested
    class FailedSaveBank {
        @BeforeEach
        void load () {
            try {
                FileLoader.loadBank(inputBank);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @DisplayName("saveBank(valid list of topics, invalid path) should throw an IllegalArgumentException")
        @Test
        void saveValidTopicsInvalidBankPath() {
            assertThrows(IllegalArgumentException.class, () -> FileLoader.saveBank("abc", List.of(topic1_CSA_FL, topic2_LA_FL)));
        }

        @DisplayName("saveBank(null list of topics, valid path) should throw an IllegalArgumentException")
        @Test
        void saveNullTopicsValidBankPath() {
            assertThrows(IllegalArgumentException.class, () -> FileLoader.saveBank(inputBank, null));
        }

        @DisplayName("saveBank(list of 3 topics, valid path) should throw an IllegalArgumentException")
        @Test
        void saveTooManyTopicsValidBankPath() {
            assertThrows(IllegalArgumentException.class, () -> FileLoader.saveBank(inputBank,
                    List.of(topic1_CSA_FL, topic2_LA_FL, topic1)));
        }

        @DisplayName("saveBank(list with one invalid topic, valid path) should throw an IllegalArgumentException")
        @Test
        void saveListWith1InvalidTopicValidBankPath() {
            assertThrows(IllegalArgumentException.class, () -> FileLoader.saveBank(inputBank,
                    List.of(topic1_CSA_FL, topic1)));
        }

    }
}

