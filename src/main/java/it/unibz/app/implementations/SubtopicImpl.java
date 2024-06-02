package it.unibz.app.implementations;

import it.unibz.app.Topic;
import it.unibz.app.comparators.QuestionPriorityComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibz.app.Question;
import it.unibz.app.Subtopic;

public class SubtopicImpl implements Subtopic {
    private String name;
    private Topic topicReference;
    private String topic;
    private List<Question> questions;

    // add topicReference as a name and as a reference

    public SubtopicImpl(String name, Topic topicReference, List<Question> questions) {
        setName(name);
        setTopicReference(topicReference);
        setTopic(topicReference.getName());
        setQuestions(questions);
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    private void setTopicReference(Topic topicReference) {
        this.topicReference = topicReference;
    }

    private void setTopic(String topic) {
        this.topic = topic;
    }

    private void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public Topic getTopicReference() {
        return this.topicReference;
    }

    public String getTopic() {
        return this.topic;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    // toString and others
    public String toString() {
        return "Subtopic: " + getName() + System.lineSeparator() + "Topic: " + getTopic()
                + System.lineSeparator() + "Nr. questions: " + getQuestions().size();
    }

    public List<Question> getAvailableQuestions() {
        return getQuestions().stream().filter((q) -> (q.getPriorityLevel() > 0)).toList();
    }

    public List<Question> pickQuestions(int n) throws IllegalArgumentException {
        if (n > getAvailableQuestions().size()) {
            throw new IllegalArgumentException();
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
