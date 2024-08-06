package it.unibz.model.implementations;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.*;

import it.unibz.model.interfaces.QuestionInt;

@JsonIgnoreProperties(value = { "subtopicReference" })
public class Question implements QuestionInt {
    private String questionStatement;
    private String rightAnswer;
    private Set<String> wrongAnswers;
    private int priorityLevel;
    private Subtopic subtopicReference;
    private String subtopic;
    private Map<String, Character> shuffleMap;

    @JsonCreator
    public Question(@JsonProperty("questionStatement") String questionStatement,
            @JsonProperty("rightAnswer") String rightAnswer,
            @JsonProperty("wrongAnswers") Set<String> wrongAnswers, @JsonProperty("subtopic") String subtopic,
            @JsonProperty("priorityLevel") int priorityLevel) {
        setQuestionStatement(questionStatement);
        setRightAnswer(rightAnswer);
        setWrongAnswers(wrongAnswers);
        setSubtopic(subtopic);
        setPriorityLevel(priorityLevel);
        generateShuffleMap();
    }

    // setters
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
        generateShuffleMap();
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

        Map<String, Character> map = getShuffleMap();

        // for Debugging
        // System.out.println("Shuffled Map: " + map);

        for (Map.Entry<String, Character> entry : map.entrySet()) {
            sb.append(entry.getValue()).append(") ").append(entry.getKey()).append(System.lineSeparator());
        }

        return sb.toString();
    }

    private void generateShuffleMap() {
        List<String> everyQuestionList = new ArrayList<>(getWrongAnswers());
        everyQuestionList.add(getRightAnswer());
        Collections.shuffle(everyQuestionList);

        shuffleMap = new LinkedHashMap<>();
        for (int i = 0; i < everyQuestionList.size(); i++) {
            shuffleMap.put(everyQuestionList.get(i), (char) ('A' + i));
        }
    }

    public Map<String, Character> getShuffleMap() {
        if (shuffleMap == null) {
            generateShuffleMap();
        }
        return shuffleMap;
    }

    public char getCorrectAnswerLabel(Map<String, Character> shuffleMap) {
        return shuffleMap.get(getRightAnswer());
    }

    @Override
    public boolean equals(Object question) {
        if (question == null || !(question instanceof Question)) {
            return false;
        } else if (question == this) {
            return true;
        }

        Question castQuestion = (Question) question;

        return getQuestionStatement().equals(castQuestion.getQuestionStatement())
                && getRightAnswer().equals(castQuestion.getRightAnswer())
                && getWrongAnswers().equals(castQuestion.getWrongAnswers())
                && getSubtopic().equals(castQuestion.getSubtopic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionStatement, rightAnswer, wrongAnswers, subtopic);
    }
}
