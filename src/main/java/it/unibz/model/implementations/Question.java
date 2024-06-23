package it.unibz.model.implementations;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibz.model.interfaces.QuestionInt;

public class Question implements QuestionInt {
    private String questionStatement;
    private String rightAnswer;
    private Set<String> wrongAnswers;
    private int priorityLevel;
    private Subtopic subtopicReference;
    private String subtopic;

    @JsonCreator
    public Question(@JsonProperty("questionStatement") String questionStatement,
            @JsonProperty("rightAnswer") String rightAnswer,
            @JsonProperty("wrongAnswers") Set<String> wrongAnswers, @JsonProperty("subtopic") String subtopic
    /* , Subtopic subtopic */) {
        setQuestionStatement(questionStatement);
        setRightAnswer(rightAnswer);
        setWrongAnswers(wrongAnswers);
        setSubtopic(subtopic);
        // setSubtopicReference(subtopic);
        setPriorityLevel(1);// starts at 1 by default
    }

    // setters
    // override????
    public void setSubtopic(String name) {
        subtopic = name;
    }

    private void setQuestionStatement(String questionStatement) {
        this.questionStatement = questionStatement;
    }

    private void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    private void setWrongAnswers(Set<String> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public void setSubtopicReference(Subtopic subtopicReference) {
        this.subtopicReference = subtopicReference;
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

    public Subtopic getSubtopicReference() {
        return this.subtopicReference;
    }

    public int getPriorityLevel() {
        return this.priorityLevel;
    }

    public String getSubtopic() {
        return subtopic;
    }

    // toString and others

    @Override
    public void linkQuestionToSubtopic(Set<Topic> topics) {
        Optional<Subtopic> correctSubtopic = topics.stream().flatMap(t -> t.getSubtopics().stream())
                .filter(s -> s.getSubtopicName().equals(subtopic)).findFirst();
        if (correctSubtopic.isPresent())
            setSubtopicReference(correctSubtopic.get());
    }

    public String toString() {
        return "Question: " + getQuestionStatement() + System.lineSeparator() + "Right answer: " + getRightAnswer()
                + System.lineSeparator() + "Wrong answers: " + returnWrongAnswers() + System.lineSeparator()
                + "Subtopic: "
                + getSubtopic() + System.lineSeparator() + "Priority: " + getPriorityLevel();
    }

    private String returnWrongAnswers() {
        return getWrongAnswers().stream().collect(Collectors.joining("; "));
    }

    public String getQuestionAndAnswers() {
        StringBuilder sb = new StringBuilder();
        sb.append(getQuestionStatement()).append(System.lineSeparator());
        Map<String, Character> shuffleMap = getShuffleMap();
  
        return sb.toString();
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

    @Override
    public boolean equals(Question question) {
        if (question == null || question.getClass() != getClass()) {
            return false;
        } else if (question == this) {
            return true;
        }

        return getQuestionStatement().equals(question.getQuestionStatement())
                && getRightAnswer().equals(question.getRightAnswer())
                && getWrongAnswers().equals(question.getWrongAnswers())
                && getSubtopic().equals(question.getSubtopic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionStatement, rightAnswer, wrongAnswers, subtopic);
    }
}
