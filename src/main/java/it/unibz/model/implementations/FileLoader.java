package it.unibz.model.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.unibz.model.interfaces.FileLoaderInt;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FileLoader implements FileLoaderInt {
    // attributes
    private static Set<Topic> topics;
    public FileLoader() {
        topics = new HashSet<>();
    }
    // methods
    // loadFile
    @Override
    public Topic loadFile(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        Topic topic = mapper.readValue(new File(fileName), Topic.class);
        topics.add(topic);
        for (Subtopic subtopic : topic.getSubtopics()) {
            subtopic.linkSubtopicToTopic(topics);
            for (Question question : subtopic.getQuestions()) {
                question.linkQuestionToSubtopic(topics);
            }
        }
        return topic;

    }
    // loadBank
    @Override
    public Set<Topic> loadBank(String bankPath) throws NullPointerException, IOException {
            Set<String> fileNames = new HashSet<>();
            File folder = new File(bankPath);
            Set<File> files = Set.of(folder.listFiles());
            for (File file : files) {
                if (file.getName().endsWith(".json"))
                    fileNames.add(file.getName());
            }
            Set<Topic> bank = new HashSet<>();
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
