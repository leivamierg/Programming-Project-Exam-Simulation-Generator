package it.unibz.utils;

import it.unibz.model.implementations.Question;
import it.unibz.model.implementations.Simulation;
import it.unibz.model.implementations.Stats;
import it.unibz.model.implementations.Subtopic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static it.unibz.utils.TopicUtils.*;
import static it.unibz.utils.SubtopicUtils.*;
import static it.unibz.utils.QuestionUtils.*;

public class SimulationUtils {

    // Simulation T1 -> simulation on topic 1 with 2 questions per subtopic
    // Simulation T1.S1.S2 -> simulation on subtopic 1.1 and 1.2 with 2 questions per subtopic
    // Simulation T2 -> simulation on topic 2 with 2 questions per subtopic
    public static Simulation simulationT1;
    public static Simulation simulationT1_S1_S2;
    public static Simulation simulationT2;
    private static char getCorrectAnswer(Simulation simulation, Question question) {
        return question.getCorrectAnswerLabel(simulation.getQuestionToShuffledAnswers().get(question));
    }
    private static char getWrongAnswer(Simulation simulation, Question question) {
        char wrongAnswer = 'E';
        switch (getCorrectAnswer(simulation, question)) {
            case 'A': wrongAnswer = 'B';
                break;
            case 'B':
            case 'C':
            case 'D': wrongAnswer = 'A';
                break;
        }
        return wrongAnswer;
    }

    public static void init() {
        // Simulation T1
        simulationT1 = new Simulation();
        simulationT1.select(topic1, 2);
        simulationT1.start();
        // Subtopic 1.1 -> 1 correct, 1 wrong
        simulationT1.answer(getCorrectAnswer(simulationT1, question1_1_1));
        simulationT1.answer(getWrongAnswer(simulationT1, question1_1_2));
        // Subtopic 1.2 -> both wrong
        simulationT1.answer(getWrongAnswer(simulationT1, question1_2_1));
        simulationT1.answer(getWrongAnswer(simulationT1, question1_2_2));
        // Subtopic 1.1 -> both wrong
        simulationT1.answer(getWrongAnswer(simulationT1, question1_3_1));
        simulationT1.answer(getWrongAnswer(simulationT1, question1_3_3));

        // Simulation T1.S1.S2
        simulationT1_S1_S2 = new Simulation();
        simulationT1_S1_S2.select(Set.of(new Subtopic[] {subtopic1_1, subtopic1_2}), 2);
        simulationT1_S1_S2.start();
        // Subtopic 1.1 -> both wrong
        simulationT1_S1_S2.answer(getWrongAnswer(simulationT1_S1_S2, question1_1_2));
        simulationT1_S1_S2.answer(getWrongAnswer(simulationT1_S1_S2, question1_1_1));
        // Subtopic 1.2 -> 1 correct, 1 wrong
        simulationT1_S1_S2.answer(getCorrectAnswer(simulationT1_S1_S2, question1_2_1));
        simulationT1_S1_S2.answer(getWrongAnswer(simulationT1_S1_S2, question1_2_2));

        // Simulation T2
        simulationT2 = new Simulation();
        simulationT2.select(topic2, 2);
        simulationT2.start();
        // Subtopic 2.1 -> 1 correct, 1 wrong
        simulationT2.answer(getCorrectAnswer(simulationT2, question2_1_1));
        simulationT2.answer(getWrongAnswer(simulationT2, question2_1_2));
        // Subtopic 2.2 -> both correct
        simulationT2.answer(getCorrectAnswer(simulationT2, question2_2_1));
        simulationT2.answer(getCorrectAnswer(simulationT2, question2_2_2));
        // Subtopic 2.3 -> both wrong
        simulationT2.answer(getWrongAnswer(simulationT2, question2_3_1));
        simulationT2.answer(getWrongAnswer(simulationT2, question2_3_3));
        // Subtopic 2.4 -> both wrong
        simulationT2.answer(getWrongAnswer(simulationT2, question2_4_1));
        simulationT2.answer(getWrongAnswer(simulationT2, question2_4_2));
    }



}
