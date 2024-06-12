package it.unibz.model;

import it.unibz.model.implementations.CorrectAnswersAndPercentage;
import it.unibz.model.implementations.Question;
import it.unibz.model.implementations.Simulation;
import it.unibz.model.implementations.Subtopic;
import it.unibz.utils.QuestionUtils;
import it.unibz.utils.SubtopicUtils;
import it.unibz.utils.TopicUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static it.unibz.utils.TopicUtils.*;
import static it.unibz.utils.QuestionUtils.*;
import static it.unibz.utils.SubtopicUtils.*;
public class SimulationTest {
    private final Simulation simulation = new Simulation();

    @BeforeEach
    void init() {
        TopicUtils.init();
        // SubtopicUtils.init();
        // QuestionUtils.init();
    }

    private void updateParametersAfterFirstSim() {
        // correct
        question1_1_1.setPriorityLevel(0);
        question1_2_1.setPriorityLevel(0);
        // wrong
        question1_3_1.setPriorityLevel(question1_3_1.getPriorityLevel() + 2);
        // non selected
        question1_1_2.setPriorityLevel(question1_1_2.getPriorityLevel() + 1);
        question1_2_2.setPriorityLevel(question1_2_2.getPriorityLevel() + 1);
        question1_3_2.setPriorityLevel(question1_3_2.getPriorityLevel() + 1);
        question1_3_3.setPriorityLevel(question1_3_3.getPriorityLevel() + 1);
    }

    @Nested
    class SelectTest {
        private Map<Subtopic, List<Question>> createExpectedSubtopicTo1QuestionFirstSim() {
            Map<Subtopic, List<Question>> expected = new HashMap<>();
            List<Question> expectedQuestions1_1 = new ArrayList<>();
            expectedQuestions1_1.add(question1_1_1);
            List<Question> expectedQuestions1_2 = new ArrayList<>();
            expectedQuestions1_2.add(question1_2_1);
            List<Question> expectedQuestions1_3 = new ArrayList<>();
            expectedQuestions1_3.add(question1_3_1);
            expected.put(subtopic1_1, expectedQuestions1_1);
            expected.put(subtopic1_2, expectedQuestions1_2);
            expected.put(subtopic1_3, expectedQuestions1_3);
            return expected;
        }

        private Map<Subtopic, List<Question>> createExpectedSubtopicTo1QuestionSecondSim() {
            Map<Subtopic, List<Question>> expected = new HashMap<>();
            List<Question> expectedQuestions1_1 = new ArrayList<>();
            expectedQuestions1_1.add(question1_1_2);
            List<Question> expectedQuestions1_2 = new ArrayList<>();
            expectedQuestions1_2.add(question1_2_2);
            List<Question> expectedQuestions1_3 = new ArrayList<>();
            expectedQuestions1_3.add(question1_3_1);
            expected.put(subtopic1_1, expectedQuestions1_1);
            expected.put(subtopic1_2, expectedQuestions1_2);
            expected.put(subtopic1_3, expectedQuestions1_3);
            return expected;
        }

        @DisplayName("Null topic: select(Topic) should throw a NullPointerException")
        @Test
        void selectTopicNull() {
            assertThrows(NullPointerException.class, () -> simulation.select(nullTopic, 2));
        }

        @DisplayName("Topic 1 at first sim: select(Topic) should return a map with first question for each subtopic")
        @Test
        void selectTopic1FirstSim() {
            Map<Subtopic, List<Question>> expected = createExpectedSubtopicTo1QuestionFirstSim();
            simulation.select(topic1, 1);
            assertEquals(expected, simulation.getQuestionsPerSubtopic());
        }

        @DisplayName("Topic 1 at second sim: select(Topic) should return a map with second question for subtopic 1 and 2, " +
                "first question for subtopic 3")
        @Test
        void selectTopic1SecondSim() {
            Map<Subtopic, List<Question>> expected = createExpectedSubtopicTo1QuestionSecondSim();
            updateParametersAfterFirstSim();
            simulation.select(topic1, 1);
            assertEquals(expected, simulation.getQuestionsPerSubtopic());
        }

        @DisplayName("Set of subtopics null: select(null set) should throw a NullPointerException")
        @Test
        void selectListSubtopicsNull() {
            Set<Subtopic> nullSet = null;
            assertThrows(NullPointerException.class, () -> simulation.select(nullSet, 2));
        }

        @DisplayName("Empty Set of subtopics: select(Set<Subtopic>) should throw an IllegalStateException")
        @Test
        void selectListSubtopicNull() {
            Set<Subtopic> emptySet = new HashSet<>();
            assertThrows(IllegalStateException.class, () -> simulation.select(emptySet, 2));
        }
    }

    @Nested
    class AllQuestionsTest {
        @DisplayName("The subtopic -> questions are: " +
                "subtopic 1.1 -> [question1_1_1], " +
                "subtopic 1.2 -> [question1_2_1]," +
                "subtopic 1.3 -> [question1_3_1]: " +
                "getAllQuestions() should output [question1_1_1, question1_2_1, question_1_3_1]")
        @Test
        void getAllQuestionsFirstSim() {
            simulation.select(topic1, 1);
            List<Question> expected = new ArrayList<>();
            expected.add(question1_1_1);
            expected.add(question1_2_1);
            expected.add(question1_3_1);
            assertEquals(expected, simulation.getAllQuestions());
        }
        @DisplayName("The subtopic -> questions are: " +
                "subtopic 1.1 -> [question1_1_2], " +
                "subtopic 1.2 -> [question1_2_2]," +
                "subtopic 1.3 -> [question1_3_1]: " +
                "getAllQuestions() should output [question1_1_2, question1_2_2, question_1_3_2]")
        @Test
        void getAllQuestionsSecondSim() {
            simulation.select(topic1, 1);
            updateParametersAfterFirstSim();
            simulation.select(topic1, 1);
            List<Question> expected = new ArrayList<>();
            expected.add(question1_1_2);
            expected.add(question1_2_2);
            expected.add(question1_3_1);
            assertEquals(expected, simulation.getAllQuestions());

        }
    }

    @Nested
    class ChangeQuestionTest {
        @BeforeEach
        void setUpSim() {
            simulation.select(topic1, 2);
            simulation.start();
        }
        @DisplayName("The current question is the first one; therefore, changeQuestion('+') should change the current question to the second one")
        @Test
        void nextWithFirstQuestion() {
            Question expected = question1_1_2;
            simulation.changeQuestion('+');
            Question produced = simulation.getCurrentQuestion();
            assertEquals(expected, produced);
        }

        @DisplayName("The current question is the last one; therefore, changeQuestion('-') should change the current question to the penultimate one")
        @Test
        void prevWithLastQuestion() {
            simulation.setCurrentQuestion(simulation.getAllQuestions().get(simulation.getAllQuestions().size() - 1));
            Question expected = question1_3_1;
            simulation.changeQuestion('-');
            Question produced = simulation.getCurrentQuestion();
            assertEquals(expected, produced);
        }

        @DisplayName("The current question is the first one; therefore, changeQuestion('-') should not change the current question")
        @Test
        void prevWithFirstQuestion() {
            Question expected = question1_1_1;
            simulation.changeQuestion('-');
            Question produced = simulation.getCurrentQuestion();
            assertEquals(expected, produced);
        }

        @DisplayName("The current question is the last one; therefore, changeQuestion('+') should not change the current question")
        @Test
        void nextWithLastQuestion() {
            simulation.setCurrentQuestion(simulation.getAllQuestions().get(simulation.getAllQuestions().size() - 1));
            Question expected = question1_3_2;
            simulation.changeQuestion('+');
            Question produced = simulation.getCurrentQuestion();
            assertEquals(expected, produced);
        }

        @DisplayName("The input is neither '+' nor '-'; therefore changeQuestion(char) should throw an IllegalArgumentException")
        @Test
        void changeQuestionWithWrongChar() {
            assertThrows(IllegalArgumentException.class, () -> simulation.changeQuestion('?'));
        }

        @DisplayName("The current question is the last one and I change to the first one: changeQuestion(1) should work")
        @Test
        void changeQuestionWithIdx1() {
            simulation.setCurrentQuestion(simulation.getAllQuestions().get(simulation.getAllQuestions().size() - 1));
            Question expected = question1_1_1;
            simulation.changeQuestion(1);
            Question produced = simulation.getCurrentQuestion();
            assertEquals(expected, produced);
        }

        @DisplayName("The input index is too small: changeQuestion(too small index) should throw an IndexOutOfBoundException")
        @Test
        void changeQuestionWithTooSmallIdx() {
            assertThrows(IndexOutOfBoundsException.class, () -> simulation.changeQuestion(-1));
        }

        @DisplayName("The input index is too big: changeQuestion(too big index) should throw an IndexOutOfBoundException")
        @Test
        void changeQuestionWithTooBigIdx() {
            assertThrows(IndexOutOfBoundsException.class, () -> simulation.changeQuestion(simulation.getAllQuestions().size()));
        }
    }

    @Nested
    class AnswerTest {
        @BeforeEach
        void setUpSim() {
            simulation.select(topic1, 1);
            simulation.start();
        }

        @DisplayName("The answer to the first question is valid but in lower-case: map questionToAnswer should contain " +
                "the answer (in upper-case) and the current question should be the second one")
        @Test
        void validAnswerToFirstQuestion() {
            Map<Question, Character> expectedMap = new HashMap<>();
            expectedMap.put(question1_1_1, 'A');
            Question expectedCurrentQuestion = question1_2_1;
            simulation.answer('a');
            assertEquals(expectedMap, simulation.getQuestionToAnswer());
            assertEquals(expectedCurrentQuestion, simulation.getCurrentQuestion());
        }

        @DisplayName("The answer to the last is valid: map questionToAnswer should contain " +
                "the answer and the current question should remain the last one")
        @Test
        void validAnswerToLastQuestion() {
            Map<Question, Character> expectedMap = new HashMap<>();
            expectedMap.put(question1_3_1, '-');
            Question expectedCurrentQuestion = question1_3_1;
            simulation.answer('-');
            assertEquals(expectedMap, simulation.getQuestionToAnswer());
            assertEquals(expectedCurrentQuestion, simulation.getCurrentQuestion());
        }

        @DisplayName("The answer is invalid but: answer(invalid answer) should throw an IllegalArgumentException")
        @Test
        void invalidAnswerToFirstQuestion() {
            assertThrows(IllegalArgumentException.class, () -> simulation.answer('?'));
        }
    }

    @Nested
    class IsCorrectTest {
        @BeforeEach
        void setUpSim() {
            simulation.select(topic1, 1);
            simulation.start();
            // first question -> correct
            simulation.answer(getCorrectAnswer(question1_1_1));
            // second question -> correct
            simulation.answer(getCorrectAnswer(question1_2_1));
            // third question
            simulation.answer(getWrongAnswer(question1_3_1));
        }
        private char getCorrectAnswer(Question question) {
            return question.getCorrectAnswerLabel(simulation.getQuestionToShuffledAnswers().get(question));
        }
        private char getWrongAnswer(Question question) {
            char wrongAnswer = 'E';
            switch (getCorrectAnswer(question)) {
                case 'A': wrongAnswer = 'A';
                    break;
                case 'B':
                case 'C':
                case 'D': wrongAnswer = 'B';
                    break;
            }
            return wrongAnswer;
        }

        @DisplayName("The first question was answered correctly: isCorrect(first question) should output true")
        @Test
        void checkFirstQuestion() {
            assertTrue(simulation.isCorrect(question1_1_1));
        }

        @DisplayName("The second question was answered correctly: isCorrect(second question) should output true")
        @Test
        void checkSecondQuestion() {
            assertTrue(simulation.isCorrect(question1_2_1));
        }

        @DisplayName("The third question was answered wrongly: isCorrect(third question) should output false")
        @Test
        void checkThirdQuestion() {
            assertFalse(simulation.isCorrect(question1_3_1));
        }

        @DisplayName("The input question is null: isCorrect(null question) should throw a NullPointerException")
        @Test
        void selectListSubtopicsNull() {
            assertThrows(NullPointerException.class, () -> simulation.isCorrect(nullQuestion));
        }
    }

    @Nested
    class NonSelected_Correct_Wrong_QuestionsTest {
        @BeforeEach
        void setUpSim() {
            simulation.select(topic1, 2);
            simulation.start();
            // first subtopic -> both questions correct
            simulation.answer(getCorrectAnswer(question1_1_1));
            simulation.answer(getCorrectAnswer(question1_1_2));
            // second subtopic -> one question correct, one question wrong
            simulation.answer(getCorrectAnswer(question1_2_1));
            simulation.answer(getWrongAnswer(question1_2_2));
            // third subtopic -> both questions wrong
            simulation.answer(getWrongAnswer(question1_3_1));
            simulation.answer(getWrongAnswer(question1_3_2));
        }
        private char getCorrectAnswer(Question question) {
            return question.getCorrectAnswerLabel(simulation.getQuestionToShuffledAnswers().get(question));
        }
        private char getWrongAnswer(Question question) {
            char wrongAnswer = 'E';
            switch (getCorrectAnswer(question)) {
                case 'A': wrongAnswer = 'A';
                    break;
                case 'B':
                case 'C':
                case 'D': wrongAnswer = 'B';
                    break;
            }
            return wrongAnswer;
        }

        @DisplayName("Selected questions: [question1_1_1, question1_1_2, question1_2_1, " +
                "question1_2_2, question1_3_1, question1_3_2] " +
                "getNonSelectedQuestions should return: [question1_3_3]")
        @Test
        void nonSelectedQuestions() {
            Set<Question> expected = new HashSet<>();
            expected.add(question1_3_3);
            Set<Question> produced = simulation.getNonSelectedQuestions();
            assertEquals(expected, produced);
        }

        @DisplayName("getAllCorrectQuestions() should return: [question1_1_1, question1_1_2, question1_2_1]")
        @Test
        void correctQuestions() {
            Set<Question> expected = new HashSet<>();
            expected.add(question1_1_1);
            expected.add(question1_1_2);
            expected.add(question1_2_1);
            Set<Question> produced = simulation.getAllCorrectQuestions();
            assertEquals(expected, produced);
        }

        @DisplayName("getAllWrongQuestions() should return: [question1_2_2, question1_3_1, question1_3_2]")
        @Test
        void wrongQuestions() {
            Set<Question> expected = new HashSet<>();
            expected.add(question1_2_2);
            expected.add(question1_3_1);
            expected.add(question1_3_2);
            Set<Question> produced = simulation.getAllWrongQuestions();
            assertEquals(expected, produced);
        }

        @DisplayName("getSubtopicCorrectQuestions(subtopic 1.1) should return: [question1_1_1, question1_1_2]")
        @Test
        void correctQuestionsSubtopic1_1() {
            Set<Question> expected = new HashSet<>();
            expected.add(question1_1_1);
            expected.add(question1_1_2);
            Set<Question> produced = simulation.getSubtopicCorrectQuestions(subtopic1_1);
            assertEquals(expected, produced);
        }

        @DisplayName("getSubtopicCorrectQuestions(subtopic 1.2) should return: [question1_2_1]")
        @Test
        void correctQuestionsSubtopic1_2() {
            Set<Question> expected = new HashSet<>();
            expected.add(question1_2_1);
            Set<Question> produced = simulation.getSubtopicCorrectQuestions(subtopic1_2);
            assertEquals(expected, produced);
        }

        @DisplayName("getSubtopicCorrectQuestions(subtopic 1.3) should return an empty Set")
        @Test
        void correctQuestionsSubtopic1_3() {
            Set<Question> expected = new HashSet<>();
            Set<Question> produced = simulation.getSubtopicCorrectQuestions(subtopic1_3);
            assertEquals(expected, produced);
        }

        @DisplayName("getSubtopicWrongQuestions(subtopic 1.1) should return an empty Set")
        @Test
        void wrongQuestionsSubtopic1_1() {
            Set<Question> expected = new HashSet<>();
            Set<Question> produced = simulation.getSubtopicWrongQuestions(subtopic1_1);
            assertEquals(expected, produced);
        }

        @DisplayName("getSubtopicWrongQuestions(subtopic 1.2) should return: [question1_2_2]")
        @Test
        void wrongQuestionsSubtopic1_2() {
            Set<Question> expected = new HashSet<>();
            expected.add(question1_2_2);
            Set<Question> produced = simulation.getSubtopicWrongQuestions(subtopic1_2);
            assertEquals(expected, produced);
        }

        @DisplayName("getSubtopicWrongQuestions(subtopic 1.3) should return: [question1_3_1, question1_3_2]")
        @Test
        void wrongQuestionsSubtopic1_3() {
            Set<Question> expected = new HashSet<>();
            expected.add(question1_3_1);
            expected.add(question1_3_2);
            Set<Question> produced = simulation.getSubtopicWrongQuestions(subtopic1_3);
            assertEquals(expected, produced);
        }
    }

    @Nested
    class StatsTest {
        @BeforeEach
        void setUpSim() {
            simulation.select(topic1, 2);
            simulation.start();
            // first subtopic -> both questions correct
            simulation.answer(getCorrectAnswer(question1_1_1));
            simulation.answer(getCorrectAnswer(question1_1_2));
            // second subtopic -> one question correct, one question wrong
            simulation.answer(getCorrectAnswer(question1_2_1));
            simulation.answer(getWrongAnswer(question1_2_2));
            // third subtopic -> both questions wrong
            simulation.answer(getWrongAnswer(question1_3_1));
            simulation.answer(getWrongAnswer(question1_3_2));
        }
        private char getCorrectAnswer(Question question) {
            return question.getCorrectAnswerLabel(simulation.getQuestionToShuffledAnswers().get(question));
        }
        private char getWrongAnswer(Question question) {
            char wrongAnswer = 'E';
            switch (getCorrectAnswer(question)) {
                case 'A': wrongAnswer = 'A';
                    break;
                case 'B':
                case 'C':
                case 'D': wrongAnswer = 'B';
                    break;
            }
            return wrongAnswer;
        }
        @DisplayName("Both questions are correct in subtopic 1.1: correct answers -> 2, percentage -> 100.0")
        @Test
        void computeSubtopic1_1Stats() {
            CorrectAnswersAndPercentage produced = simulation.computeSubtopicStats(subtopic1_1);
            CorrectAnswersAndPercentage expected = new CorrectAnswersAndPercentage(2, 100.0);
            assertEquals(expected, produced);
        }

        @DisplayName("One question is correct, one question is wrong correct in subtopic 1.2: correct answers -> 1, percentage -> 50.0")
        @Test
        void computeSubtopic1_2Stats() {
            CorrectAnswersAndPercentage produced = simulation.computeSubtopicStats(subtopic1_2);
            CorrectAnswersAndPercentage expected = new CorrectAnswersAndPercentage(1, 50.0);
            assertEquals(expected, produced);
        }

        @DisplayName("Both questions are wrong in subtopic 1.3: correct answers -> 0, percentage -> 0.0")
        @Test
        void computeSubtopic1_3Stats() {
            CorrectAnswersAndPercentage produced = simulation.computeSubtopicStats(subtopic1_3);
            CorrectAnswersAndPercentage expected = new CorrectAnswersAndPercentage(0, 0.0);
            assertEquals(expected, produced);
        }

        @DisplayName("Three questions are correct, three questions are wrong in this sim: correct answers -> 3, percentage -> 50.0")
        @Test
        void computeSimStats() {
            CorrectAnswersAndPercentage produced = simulation.computeSimStats();
            CorrectAnswersAndPercentage expected = new CorrectAnswersAndPercentage(3, 50.0);
            assertEquals(expected, produced);
        }
    }
}
