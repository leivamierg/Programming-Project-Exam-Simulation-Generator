package it.unibz.model.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibz.model.interfaces.QuestionInt;

public class Question implements QuestionInt {
    private String questionStatement;
    private String rightAnswer;
    private Set<String> wrongAnswers;
    private Subtopic subtopic;
    private int priorityLevel;

    @JsonCreator
    public Question(@JsonProperty("questionStatement") String questionStatement,
            @JsonProperty("rightAnswer") String rightAnswer,
            @JsonProperty("wrongAnswers") Set<String> wrongAnswers
    /* , Subtopic subtopic */) {
        setQuestionStatement(questionStatement);
        setRightAnswer(rightAnswer);
        setWrongAnswers(wrongAnswers);
        // setSubtopicReference(subtopic);
        setPriorityLevel(1);// starts at 1 by default
    }

    // setters
    // override????
    private void setQuestionStatement(String questionStatement) {
        this.questionStatement = questionStatement;
    }

    private void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    private void setWrongAnswers(Set<String> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    void setSubtopic(Subtopic subtopic) {
        this.subtopic = subtopic;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    // getters
    public String getQuestionStatement() {
        return this.questionStatement;
    }

    public String getRightAnswer() {
        return this.rightAnswer;
    }

    public Set<String> getWrongAnswers() {
        return this.wrongAnswers;
    }

    public Subtopic getSubtopic() {
        return this.subtopic;
    }

    public int getPriorityLevel() {
        return this.priorityLevel;
    }

    // toString and others
    public String toString() {
        return "Question: " + getQuestionStatement() + System.lineSeparator() + "Right answer: " + getRightAnswer()
                + System.lineSeparator() + "Wrong answers: " + returnWrongAnswers() + System.lineSeparator()
                + "Subtopic: "
                + getSubtopic().getSubtopicName() + System.lineSeparator() + "Priority: " + getPriorityLevel();
    }

    private String returnWrongAnswers() {
        return getWrongAnswers().stream().collect(Collectors.joining("; "));
    }

    public Map<String, Character> getShuffleMap() {
        List<String> everyQuestionList = new ArrayList<>(getWrongAnswers());
        everyQuestionList.add(getRightAnswer());
        Collections.shuffle(everyQuestionList);
        Map<String, Character> shufflemap = new HashMap<>();

        shufflemap.put(everyQuestionList.get(0), 'A');
        shufflemap.put(everyQuestionList.get(1), 'B');
        shufflemap.put(everyQuestionList.get(2), 'C');
        shufflemap.put(everyQuestionList.get(3), 'D');

        return shufflemap;
    }

    public char getCorrectAnswerLabel(Map<String, Character> shuffleMap) {
        return shuffleMap.get(getRightAnswer());
    }

    public boolean equals(Question question) {
        if (question == null || question.getClass() != getClass()) {
            return false;
        } else if (question == this) {
            return true;
        }

        return getQuestionStatement().equals(question.getQuestionStatement())
                && getRightAnswer().equals(question.getRightAnswer())
                && getWrongAnswers().equals(question.getWrongAnswers())
                && getSubtopic().equals(question.getSubtopic())
                && getPriorityLevel() == question.getPriorityLevel();
    }

    public int hashCode() {
        return Objects.hash(questionStatement, rightAnswer, wrongAnswers, subtopic, priorityLevel);
    }
}
