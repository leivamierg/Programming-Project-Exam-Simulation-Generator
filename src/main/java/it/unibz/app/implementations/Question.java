package it.unibz.app.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibz.app.QuestionInt;

public class Question implements QuestionInt {
    private String questionStatement;
    private String rightAnswer;
    private List<String> wrongAnswers;
    private String subtopic;
    private Subtopic subtopicReference;
    private int priorityLevel;

    @JsonCreator
    public Question(@JsonProperty("questionStatement") String questionStatement,
            @JsonProperty("rightAnswer") String rightAnswer,
            @JsonProperty("wrongAnswers") List<String> wrongAnswers
    /* , Subtopic subtopicReference */) {
        setQuestionStatement(questionStatement);
        setRightAnswer(rightAnswer);
        setWrongAnswers(wrongAnswers);
        setSubtopic(subtopicReference.getSubtopicName());
        // setSubtopicReference(subtopicReference);
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

    private void setWrongAnswers(List<String> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    private void setSubtopic(String subtopic) {
        this.subtopic = subtopic;
    }

    void setSubtopicReference(Subtopic subtopicReference) {
        this.subtopicReference = subtopicReference;
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

    public List<String> getWrongAnswers() {
        return this.wrongAnswers;
    }

    public String getSubtopic() {
        return this.subtopic;
    }

    public Subtopic getSubtopicReference() {
        return this.subtopicReference;
    }

    public int getPriorityLevel() {
        return this.priorityLevel;
    }

    // toString and others
    public String toString() {
        return "Question: " + getQuestionStatement() + System.lineSeparator() + "Right answer: " + getRightAnswer()
                + System.lineSeparator() + "Wrong answers: " + returnWrongAnswers() + System.lineSeparator()
                + "Subtopic: "
                + getSubtopic() + System.lineSeparator() + "Priority: " + getPriorityLevel();
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
}
