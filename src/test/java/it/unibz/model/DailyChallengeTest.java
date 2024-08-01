package it.unibz.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unibz.model.implementations.*;
import it.unibz.model.interfaces.ModelInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class DailyChallengeTest {

    private DailyChallenge dailyChallenge;
    private User user;
    private ModelInt model;
    private List<Question> questions;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp()
    {
        user = new User("VioTest");
        model = new Model();
        questions = model.getRandomQuestions(5);
        dailyChallenge = new DailyChallenge(questions, user, model);
    }

    @Test
    void testInvalidLength()
    {
        List<String> userAnswers = new ArrayList<>();

        for (Question question : questions)
        {
            userAnswers.add("PO");
        }
        assertFalse(dailyChallenge.checkAnswers(userAnswers));
    }

    @Test
    void testCorrectAnswer()
    {
        List<String> userAnswers = new ArrayList<>();

        for (Question question : questions)
        {
            Map<String, Character> shuffleMap = question.getShuffleMap();
            char correctAnswer = question.getCorrectAnswerLabel(shuffleMap);
            userAnswers.add(String.valueOf(correctAnswer));
        }
        assertTrue(dailyChallenge.checkAnswers(userAnswers));
    }

    @Test
    void testWrongLabel()
    {
        List<String> userAnswers = new ArrayList<>();

        for (Question question : questions)
        {
            userAnswers.add("X");
        }
        assertFalse(dailyChallenge.checkAnswers(userAnswers));
    }

    @Test
    void testAtLeastThreeCorrectAnswers()
    {
        List<String> userAnswers = new ArrayList<>();

        int counterCorrectAnswers = 0;

        for (Question question : questions)
        {
            Map<String, Character> shuffleMap = question.getShuffleMap();
            char correctAnswer = question.getCorrectAnswerLabel(shuffleMap);

            if(counterCorrectAnswers < 3)
            {
                userAnswers.add(String.valueOf(correctAnswer));
                counterCorrectAnswers++;
            } else {
                userAnswers.add("x"); // I must add a wrong answer
            }
        }

        assertTrue(dailyChallenge.checkAnswers(userAnswers), "Pass with at least 3 correct answers");
    }

    @Test
    void testLessThanThreeCorrectAnswers()
    {
        List<String> userAnswers = new ArrayList<>();

        int counterCorrectAnswers = 0;

        for (Question question : questions)
        {
            Map<String, Character> shuffleMap = question.getShuffleMap();
            char correctAnswer = question.getCorrectAnswerLabel(shuffleMap);

            if(counterCorrectAnswers < 2)
            {
                userAnswers.add(String.valueOf(correctAnswer));
                counterCorrectAnswers++;
            } else {
                userAnswers.add("x"); // I must add a wrong answer
            }
        }

        assertFalse(dailyChallenge.checkAnswers(userAnswers), "Fail with less than 3 correct answers");
    }

    @Test
    void testLoggedInUser()
    {
        dailyChallenge = new DailyChallenge(questions, null, model);
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        dailyChallenge.startDailyChallenge();

        System.setOut(originalOut);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("No user logged in"), "Expected 'No user logged in'");
    }

    @Test
    void testUserData()
    {
        user.setChallengeDate(LocalDate.now().toString());
        dailyChallenge.saveUserData();
        user.incrementStreak();

        assertEquals(LocalDate.now().toString(), user.getChallengeDate());
        assertEquals(1, user.getStreak());

        user.resetStreak();
        dailyChallenge.saveUserData();

        assertEquals(0, user.getStreak());
    }

    @Test
    void testJsonFile() throws IOException {
        user.setChallengeDate(LocalDate.now().toString());
        user.incrementStreak(); //it becomes 1

        Path filePath = Paths.get("src/main/resources/challenge/" + user.getUsername() + ".json");
        File userFile = filePath.toFile();

        dailyChallenge.saveUserData();

        assertTrue(userFile.exists(), "User data file should be created.");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(userFile);

        assertEquals(user.getChallengeDate(), rootNode.get("challengeDate").asText(), "Challenge date should match.");
        assertEquals(user.getStreak(), rootNode.get("streak").asInt(), "Streak should match.");

        user.getStreak(); //should be 1
        dailyChallenge.saveUserData();

        rootNode = mapper.readTree(userFile);
        assertEquals(user.getStreak(), rootNode.get("streak").asInt(), "Streak should match after reset.");
    }

}


