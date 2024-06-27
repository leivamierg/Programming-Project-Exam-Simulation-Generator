package it.unibz.model;

import static it.unibz.utils.QuestionUtils.question1_1_1;
import static it.unibz.utils.QuestionUtils.question1_2_1;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import it.unibz.model.implementations.Question;
import it.unibz.utils.SubtopicUtils;
import it.unibz.utils.TopicUtils;

public class QuestionTest {
    @BeforeEach
    void init() {
        TopicUtils.init();
        SubtopicUtils.init();
    }

    @Nested
    class equalsAndHashCodeTest {
        @DisplayName("Should return true for the same object and different objects but with identical parameters")
        @Test
        void test1() {
            Question q1 = question1_1_1;
            Question q2 = q1;
            Question q3 = new Question(new String(q1.getQuestionStatement()), new String(q1.getRightAnswer()),
                    new HashSet<String>(q1.getWrongAnswers()),
                    new String(q1.getSubtopic()));

            assertEquals(q1, q2);
            assertEquals(q1, q3);

            assertEquals(q1.hashCode(), q2.hashCode());
            assertEquals(q1.hashCode(), q3.hashCode());
        }
    }

    @Nested
    class getCorrectAnswerLabelTest {
        @DisplayName("Should return all the subtopic's questions as they have a priority level of 1 or higher")
        @Test
        void test2() {
            Question q4 = question1_2_1;

            Map<String, Character> shuffleMapQ4_1 = q4.getShuffleMap();
            Map<String, Character> shuffleMapQ4_2 = q4.getShuffleMap();

            Character shuffleMapKey1 = q4.getCorrectAnswerLabel(shuffleMapQ4_1);
            Character shuffleMapKey2 = q4.getCorrectAnswerLabel(shuffleMapQ4_2);

            assertEquals(shuffleMapQ4_1.get(q4.getRightAnswer()), shuffleMapKey1);
            assertEquals(shuffleMapQ4_2.get(q4.getRightAnswer()), shuffleMapKey2);

        }
    }
}
