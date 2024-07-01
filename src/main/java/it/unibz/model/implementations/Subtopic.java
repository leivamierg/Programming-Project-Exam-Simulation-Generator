package it.unibz.model.implementations;

import com.fasterxml.jackson.annotation.*;
import it.unibz.model.comparators.QuestionPriorityComparator;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import it.unibz.model.interfaces.SubtopicInt;

/**
 * Represents a subtopic in a topic.
 */
@JsonIgnoreProperties(value = { "topicReference" })
public class Subtopic implements SubtopicInt {
    private String subtopicName;
    private String topic;
    private Topic topicReference;
    private Set<Question> questions;

    /**
     * Constructs a Subtopic object with the specified subtopic name, questions, and topic.
     *
     * @param subtopicName the name of the subtopic
     * @param questions the set of questions associated with the subtopic
     * @param topic the topic of the subtopic
     */
    @JsonCreator
    public Subtopic(@JsonProperty("subtopicName") String subtopicName,
            @JsonProperty("questions") Set<Question> questions, @JsonProperty("topic") String topic) {
        setSubtopicName(subtopicName);
        setQuestions(questions);
        setTopic(topic);
    }

    // setters

    /**
     * Sets the subtopic name.
     *
     * @param subtopicName the name of the subtopic
     */
    public void setSubtopicName(String subtopicName) {
        this.subtopicName = subtopicName;
    }

    /**
     * Sets the topic.
     *
     * @param topic the topic of the subtopic
     */
    void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * Sets the questions associated with the subtopic.
     *
     * @param questions the set of questions
     */
    private void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    /**
     * Sets the topic reference.
     *
     * @param topicReference the reference to the topic
     */
    public void setTopicReference(Topic topicReference) {
        this.topicReference = topicReference;
    }

    // getters

    /**
     * Returns the subtopic name.
     *
     * @return the name of the subtopic
     */
    public String getSubtopicName() {
        return subtopicName;
    }

    /**
     * Returns the topic of the subtopic.
     *
     * @return the topic
     */
    public String getTopic() {
        return this.topic;
    }

    /**
     * Returns the set of questions associated with the subtopic.
     *
     * @return the set of questions
     */
    public Set<Question> getQuestions() {
        return this.questions;
    }

    /**
     * Returns the reference to the topic.
     *
     * @return the topic reference
     */
    public Topic getTopicReference() {
        return topicReference;
    }

    // toString and others

    /**
     * Links the subtopic to the corresponding topic.
     *
     * @param topics the set of topics
     */
    public void linkSubtopicToTopic(Set<Topic> topics) {
        Optional<Topic> correctSubtopic = topics.stream().filter(s -> s.getTopicName().equals(topic)).findFirst();
        if (correctSubtopic.isPresent())
            setTopicReference(correctSubtopic.get());
    }

    /**
     * Returns a string representation of the subtopic.
     *
     * @return a string representation of the subtopic
     */
    public String toString() {
        return "Subtopic: " + getSubtopicName() + System.lineSeparator() + "Topic: "
                + getTopic()
                + System.lineSeparator() + "Nr. questions: " + getQuestions().size();
    }

    /**
     * Returns the set of available questions (with priority level greater than 0).
     *
     * @return the set of available questions
     */
    public Set<Question> getAvailableQuestions() {
        return getQuestions().stream().filter((q) -> (q.getPriorityLevel() > 0)).collect(Collectors.toSet());
    }

    /**
     * Picks a specified number of questions from the available questions.
     *
     * @param n the number of questions to pick
     * @return the set of picked questions
     * @throws IllegalArgumentException if the number of questions requested is greater than the number of available questions
     */
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
            }

            return returnSet;
        }
    }

    /**
     * Adds a question to the set of questions.
     *
     * @param question the question to add
     */
    public void addQuestion(Question question) {
        getQuestions().add(question);
    }

    /**
     * Checks if the subtopic is equal to the specified object.
     *
     * @param subtopic the object to compare
     * @return true if the subtopic is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(Object subtopic) {
        if (subtopic == null || !(subtopic instanceof Subtopic)) {
            return false;
        } else if (this == (Object) subtopic) {
            return true;
        }

        return (getTopic().equals(((Subtopic) subtopic).getTopic())
                && getSubtopicName().equals(((Subtopic) subtopic).getSubtopicName())
                && getQuestions().equals((((Subtopic) subtopic).getQuestions())));
    }

    /**
     * Returns the hash code of the subtopic.
     *
     * @return the hash code of the subtopic
     */
    @Override
    public int hashCode() {
        return Objects.hash(topic, subtopicName, questions);
    }
}
