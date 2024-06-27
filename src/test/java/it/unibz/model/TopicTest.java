package it.unibz.model;

import static it.unibz.utils.TopicUtils.topic1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import it.unibz.model.implementations.Question;
import it.unibz.model.implementations.Subtopic;
import it.unibz.model.implementations.Topic;
import it.unibz.utils.SubtopicUtils;
import it.unibz.utils.TopicUtils;

public class TopicTest {
    @BeforeEach
    void init() {
        TopicUtils.init();
        SubtopicUtils.init();
    }

    @Nested
    class equalsAndHashCodeTest {
        @DisplayName("Should return true for the same object and different objects but with identical parameters")
        @Test
        void test7() {
            Topic topicA = topic1;
            Topic topicB = topicA;

            Set<Subtopic> subtopicsSet = new HashSet<>();
            Topic topicC = new Topic(topicA.getTopicName(), subtopicsSet);

            for (Subtopic s : topicA.getSubtopics()) {
                Set<Question> newSet = new HashSet<>();
                Subtopic sX = new Subtopic(s.getSubtopicName(), newSet, topicA.getTopicName());
                for (Question q : s.getQuestions()) {
                    Question newQuestion = new Question(q.getQuestionStatement(), q.getRightAnswer(),
                            q.getWrongAnswers(), q.getSubtopic());
                    sX.getQuestions().add(newQuestion);
                }
                topicC.getSubtopics().add(sX);
            }

            assertEquals(topicA, topicB);
            assertEquals(topicA, topicC);

            assertEquals(topicA.hashCode(), topicB.hashCode());
            assertEquals(topicA.hashCode(), topicC.hashCode());
        }
    }
}
