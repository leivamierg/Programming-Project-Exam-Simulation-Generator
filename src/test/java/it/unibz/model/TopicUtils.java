package it.unibz.model;

import java.util.ArrayList;
import static it.unibz.model.SubtopicUtils.*;
public class TopicUtils {
    // Topic 1 CSA FL -> first topic (CSA) -> for FileLoader
    // Topic 2 LA FL -> second topic (LA) -> for FileLoader
    private static List<Subtopic> subtopics1_CSA_FL = new ArrayList<>();
    subtopics1_CSA_FL.add(subtopic1_1_CSA_FL);
    subtopics1_CSA_FL.add(subtopic1_2_CSA_FL);
    static Topic topic1_CSA_FL = new Topic("Computer System Architecture", subtopics1_CSA_FL);

    private static List<Subtopic> subtopics2_LA_FL = new ArrayList<>();
    subtopics2_LA_FL.add(subtopic2_1_LA_FL);
    subtopics2_LA_FL.add(subtopic2_2_LA_FL);
    static Topic topic2_LA_FL = new Topic("Linear Algebra", subtopics2_LA_FL);

    // Topic 1 -> first topic
    // Topic 2 -> second topic
    private static List<Subtopic> subtopics1 = new ArrayList<>();
    subtopics1.add(subtopic1_1);
    subtopics1.add(subtopic1_2);
    subtopics1.add(subtopic1_3);
    static Topic topic1 = new Topic("Topic 1", subtopics1);

    private static List<Subtopic> subtopics2 = new ArrayList<>();
    subtopics2.add(subtopic2_1);
    subtopics2.add(subtopic2_2);
    subtopics2.add(subtopic2_3);
    subtopics2.add(subtopic2_4);
    static Topic topic2 = new Topic("Topic 2", subtopics2);
}
