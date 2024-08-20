package it.unibz.model.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unibz.model.interfaces.DailyChallengeInt;
import it.unibz.model.interfaces.ModelInt;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DailyChallenge implements DailyChallengeInt {

    private List<Question> questions;
    private User user;
    private ModelInt model;
    private Map<Question, Map<String, Character>> questionToShuffleMap = new HashMap<>();
    private Map<Question, Character> questionToCorrectAnswer = new HashMap<>();

    //
    private Map<String, Character> questionStatementToAnswer = new HashMap();

    public DailyChallenge(List<Question> questions, User user, ModelInt model) {
        this.questions = questions;
        this.user = user;
        this.model = model;
        prepareShuffleMaps();
    }

    // Initialise a single shuffled map (otherwise it will shuffle the answers over
    // and over)
    private void prepareShuffleMaps() {
        for (Question question : questions) {
            Map<String, Character> shuffleMap = question.getShuffleMap();
            questionToShuffleMap.put(question, shuffleMap);
            char correctAnswerLabel = question.getCorrectAnswerLabel(shuffleMap);
            questionToCorrectAnswer.put(question, correctAnswerLabel);
        }
    }

    public String GUICheckAnswers(List<String> userAnswers) {
        int correctAnswers = 0;
        String results = "";

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String userAnswer = userAnswers.get(i).toUpperCase().trim();

            Map<String, Character> shuffleMap = questionToShuffleMap.get(question); // rmb to check whether to remove it
                                                                                    // or not
            char correctAnswerLabel = questionToCorrectAnswer.get(question);

            /*
             * for debugging
             * System.out.println("Question " + (i + 1) + ":");
             * System.out.println("Question Statement: " + question.getQuestionStatement());
             * System.out.println("Shuffled Answers: " + shuffleMap);
             * System.out.println("Correct Answer Label: " + correctAnswerLabel);
             * System.out.println("User Answer: " + userAnswer);
             */

            if (userAnswer.length() == 1 && userAnswer.charAt(0) == correctAnswerLabel) {
                correctAnswers++;
                results = results + "Question N° " + (i + 1) + " is correct" + System.lineSeparator();
            } else {
                results = results + "Question N° " + (i + 1) + " is wrong" + System.lineSeparator();
            }
        }

        results = results + "Total correct answers: " + correctAnswers + " out of 5" + System.lineSeparator();
        if (correctAnswers >= 3) {
            results = results + "Congratulations! You have earned today's badge";
            checkPassed(true);
        } else {
            results = results + "That's unfortunate, try again next time";
            checkPassed(false);
        }

        return results;
    }

    public boolean checkAnswers(List<String> userAnswers) {
        int correctAnswers = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String userAnswer = userAnswers.get(i).toUpperCase().trim();

            Map<String, Character> shuffleMap = questionToShuffleMap.get(question); // rmb to check whether to remove it
                                                                                    // or not
            char correctAnswerLabel = questionToCorrectAnswer.get(question);

            /*
             * for debugging
             * System.out.println("Question " + (i + 1) + ":");
             * System.out.println("Question Statement: " + question.getQuestionStatement());
             * System.out.println("Shuffled Answers: " + shuffleMap);
             * System.out.println("Correct Answer Label: " + correctAnswerLabel);
             * System.out.println("User Answer: " + userAnswer);
             */

            if (userAnswer.length() == 1 && userAnswer.charAt(0) == correctAnswerLabel) {
                correctAnswers++;
            } else {
                System.out.println(
                        "Wrong Answer: " + userAnswer + " does not match Correct Label: " + correctAnswerLabel);
            }
        }

        System.out.println("Total correct answers: " + correctAnswers);
        return correctAnswers >= 3; // at least 3 correct answers to get the badge
    }

    public void startDailyChallenge() {
        if (user == null) {
            System.out.println("No user logged in");
            return;
        }

        // List<String> userAnswers = getUserAnswers();

        // boolean passed = checkAnswers(userAnswers);

        // checkPassed(passed);
        // updateChallengeDate();
        // saveUserData();
    }

    // Helpers to improve clarity

    private List<String> getUserAnswers() {
        List<String> userAnswers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        for (Question question : questions) {
            String userAnswer;
            while (true) {
                System.out.println(question.getQuestionAndAnswers());
                System.out.print("Your answer: ");
                userAnswer = scanner.nextLine().toUpperCase().trim();

                if (userAnswer.length() == 1 && (userAnswer.charAt(0) == 'A' || userAnswer.charAt(0) == 'B'
                        || userAnswer.charAt(0) == 'C' || userAnswer.charAt(0) == 'D') && userAnswer.charAt(0) != ' ') {
                    break;
                } else {
                    System.out.println("Invalid answer");
                }
            }
            userAnswers.add(userAnswer);
        }
        return userAnswers;
    }

    private void checkPassed(boolean passed) {
        if (passed) {
            user.addBadge(new Badge("Daily Challenge Winner", "Awarded for passing a daily challenge."));
            System.out.println("Congratulations! You've earned a badge.");
        } else {
            System.out.println("Better luck next time!");
        }
    }

    private void updateChallengeDate() {
        String today = LocalDate.now().toString();
        if (user.getChallengeDate() == null || user.getChallengeDate().isEmpty()) {
            user.setChallengeDate(today);
            user.resetStreak();
        } else {
            try {
                LocalDate lastChallengeDate = LocalDate.parse(user.getChallengeDate(),
                        DateTimeFormatter.ISO_LOCAL_DATE);
                if (lastChallengeDate.plusDays(1).toString().equals(today)) {
                    user.incrementStreak();
                } else {
                    user.resetStreak();
                }
                user.setChallengeDate(today);
            } catch (DateTimeParseException e) {
                System.out.println("Error with oparsing date.");
                user.setChallengeDate(today);
                user.resetStreak();
            }
        }
    }

    public void saveUserData() {
        updateChallengeDate();//
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("src/main/resources/challenge/" + user.getUsername() + ".json"), user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    public Map<Question, Map<String, Character>> getQuestionToShuffleMap() {
        return questionToShuffleMap;
    }

    public Map<String, Character> getQuestionStatementToAnswer() {
        return questionStatementToAnswer;
    }

    public Map<Question, Character> getQuestionToCorrectAnswer() {
        return questionToCorrectAnswer;
    }
}
