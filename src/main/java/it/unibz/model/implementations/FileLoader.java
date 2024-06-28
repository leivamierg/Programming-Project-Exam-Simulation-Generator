package it.unibz.model.implementations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileLoader {

    private static final Set<Topic> topics = new HashSet<>();
    private static final Map<String, List<String>> bankToFiles = new HashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * loads an input bank file -> transforms the input file into a Topic object -> deserialization
     * 
     * @param filePath path to the bank file you want to load
     * @return the Topic object that corresponds to the input file
     * @throws IOException if the input file doesn't exist
     */
    public static Topic loadFile(String filePath) throws IOException {
        setMapper();
        Topic topic = mapper.readValue(new File(filePath), Topic.class);
        topics.add(topic);
        bankToFiles.put(getBankFromFile(filePath), filePath)
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
     * loads all files in the given bank -> deserialization
     * 
     * @param bankPath path to the bank you want to load files from
     * @throws IOException if the given bank doesn't exist
     * @return the set of loaded topics
     */
    public static Set<Topic> loadBank(String bankPath) throws IOException {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(bankPath);
        if (!folder.exists()) {
            throw new IOException("Bank folder does not exist");
        }
        List<File> files = List.of(folder.listFiles());
        for (File file : files) {
            if (file.getName().endsWith(".json"))
                fileNames.add(file.getName());
        }
        bankToFiles.put(bankPath, fileNames);
        Set<Topic> bank = new HashSet<>();
        for (String fileName : fileNames) {
            bank.add(loadFile(bankPath + fileName));
        }
        return bank;
    }

    /**
     * save a topic into a json file -> serialization
     * @param topic the topic to serialize
     * @param jsonFilePath the path of the file where the topic is stored
     * @throws IllegalArgumentException if the json file doesn't exist or topic is null
     */
    public static void saveFile(Topic topic, String jsonFilePath) throws IllegalArgumentException {
        if (topic == null)
            throw new IllegalArgumentException();
        for (Subtopic subtopic : topic.getSubtopics()) {
            subtopic.setTopicReference(null);
            for (Question question : subtopic.getQuestions()) {
                question.setSubtopicReference(null);
            }
        }
        setMapper();
        try {
            if (correctFile(jsonFilePath, topic)) {
                mapper.writeValue(new File(jsonFilePath), topic);
            } else throw new IllegalArgumentException();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param bankPath the path to the bank where you want to save the topics
     * @param topics the list of topics you want to save
     * @throws IllegalArgumentException if the list of topics is null, empty or the topic names don't
     * match with the name of the files
     */

    public static void saveBank(String bankPath, List<Topic> topics) throws IllegalArgumentException {
        if (bankToFiles.get(bankPath) == null)
            throw new IllegalArgumentException();
        if (topics == null || topics.isEmpty())
            throw new IllegalArgumentException();
        List<String> fileNames = bankToFiles.get(bankPath);
        // if (fileNames.size() != topics.size())
           // throw new IllegalArgumentException();
        for (int i = 0; i < topics.size(); i++) {
                saveFile(topics.get(i), bankPath + fileNames.get(i));
        }
    }

    private static boolean correctFile(String fileName, Topic topic) {
        String[] temp = topic.getTopicName().split(" ");
        for (String word : temp) {
            if (!fileName.toLowerCase().contains(word.toLowerCase()))
                return false;
        }
        return true;
    }

    // getTopics
    /**
     * @return a set of all available topics for the user
     */
    public static Set<Topic> getTopics() {
        return topics;
    }

    private static void setMapper() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
    }

    private static String getBankFromFile(String filePath) {
        String[] temp = filePath.split("/");
        temp[temp.length - 1] = "";
        return String.join("/", temp);
    }
}
