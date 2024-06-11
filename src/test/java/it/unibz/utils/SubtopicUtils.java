package it.unibz.utils;

import it.unibz.model.implementations.Question;
import it.unibz.model.implementations.Subtopic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static it.unibz.utils.QuestionUtils.*;
public class SubtopicUtils {
    // Subtopic 1.1 CSA FL -> first topic (CSA) - first subtopic (Binary Arithmetic) -> for FileLoader
    // Subtopic 1.2 CSA FL -> first topic (CSA) - second subtopic (Sequential and Combinatorial circuits) -> for FileLoader
    public static Subtopic subtopic1_1_CSA_FL;
    public static Subtopic subtopic1_2_CSA_FL;
    
    // Subtopic 1.1 -> first topic - first subtopic
    // Subtopic 1.2 -> first topic - second subtopic
    // Subtopic 1.3 -> first topic - third subtopic
    public static Subtopic subtopic1_1;
    public static Subtopic subtopic1_2;
    public static Subtopic subtopic1_3;

    // Subtopic 2.1 LA FL -> second topic (LA) - first subtopic (Vectors) -> for FileLoader
    // Subtopic 2.2 LA FL -> second topic (LA) - second subtopic (Matrices) -> for FileLoader
    public static Subtopic subtopic2_1_LA_FL;
    public static Subtopic subtopic2_2_LA_FL;

    // Subtopic 2.1 -> second topic - first subtopic
    // Subtopic 2.2 -> second topic - second subtopic
    // Subtopic 2.3 -> second topic - third subtopic
    // Subtopic 2.4 -> second topic - fourth subtopic
    // Subtopic null -> null
    public static Subtopic subtopic2_1;
    public static Subtopic subtopic2_2;
    public static Subtopic subtopic2_3;
    public static Subtopic subtopic2_4;
    public static Subtopic nullSubtopic;
    
    public static void init() {
        // Subtopic 1.1 CSA FL
        List<Question> questions1_1_CSA_FL = new ArrayList<>();
        questions1_1_CSA_FL.add(question1_1_1_CSA_FL);
        subtopic1_1_CSA_FL = new Subtopic("Binary Arithmetic", questions1_1_CSA_FL);
        
        // Subtopic 1.2 CSA FL 
        List<Question> questions1_2_CSA_FL = new ArrayList<>();
        questions1_2_CSA_FL.add(question1_2_1_CSA_FL);
        subtopic1_2_CSA_FL = new Subtopic("Sequential and Combinatorial circuits", questions1_2_CSA_FL);
        
        // Subtopic 1.1
        List<Question> questions1_1 = new ArrayList<>();
        questions1_1.add(question1_1_1);
        questions1_1.add(question1_1_2);
        subtopic1_1 = new Subtopic("Subtopic 1.1", questions1_1);
        
        // Subtopic 1.2
        List<Question> questions1_2 = new ArrayList<>();
        questions1_2.add(question1_2_1);
        questions1_2.add(question1_2_2);
        subtopic1_2 = new Subtopic("Subtopic 1.2", questions1_2);
        
        // Subtopic 1.3
        List<Question> questions1_3 = new ArrayList<>();
        questions1_3.add(question1_3_1);
        questions1_3.add(question1_3_2);
        questions1_3.add(question1_3_3);
        subtopic1_3 = new Subtopic("Subtopic 1.3", questions1_3);
        
        // Subtopic 2.1 LA FL
        List<Question> questions2_1_LA_FL = new ArrayList<>();
        questions2_1_LA_FL.add(question2_1_1_LA_FL);
        subtopic2_1_LA_FL = new Subtopic("Vectors", questions2_1_LA_FL);

        // Subtopic 2.2 LA FL
        List<Question> questions2_2_LA_FL = new ArrayList<>();
        questions2_2_LA_FL.add(question2_2_1_LA_FL);
        subtopic2_2_LA_FL = new Subtopic("Matrices", questions2_2_LA_FL);
        
        // Subtopic 2.1
        List<Question> questions2_1 = new ArrayList<>();
        questions2_1.add(question2_1_1);
        questions2_1.add(question2_1_2);
        questions2_1.add(question2_1_3);
        subtopic2_1 = new Subtopic("Subtopic 2.1", questions2_1);

        // Subtopic 2.2
        List<Question> questions2_2 = new ArrayList<>();
        questions2_2.add(question2_2_1);
        questions2_2.add(question2_2_2);
        subtopic2_2 = new Subtopic("Subtopic 2.2", questions2_2);

        // Subtopic 2.3
        List<Question> questions2_3 = new ArrayList<>();
        questions2_3.add(question2_3_1);
        questions2_3.add(question2_3_2);
        questions2_3.add(question2_3_3);
        subtopic2_3 = new Subtopic("Subtopic 2.3", questions2_3);

        // Subtopic 2.4
        List<Question> questions2_4 = new ArrayList<>();
        questions2_4.add(question2_4_1);
        questions2_4.add(question2_4_2);
        subtopic2_4 = new Subtopic("Subtopic 2.4", questions2_4);

        // Subtopic null
        nullSubtopic = null;
    }
}