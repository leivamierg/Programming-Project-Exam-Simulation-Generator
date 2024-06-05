package it.unibz.app.implementations;

import it.unibz.app.comparators.QuestionPriorityComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibz.app.SubtopicInt;

public class Subtopic implements SubtopicInt {
    private String subtopicName;
    private Topic topicReference;
    private String topicName;
    private List<Question> questions;

    @JsonCreator
    public Subtopic(@JsonProperty("subtopicName") String subtopicName,
            @JsonProperty("questions") List<Question> questions) {
        setSubtopicName(subtopicName);
        // setTopicReference(topicReference);TODO: add a loop in the Topic class
        setTopicName(topicReference.getTopicName());// TODO: might not work :p
        setQuestions(questions);

        for (Question question : questions) {
            question.setSubtopicReference(this);
        }

    }

    // setters
    public void setSubtopicName(String subtopicName) {
        this.subtopicName = subtopicName;
    }

    void setTopicReference(Topic topicReference) {
        this.topicReference = topicReference;
    }

    private void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    private void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    // getters
    public String getSubtopicName() {
        return this.subtopicName;
    }

    public Topic getTopicReference() {
        return this.topicReference;
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

            for (int i = 0; i < n; i++) {// adds to another list the first n elements
                returnList.add(copy.get(i));
            }

            return returnList;
        }
    }

    public void addQuestion(Question question) {
        getQuestions().add(question);
    }
}
