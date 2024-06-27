package it.unibz.model;

import static it.unibz.utils.QuestionUtils.*;
import static it.unibz.utils.SubtopicUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import it.unibz.model.implementations.Question;
import it.unibz.model.implementations.Subtopic;
import it.unibz.utils.SubtopicUtils;
import it.unibz.utils.TopicUtils;

public class SubtopicTest {
    @BeforeEach
    void init() {
        TopicUtils.init();
        SubtopicUtils.init();
    }

    @Nested
    class getAvailableQuestionsTest {

        @DisplayName("Should return all the subtopic's questions as they have a priority level of 1 or higher")
        @Test
        void test1() {
            Question q1 = question1_1_1;
            Question q2 = question1_1_2;

            Subtopic sT1 = subtopic1_1;
            q1.setPriorityLevel(2);
            q2.setPriorityLevel(4);

            assertEquals(q1.getPriorityLevel(), 2);
            assertEquals(q2.getPriorityLevel(), 4);
            assertEquals(sT1.getAvailableQuestions(), Set.of(q1, q2));
        }

        @DisplayName("Shouldn't return anything as both questions have 0 as their priority level")
        @Test
        void test2() {
            Question q3 = question1_2_1;
            Question q4 = question1_2_2;

            Subtopic sT2 = subtopic1_2;

            q3.setPriorityLevel(0);
            q4.setPriorityLevel(0);

            assertEquals(sT2.getAvailableQuestions(), Set.of());
        }

        @DisplayName("Should return 2 out of the 3 questions within the subtopic")
        @Test
        void test3() {
            Question q5 = question1_3_1;
            Question q6 = question1_3_2;
            Question q7 = question1_3_3;

            Subtopic sT3 = subtopic1_3;

            q5.setPriorityLevel(2);
            q6.setPriorityLevel(3);
            q7.setPriorityLevel(0);

            assertTrue(sT3.getAvailableQuestions().equals(Set.of(q6, q5)));
            assertTrue(sT3.getAvailableQuestions().size() == 2);
        }
    }

    @Nested
    class pickQuestionsTest {
        @DisplayName("Should pick 2 questions out of three")
        @Test
        void test4() {
            Question q8 = question2_1_1;
            Question q9 = question2_1_2;
            Question q10 = question2_1_3;

            // every question start with 1 as priority by default

            Subtopic sT4 = subtopic2_1;

            assertEquals(sT4.pickQuestions(2).size(), 2);
            assertTrue(List.of(q8, q9, q10).containsAll(sT4.pickQuestions(2)));

        }

        @DisplayName("Should pick 2 questions and display a message in the console")
        @Test
        void test5() {
            Question q11 = question2_2_1;
            Question q12 = question2_2_2;

            Subtopic sT5 = subtopic2_2;

            assertEquals(sT5.pickQuestions(4).size(), 2);
            assertTrue(List.of(q11, q12).containsAll(sT5.pickQuestions(4)));

        }

        // add test to check the priority is respected

        @DisplayName("Should pick 2 questions out of three depending in their priority level")
        @Test
        void test6() {
            Question q13 = question2_3_1;
            Question q14 = question2_3_2;
            Question q15 = question2_3_3;

            Subtopic sT6 = subtopic2_3;

            q13.setPriorityLevel(2);
            q15.setPriorityLevel(3);

            assertEquals(sT6.pickQuestions(2).size(), 2);
            assertTrue(List.of(q15, q13).containsAll(sT6.pickQuestions(2)));

        }
    }

    @Nested
    class equalsAndHashCodeTest {
        @DisplayName("Should return true for the same object and different objects but with identical parameters")
        @Test
        void test7() {
            Subtopic sTA = subtopic2_4;
            Subtopic sTB = sTA;

            Set<Question> questionsSet = new HashSet<>();
            for (Question q : sTA.getQuestions()) {
                questionsSet.add(new Question(new String(q.getQuestionStatement()), new String(q.getRightAnswer()),
                        new HashSet<String>(q.getWrongAnswers()),
                        new String(q.getSubtopic()), 1));
            }

            Subtopic sTC = new Subtopic(new String(sTA.getSubtopicName()), questionsSet, new String(sTA.getTopic()));

            assertEquals(sTA, sTB);
            assertEquals(sTA, sTC);

            assertEquals(sTA.hashCode(), sTB.hashCode());
            assertEquals(sTA.hashCode(), sTC.hashCode());
        }
    }
}
