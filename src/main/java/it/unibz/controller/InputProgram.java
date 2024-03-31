package it.unibz.controller;

import it.unibz.model.FileLoading;
import it.unibz.model.Question;
import it.unibz.model.Subtopic;
import it.unibz.model.Topic;

import java.util.Scanner;

public class InputProgram {
    private Scanner scanner;

    public InputProgram()
    {
        scanner = new Scanner(System.in);
    }

    public void loadFile(FileLoading fileLoader)
    {
        System.out.println("Enter file name to load:");

        String fileName = scanner.nextLine();

        boolean loadedFile = fileLoader.loadFile(fileName);

        if (loadedFile)
        {
            System.out.println("Success");
        } else {
            System.out.println("Fail");
        }
    }

    public void displayTopicInformation(Topic topic)
    {
        System.out.println("Topic Name: " + topic.getName());
        System.out.println("Number of Subtopics: " + topic.getNumberOfSubtopics());
        System.out.println("Subtopics:");

        for (Subtopic subtopic : topic.getSubtopics())
        {
            System.out.println(subtopic.getName());
        }
    }

    public void displaySubtopicInfo(Subtopic subtopic)
    {
        System.out.println("Subtopic Name: " + subtopic.getName());
        System.out.println("Total Questions: " + subtopic.getNumberOfQuestions());
        System.out.println("Available Questions: " + subtopic.getNumberOfAvbQuestions());
        System.out.println("Wrong Questions: " + subtopic.getNumberOfWrongQuestions());
    }

    public void displayQuestion(Question question) {
        System.out.println(question.getQuestion());
        System.out.println("Possible Answers:");

        for (String answer : question.getAnswers())
        {
            System.out.println(answer);
        }
    }
}
