package it.unibz.model.implementations;

import it.unibz.model.comparators.QuestionPriorityComparator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibz.model.interfaces.SubtopicInt;

public class Subtopic implements SubtopicInt {
    private String subtopicName;
    private Topic topicReference;
    private String topicName;
    private Set<Question> questions;

    @JsonCreator
    public Subtopic(@JsonProperty("subtopicName") String subtopicName,
            @JsonProperty("questions") Set<Question> questions) {
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

    private void setQuestions(Set<Question> questions) {
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

    public Set<Question> getQuestions() {
        return this.questions;
    }

    // toString and others
    public String toString() {
        return "Subtopic: " + getSubtopicName() + System.lineSeparator() + "Topic: "
                + getTopicReference().getTopicName()
                + System.lineSeparator() + "Nr. questions: " + getQuestions().size();
    }

    public Set<Question> getAvailableQuestions() {
        return getQuestions().stream().filter((q) -> (q.getPriorityLevel() > 0)).collect(Collectors.toSet());
    }

    public Set<Question> pickQuestions(int n) throws IllegalArgumentException {
        if (n > getAvailableQuestions().size()) {
            System.err.println(
                    "Attention: the number of questions requested is greater than the number of available question");
            System.err.println(
                    "Only " + getAvailableQuestions().size() + " questions were added to the current simulation");
            return getAvailableQuestions();
        } else if (n == getAvailableQuestions().size()) {
            return getAvailableQuestions();
        } else {
            List<Question> copy = getAvailableQuestions().stream().toList();
            Collections.sort(copy, new QuestionPriorityComparator());// gets the most prioritized at the start
            Set<Question> returnSet = new HashSet<>();

            for (int i = 0; i < n; i++) {
                returnSet.add(copy.get(i));
            }

            return returnSet;
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
                && getSubtopicName().equals(subtopic.getSubtopicName()) && equalsQuestions(subtopic.getQuestions()));
    }

    private boolean equalsQuestions(Set<Question> questions) {
        if (getQuestions().size() != questions.size() || questions == null) {
            return false;
        } else {
            return questions.containsAll(getQuestions());
        }
    }

    public int hashCode() {
        return Objects.hash(subtopicName, topicReference, questions);
    }
}
