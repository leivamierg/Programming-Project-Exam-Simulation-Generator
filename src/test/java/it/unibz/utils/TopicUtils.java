package it.unibz.utils;

import it.unibz.model.implementations.Subtopic;
import it.unibz.model.implementations.Topic;

import java.util.HashSet;
import java.util.Set;

import static it.unibz.utils.SubtopicUtils.*;
public class TopicUtils {
    // Topic 1 CSA FL -> first topic (CSA) -> for FileLoader
    // Topic 2 LA FL -> second topic (LA) -> for FileLoader
    public static Topic topic1_CSA_FL;
    public static Topic topic2_LA_FL;

    // Topic 1 -> first topic
    // Topic 2 -> second topic
    // Topic null -> null;
    public static Topic topic1;
    public static Topic topic2;
    public static Topic nullTopic;

    public static void init() {
        SubtopicUtils.init();
        // Topic 1 CSA FL
        Set<Subtopic> subtopics1_CSA_FL = new HashSet<>();
        subtopics1_CSA_FL.add(subtopic1_1_CSA_FL);
        subtopics1_CSA_FL.add(subtopic1_2_CSA_FL);
        topic1_CSA_FL = new Topic("Computer System Architecture", subtopics1_CSA_FL);

        // Topic 2 LA FL
        Set<Subtopic> subtopics2_LA_FL = new HashSet<>();
        subtopics2_LA_FL.add(subtopic2_1_LA_FL);
        subtopics2_LA_FL.add(subtopic2_2_LA_FL);
        topic2_LA_FL = new Topic("Linear Algebra", subtopics2_LA_FL);

        // Topic 1
        Set<Subtopic> subtopics1 = new HashSet<>();
        subtopics1.add(subtopic1_1);
        subtopics1.add(subtopic1_2);
        subtopics1.add(subtopic1_3);
        topic1 = new Topic("Topic 1", subtopics1);

        // Topic 2
        Set<Subtopic> subtopics2 = new HashSet<>();
        subtopics2.add(subtopic2_1);
        subtopics2.add(subtopic2_2);
        subtopics2.add(subtopic2_3);
        subtopics2.add(subtopic2_4);
        topic2 = new Topic("Topic 2", subtopics2);
        // Topic null
        nullTopic = null;
        setSubtopicToTopicReference();
    }

    private static void setSubtopicToTopicReference() {
        subtopic1_1_CSA_FL.setTopicReference(topic1_CSA_FL);
        subtopic1_2_CSA_FL.setTopicReference(topic1_CSA_FL);

        subtopic1_1.setTopicReference(topic1);
        subtopic1_2.setTopicReference(topic1);
        subtopic1_3.setTopicReference(topic1);

        subtopic2_1_LA_FL.setTopicReference(topic2_LA_FL);
        subtopic2_2_LA_FL.setTopicReference(topic2_LA_FL);

        subtopic2_1.setTopicReference(topic2);
        subtopic2_2.setTopicReference(topic2);
        subtopic2_3.setTopicReference(topic2);
        subtopic2_4.setTopicReference(topic2);
    }
}