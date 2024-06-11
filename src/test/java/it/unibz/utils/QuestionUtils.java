package it.unibz.utils;

import it.unibz.model.implementations.Question;

import java.util.Set;

public class QuestionUtils {

    // Question 1.1.1 CSA FL -> first topic (CSA) - first subtopic (Binary Arithmetic) - first question -> for FileLoader
    // Question 1.2.1 CSA FL -> first topic (CSA) - second subtopic (Sequential and Combinatorial circuits) - first question -> for FileLoader
    public static Question question1_1_1_CSA_FL = new Question("What happens to the binary representation of a number when it gets multiplied by 2?",
            "The bits get shifted to the left",
            Set.of(new String[]{"The bits get flipped", "The bits get shifted to the right", "The bits are shuffled"}),
            "Binary Arithmetic");
    public static Question question1_2_1_CSA_FL = new Question("A Set-Reset flip-flop has the purpose of:",
            "memorizing one bit of information",
            Set.of(new String[]{"setting or resetting an external circuit", "memorizing a word of N bits", "generating a clock signal"}),
            "Sequential and Combinatorial circuits");

    // Question 1.1.1 -> first topic - first subtopic - first question
    // Question 1.1.2 -> first topic - first subtopic - second question

    public static Question question1_1_1= new Question("Question 1.1.1",
            "Right answer 1.1.1",
            Set.of(new String[]{"Wrong answer 1.1.1.1", "Wrong answer 1.1.1.2", "Wrong answer 1.1.1.3"}), // last number is the number of answer
            "Subtopic 1.1");
    public static Question question1_1_2 = new Question("Question 1.1.2",
            "Right answer 1.1.2",
            Set.of(new String[]{"Wrong answer 1.1.2.1", "Wrong answer 1.1.2.2", "Wrong answer 1.1.2.3"}),
            "Subtopic 1.1");

    // Question 1.2.1 -> first topic - second subtopic - first question
    // Question 1.2.2 -> first topic - second subtopic - second question
    public static Question question1_2_1 = new Question("Question 1.2.1",
            "Right answer 1.2.1",
            Set.of(new String[]{"Wrong answer 1.2.1.1", "Wrong answer 1.2.1.2", "Wrong answer 1.2.1.3"}),
            "Subtopic 1.2");
    public static Question question1_2_2 = new Question("Question 1.2.2",
            "Right answer 1.2.2",
            Set.of(new String[]{"Wrong answer 1.2.2.1", "Wrong answer 1.2.2.2", "Wrong answer 1.2.2.3"}),
            "Subtopic 1.2");

    // Question 1.3.1 -> first topic - third subtopic - first question
    // Question 1.3.2 -> first topic - third subtopic - second question
    // Question 1.3.3 -> first topic - third subtopic - third question
    public static Question question1_3_1 = new Question("Question 1.3.1",
            "Right answer 1.3.1",
            Set.of(new String[]{"Wrong answer 1.3.1.1", "Wrong answer 1.3.1.2", "Wrong answer 1.3.1.3"}),
            "Subtopic 1.3");
    public static Question question1_3_2 = new Question("Question 1.3.2",
            "Right answer 1.3.2",
            Set.of(new String[]{"Wrong answer 1.3.2.1", "Wrong answer 1.3.2.2", "Wrong answer 1.3.2.3"}),
            "Subtopic 1.3");
    public static Question question1_3_3= new Question("Question 1.3.3",
            "Right answer 1.3.3",
            Set.of(new String[]{"Wrong answer 1.3.3.1", "Wrong answer 1.3.3.2", "Wrong answer 1.3.3.3"}),
            "Subtopic 1.3");


    // Question 2.1.1 LA FL -> second topic (LA) - first subtopic (Vectors) - first question -> for FileLoader
    // Question 2.2.1 LA FL -> second topic (LA) - second subtopic (Matrices) - first question -> for FileLoader
    public static Question question2_1_1_LA_FL = new Question("Let vectors v and w. v + w = (5, 1) and v - w = (1, 5). Compute v and w. ",
            "v = (3, 3) and w = (2, -2)",
            Set.of(new String[]{"v = (3, -3) and w = (2, -2)", "v = (5, 2) and w = (4, -3)", "v = (-3, 3) and w = (-2, -2)"}),
            "Vectors");
    public static Question question2_2_1_LA_FL = new Question("Is this matrix symmetric, skew symmetric or neither?\n[1, -3, 3]\n[-3, 4, -3]\n[3, 3, 0] ",
            "Neither",
            Set.of(new String[]{"Skew symmetric", "Symmetric"}),
            "Matrices");

    // Question 2.1.1 -> second topic - first subtopic - first question
    // Question 2.1.2 -> second topic - first subtopic - second question
    // Question 2.1.3 -> second topic - first subtopic - third question
    public static Question question2_1_1= new Question("Question 2.1.1",
            "Right answer 2.1.1",
            Set.of(new String[]{"Wrong answer 2.1.1.1", "Wrong answer 2.1.1.2", "Wrong answer 2.1.1.3"}),
            "Subtopic 2.1");
    public static Question question2_1_2 = new Question("Question 2.1.2",
            "Right answer 2.1.2",
            Set.of(new String[]{"Wrong answer 2.1.2.1", "Wrong answer 2.1.2.2", "Wrong answer 2.1.2.3"}),
            "Subtopic 2.1");
    public static Question question2_1_3 = new Question("Question 2.1.3",
            "Right answer 2.1.3",
            Set.of(new String[]{"Wrong answer 2.1.3.1", "Wrong answer 2.1.3.2", "Wrong answer 2.1.3.3"}),
            "Subtopic 2.1");

    // Question 2.2.1 -> second topic - second subtopic - first question
    // Question 2.2.2 -> second topic - second subtopic - second question
    public static Question question2_2_1 = new Question("Question 2.2.1",
            "Right answer 2.2.1",
            Set.of(new String[]{"Wrong answer 2.2.1.1", "Wrong answer 2.2.1.2", "Wrong answer 2.2.1.3"}),
            "Subtopic 2.2");
    public static Question question2_2_2 = new Question("Question 2.2.2",
            "Right answer 2.2.2",
            Set.of(new String[]{"Wrong answer 2.2.2.1", "Wrong answer 2.2.2.2", "Wrong answer 2.2.2.3"}),
            "Subtopic 2.2");

    // Question 2.3.1 -> second topic - third subtopic - first question
    // Question 2.3.2 -> second topic - third subtopic - second question
    // Question 2.3.3 -> second topic - third subtopic - third question
    public static Question question2_3_1 = new Question("Question 2.3.1",
            "Right answer 2.3.1",
            Set.of(new String[]{"Wrong answer 2.3.1.1", "Wrong answer 2.3.1.2", "Wrong answer 2.3.1.3"}),
            "Subtopic 2.3");
    public static Question question2_3_2 = new Question("Question 2.3.2",
            "Right answer 2.3.2",
            Set.of(new String[]{"Wrong answer 2.3.2.1", "Wrong answer 2.3.2.2", "Wrong answer 2.3.2.3"}),
            "Subtopic 2.3");
    public static Question question2_3_3= new Question("Question 2.3.3",
            "Right answer 2.3.3",
            Set.of(new String[]{"Wrong answer 2.3.3.1", "Wrong answer 2.3.3.2", "Wrong answer 2.3.3.3"}),
            "Subtopic 2.3");

    // Question 2.4.1 -> second topic - fourth subtopic - first question
    // Question 2.4.2 -> second topic - fourth subtopic - second question
    // Question null -> null
    public static Question question2_4_1 = new Question("Question 2.4.1",
            "Right answer 2.4.1",
            Set.of(new String[]{"Wrong answer 2.4.1.1", "Wrong answer 2.4.1.2", "Wrong answer 2.4.1.3"}),
            "Subtopic 2.4");
    public static Question question2_4_2 = new Question("Question 2.4.2",
            "Right answer 2.4.2",
            Set.of(new String[]{"Wrong answer 2.4.2.1", "Wrong answer 2.4.2.2", "Wrong answer 2.4.2.3"}),
            "Subtopic 2.4");
    public static Question nullQuestion = null;
}
