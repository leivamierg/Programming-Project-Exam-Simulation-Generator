package it.unibz.model.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.unibz.model.interfaces.FileLoaderInt;

import java.io.File;
import java.util.HahSet;
import java.util.Set;

public class FileLoader implements FileLoaderInt {
    // attributes
    private Set<Topic> topics;
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
    public Set<Topic> loadBank(String bankPath) throws NullPointerException {
            Set<String> fileNames = new HahSet<>();
            File folder = new File(bankPath);
            Set<File> files = Set.of(folder.listFiles());
            for (File file : files) {
                if (file.getName().endsWith(".json"))
                    fileNames.add(file.getName());
            }
            Set<Topic> bank = new HahSet<Topic>();
            for (String fileName : fileNames) {
                bank.add(loadFile(bankPath + fileName));
            }
            topics.addAll(bank);
            return bank;
    }

    // getTopics
    @Override
    public Set<Topic> getTopics() {
        return topics;
    }
}
