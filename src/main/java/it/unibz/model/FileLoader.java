package it.unibz.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader implements FileLoaderInt {
    // attributes
    private List<Topic> topics;
    // constructor
    public FileLoader() {
        topics = new ArrayList<Topic>();
    }
    // methods
    // loadFile
    @Override
    public Topic loadFile(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        Topic topic;
        try {
            topic = mapper.readValue(new File(fileName), Topic.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // loadBank
    @Override
    public List<Topic> loadBank(String bankPath) {
        try {
            List<String> fileNames = new ArrayList<>();

            File folder = new File(bankPath);

            for (File file : folder.listFiles()) {
                if (file.getName().endsWith(".json"))
                    fileNames.add(file.getName());
            }
            List<Topic> bank = new ArrayList<Topic>();
            for (String fileName : fileNames) {
                bank.add(loadFile(bankPath + fileName));
            }
            topics.addAll(bank);
            return bank;
        } catch (NullPointerException e) {
            return null;
        }
    }

    // getTopics
    @Override
    public List<Topic> getTopics() {
        return topics;
    }
}
