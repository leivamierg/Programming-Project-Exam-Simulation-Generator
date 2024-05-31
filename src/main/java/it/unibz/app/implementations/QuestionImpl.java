package it.unibz.app.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibz.app.Question;
import it.unibz.app.Subtopic;

public class QuestionImpl implements Question {
    private String questionStatement;
    private String rightAnswer;
    private List<String> wrongAnswers;
    private Subtopic subtopic;
    private int priorityLevel;

    public QuestionImpl(String questionStatement, String rightAnswer, List<String> wrongAnswers, Subtopic subtopic,
            int priorityLevel) {
        setQuestionStatement(questionStatement);
        setRightAnswer(rightAnswer);
        setWrongAnswers(wrongAnswers);
        setSubtopic(subtopic);
        setPriorityLevel(priorityLevel);// might need a starting value of 1 or so
    }

    // setters
    // override????
    public void setQuestionStatement(String questionStatement) {
        this.questionStatement = questionStatement;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setWrongAnswers(List<String> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public void setSubtopic(Subtopic subtopic) {
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

    public List<String> getWrongAnswers() {
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
                + System.lineSeparator() + "Wrong answers: " + getWrongAnswers() + System.lineSeparator() + "Subtopic: "
                + getSubtopic().getName() + System.lineSeparator() + "Priority: " + getPriorityLevel();
    }

    public List<String> shuffle() {
        List<String> copy = new ArrayList<>(getWrongAnswers());
        copy.add(getRightAnswer());
        Collections.shuffle(copy);
        return copy;
    }
}
