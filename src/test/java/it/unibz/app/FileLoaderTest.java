package it.unibz.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import it.unibz.model.FileLoader;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Unit test for FileLoader class.
 */
public class FileLoaderTest {
    private final String inputBank = "src/test/resources/";
    @Test
    public void checkLoadCSABank() {
        FileLoader fileLoader = new FileLoader();
        Topic producedTopic = fileLoader.loadFile(inputBank + "input_csa_bank_test.json");
        Topic expectedTopic = createExpectedCSATopic();
        assertEquals(expectedTopic, producedTopic);
    }

    @Test
    public void checkLoadBank() {
        FileLoader fileLoader = new FileLoader();
        List<Topic> producedBank = fileLoader.loadBank(inputBank);
        List<Topic> expectedBank = new ArrayList<>();
        Topic topic1 = createExpectedCSATopic();
        Topic topic2 = createExpectedLATopic();
        expectedBank.add(topic1);
        expectedBank.add(topic2);
        assertEquals(expectedBank, producedBank);
    }

    private Topic createExpectedCSATopic() {
        List<Question> questions1 = new ArrayList<>();
        List<Question> questions2 = new ArrayList<>();
        Question question1 = new Question("What happens to the binary representation of a number when it gets multiplied by 2?",
                "The bits get shifted to the left",
                new String[]{"The bits get flipped", "The bits get shifted to the right", "The bits are shuffled"},
                "Binary Arithmetic");
        Question question2 = new Question("A Set-Reset flip-flop has the purpose of:",
                "memorizing one bit of information",
                new String[]{"setting or resetting an external circuit", "memorizing a word of N bits", "generating a clock signal"},
                "Sequential and Combinatorial circuits");
        questions1.add(question1);
        questions2.add(question2);
        List<Subtopic> subtopics = new ArrayList<>();
        Subtopic subtopic1 = new Subtopic("Binary arithmetics", questions1);
        Subtopic subtopic2 = new Subtopic("Sequential and Combinatorial circuits", questions2);
        subtopics.add(subtopic1);
        subtopics.add(subtopic2);
        return new Topic("Computer System Architecture", subtopics);
    }

    private Topic createExpectedLATopic() {
        List<Question> questions1 = new ArrayList<>();
        List<Question> questions2 = new ArrayList<>();
        Question question1 = new Question("Let vectors v and w. v + w = (5, 1) and v - w = (1, 5). Compute v and w. ",
                "v = (3, 3) and w = (2, -2)",
                new String[]{"v = (3, -3) and w = (2, -2)", "v = (5, 2) and w = (4, -3)", "v = (-3, 3) and w = (-2, -2)"},
                "Vectors");
        Question question2 = new Question("Is this matrix symmetric, skew symmetric or neither?\n[1, -3, 3]\n[-3, 4, -3]\n[3, 3, 0] ",
                "Neither",
                new String[]{"Skew symmetric", "Symmetric"},
                "Matrices");
        questions1.add(question1);
        questions2.add(question2);
        List<Subtopic> subtopics = new ArrayList<>();
        Subtopic subtopic1 = new Subtopic("Vectors", questions1);
        Subtopic subtopic2 = new Subtopic("Matrices", questions2);
        subtopics.add(subtopic1);
        subtopics.add(subtopic2);
        return new Topic("Linear Algebra", subtopics);
    }
}

