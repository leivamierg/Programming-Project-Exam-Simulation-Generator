package it.unibz.model.implementations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HistoryStatsLoader {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * loads the stats -> transforms the input file into a Stats object ->
     * deserialization
     * 
     * @param filePath path to the file that stores the stats
     * @return a Stats object that corresponds to the input file
     * @throws IOException if the input file doesn't exist
     */
    public static Stats loadStats(String filePath) throws IOException {
        setMapper();
        return mapper.readValue(new File(filePath), Stats.class);
    }

    /**
     * save a Stats object into a json file -> serialization
     * 
     * @param filePath path to the file you want to store the stats
     * @param stats    the Stats object to be serialized
     * @throws IllegalArgumentException if the object stats is null
     * @throws IOException
     *
     */
    public static void saveStats(String filePath, Stats stats) throws IOException, IllegalArgumentException {
        if (stats == null)
            throw new IllegalArgumentException();
        
        Path pathToFile = Paths.get(filePath);
        Path directoryPath = pathToFile.getParent();

        // Ensure the directory exists
        if (directoryPath != null && Files.notExists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        
        // Ensure the file exists
        if (Files.notExists(pathToFile)) {
            Files.createFile(pathToFile);
        }
        
        setMapper();
        mapper.writeValue(new File(filePath), stats);
    }

    /**
     * loads the history -> transforms the input file into a History object -> deserialization
     *
     * @param filePath path to the file that stores the history
     * @return a history object that corresponds to the input file
     * @throws IOException if the input file doesn't exist
     */
    public static History loadHistory(String filePath) throws IOException {
        setMapper();
        return mapper.readValue(new File(filePath), History.class);
    }
    /**
     * save a History object into a json file -> serialization
     *
     * @param filePath path to the file you want to store the history
     * @param history    the History object to be serialized
     * @throws IllegalArgumentException if the object history is null
     * @throws IOException
     *
     */
    public static void saveHistory(String filePath, History history) throws IOException, IllegalArgumentException {
        if (history == null) {
            throw new IllegalArgumentException();
        }

        Path pathToFile = Paths.get(filePath);
        Path directoryPath = pathToFile.getParent();

        // Ensure the directory exists
        if (directoryPath != null && Files.notExists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        
        // Ensure the file exists
        if (Files.notExists(pathToFile)) {
            Files.createFile(pathToFile);
        }

        setMapper();
        mapper.writeValue(new File(filePath), history);
    }

    private static void setMapper() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
    }
}
// }
