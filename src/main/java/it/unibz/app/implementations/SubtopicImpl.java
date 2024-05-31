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
    private Topic topic;
    private List<Question> questions;

    public SubtopicImpl(String name, Topic topic, List<Question> questions) {
        setName(name);
        setTopic(topic);
        setQuestions(questions);
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public Topic getTopic() {
        return this.topic;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    // toString and others
    public String toString() {
        return "Subtopic: " + getName() + System.lineSeparator() + "Topic: " + getTopic().getName()
                + System.lineSeparator() + "Questions: " + getQuestions();
        // TODO
    }

    public List<Question> getAvailableQuestions() {
        return getQuestions().stream().filter((q) -> (q.getPriorityLevel() > 0)).toList();
    }

    public List<Question> pickQuestions(int n) {
        if (n >= getQuestions().size()) {
            return getQuestions();
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
}
