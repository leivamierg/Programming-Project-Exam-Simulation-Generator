package it.unibz.model;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
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
        return mapper.readValue(new File(fileName), Topic.class);

    }
    // loadBank
    @Override
    public List<Topic> loadBank(String bankPath) throws NullPointerException {
            List<String> fileNames = new ArrayList<>();
            File folder = new File(bankPath);
            List<File> files = List.of(folder.listFiles());
            for (File file : files) {
                if (file.getName().endsWith(".json"))
                    fileNames.add(file.getName());
            }
            List<Topic> bank = new ArrayList<Topic>();
            for (String fileName : fileNames) {
                bank.add(loadFile(bankPath + fileName));
            }
            topics.addAll(bank);
            return bank;
    }

    // getTopics
    @Override
    public List<Topic> getTopics() {
        return topics;
    }
}
