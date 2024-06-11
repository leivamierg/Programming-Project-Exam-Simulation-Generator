package it.unibz.model.implementations;

import it.unibz.model.comparators.QuestionPriorityComparator;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibz.model.interfaces.SubtopicInt;

public class Subtopic implements SubtopicInt {
    private String subtopicName;
    private String topic;
    private Topic topicReference;
    private List<Question> questions;

    @JsonCreator
    public Subtopic(@JsonProperty("subtopicName") String subtopicName,
            @JsonProperty("questions") List<Question> questions, @JsonProperty("topic") String topic) {
        setSubtopicName(subtopicName);
        // setTopic(topic);TODO: add a loop in the Topic class
        setQuestions(questions);
        setTopic(topic);

        /*for (Question question : questions) {
            question.setSubtopic(this);
        }*/

    }

    // setters


    public void setSubtopicName(String subtopicName) {
        this.subtopicName = subtopicName;
    }

    void setTopic(String topic) {
        this.topic = topic;
    }

    private void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    private void setTopicReference(Topic topicReference) {
        this.topicReference = topicReference;
    }
    // getters


    public String getSubtopicName() {
        return subtopicName;
    }

    public String getTopic() {
        return this.topic;
    }

    /*
     * public String getTopicName() {
     * return this.topicName;
     * }
     */

    public List<Question> getQuestions() {
        return this.questions;
    }

    public Topic getTopicReference() {
        return topicReference;
    }

    // toString and others
    public void linkSubtopicToTopic() {
        FileLoader fileLoader = new FileLoader();
        Set<Topic> allTopics = fileLoader.getTopics();
        Optional<Topic> correctSubtopic = allTopics.stream().
                filter(s -> s.getTopicName().equals(topic)).
                findFirst();
        if (correctSubtopic.isPresent())
            setTopicReference(correctSubtopic.get());
    }
    public String toString() {
        return "Subtopic: " + getSubtopicName() + System.lineSeparator() + "Topic: "
                + getTopicReference().getTopicName()
                + System.lineSeparator() + "Nr. questions: " + getQuestions().size();
    }

    public List<Question> getAvailableQuestions() {
        return getQuestions().stream().filter((q) -> (q.getPriorityLevel() > 0)).toList();
    }

    public List<Question> pickQuestions(int n) throws IllegalArgumentException {
        if (n > getAvailableQuestions().size()) {
            System.err.println(
                    "Attention: the number of questions requested is greater than the number of available question");
            System.err.println(
                    "Only " + getAvailableQuestions().size() + " questions were added to the current simulation");
            return getAvailableQuestions();
        } else if (n == getAvailableQuestions().size()) {
            return getAvailableQuestions();
        } else {
            List<Question> copy = getAvailableQuestions();
            Collections.sort(copy, new QuestionPriorityComparator());// gets the most prioritized at the start
            List<Question> returnList = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                returnList.add(copy.get(i));
            }

            return returnList;
        }
    }

    public void addQuestion(Question question) {
        getQuestions().add(question);
    }

    public boolean equals(Subtopic subtopic) {
        if (subtopic == null || subtopic.getClass() != getClass()) {
            return false;
        } else if (this == subtopic) {
            return true;
        }

        return (getTopicReference().equals(subtopic.getTopicReference())
                && getSubtopicName().equals(subtopic.getSubtopicName()) && getQuestions().equals(subtopic.getQuestions()));
    }

    public int hashCode() {
        return Objects.hash(subtopicName, topicReference, questions);
    }
}
