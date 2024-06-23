package it.unibz.model.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FileLoader {
    // attributes
    private static final Set<Topic> topics = new HashSet<>();

    private FileLoader() {}
    // methods
    // loadFile
    /**
     * loads an input bank file -> transforms the input file into a Topic object
     * @param filePath path to the bank file you want to load
     * @return a Topic object that corresponds to the input file
     * @throws IOException if the input file doesn't exist
     */
    public static Topic loadFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        Topic topic = mapper.readValue(new File(filePath), Topic.class);
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
    /**
     * loads all files in the given bank
     * @param bankPath path to the bank you want to load files from
     * @throws NullPointerException if the given bank doesn't exist
     * @throws IOException
     * @return the set of loaded topics
     */
    public static Set<Topic> loadBank(String bankPath) throws NullPointerException, IOException {
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
    /**
     *
     * @return a set of all available topics for the user
     */
    public static Set<Topic> getTopics() {
        return topics;
    }
}
