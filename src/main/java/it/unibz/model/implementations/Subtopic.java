package it.unibz.model.implementations;

import it.unibz.model.comparators.QuestionPriorityComparator;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibz.model.interfaces.SubtopicInt;

public class Subtopic implements SubtopicInt {
    private String subtopicName;
    private Topic topic;
    private List<Question> questions;

    @JsonCreator
    public Subtopic(@JsonProperty("subtopicName") String subtopicName,
            @JsonProperty("questions") List<Question> questions) {
        setSubtopicName(subtopicName);
        // setTopic(topic);TODO: add a loop in the Topic class
        setQuestions(questions);

        for (Question question : questions) {
            question.setSubtopic(this);
        }

    }

    // setters


    public void setSubtopicName(String subtopicName) {
        this.subtopicName = subtopicName;
    }

    void setTopic(Topic topic) {
        this.topic = topic;
    }

    private void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    // getters


    public String getSubtopicName() {
        return subtopicName;
    }

    public Topic getTopic() {
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

    // toString and others
    public String toString() {
        return "Subtopic: " + getSubtopicName() + System.lineSeparator() + "Topic: "
                + getTopic().getTopicName()
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

        return (getTopic().equals(subtopic.getTopic())
                && getSubtopicName().equals(subtopic.getSubtopicName()) && getQuestions().equals(subtopic.getQuestions()));
    }

    public int hashCode() {
        return Objects.hash(subtopicName, topic, questions);
    }
}
