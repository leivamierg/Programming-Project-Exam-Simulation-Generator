package it.unibz.model.implementations;

public record TestRegister(int testNumber, String totalTime, int numberOfQuestions, int correctlyAnsweredQuestions,
                int incorrectlyAnsweredQuestions, int blankQuestions, double overallScore, String topic,
                String[] subtopics) {

}
