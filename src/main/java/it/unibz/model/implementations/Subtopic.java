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
    private Set<Question> questions;

    @JsonCreator
    public Subtopic(@JsonProperty("subtopicName") String subtopicName,
            @JsonProperty("questions") Set<Question> questions, @JsonProperty("topic") String topic) {
        setSubtopicName(subtopicName);
        // setTopic(topic);TODO: add a loop in the Topic class
        setQuestions(questions);
        setTopic(topic);

        /*
         * for (Question question : questions) {
         * question.setSubtopic(this);
         * }
         */

    }

    // setters

    public void setSubtopicName(String subtopicName) {
        this.subtopicName = subtopicName;
    }

    void setTopic(String topic) {
        this.topic = topic;
    }

    private void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public void setTopicReference(Topic topicReference) {
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

    public Set<Question> getQuestions() {
        return this.questions;
    }

    public Topic getTopicReference() {
        return topicReference;
    }

    // toString and others
    public void linkSubtopicToTopic(Set<Topic> topics) {
        Optional<Topic> correctSubtopic = topics.stream().filter(s -> s.getTopicName().equals(topic)).findFirst();
        if (correctSubtopic.isPresent())
            setTopicReference(correctSubtopic.get());
    }

    public String toString() {
        return "Subtopic: " + getSubtopicName() + System.lineSeparator() + "Topic: "
                + getTopic()
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
            List<Question> copy = new ArrayList<>(getAvailableQuestions());
            Collections.sort(copy, new QuestionPriorityComparator());// gets the most prioritized at the start
            Set<Question> returnSet = new HashSet<>();

            for (int i = 0; i < n; i++) {
                returnSet.add(copy.get(i));
                returnSet.add(copy.get(copy.size() - i - 1));
            }

            return returnSet;
        }
    }

    public void addQuestion(Question question) {
        getQuestions().add(question);
    }

    @Override
    public boolean equals(Subtopic subtopic) {
        if (subtopic == null || subtopic.getClass() != getClass()) {
            return false;
        } else if (this == subtopic) {
            return true;
        }

        return (getTopic().equals(subtopic.getTopic())
                && getSubtopicName().equals(subtopic.getSubtopicName())
                && getQuestions().equals(subtopic.getQuestions()));
    }

    private boolean equalsQuestions(Set<Question> questions) {
        if (getQuestions().size() != questions.size() || questions == null) {
            return false;
        } else {
            for(int i=0; i<questions.size(); i++){
                if(q)
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, subtopicName, questions);
    }
}
