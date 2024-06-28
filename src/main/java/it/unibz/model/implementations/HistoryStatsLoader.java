package it.unibz.model.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class HistoryStatsLoader {
    /**
     * loads the stats -> transforms the input file into a Stats object ->
     * deserialization
     * 
     * @param filePath path to the file that stores the stats
     * @return a Stats object that corresponds to the input file
     * @throws IOException if the input file doesn't exist
     */
    public static Stats loadStats(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
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
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(filePath), stats);
    }

    // TODO:
    public static History loadHistory(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.readValue(new File(filePath), History.class);
    }

    public static void saveHistory(String filePath, History history) throws IOException, IllegalArgumentException {
        if (history == null) {
            throw new IllegalArgumentException();
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(filePath), history);
    }
}
// }
