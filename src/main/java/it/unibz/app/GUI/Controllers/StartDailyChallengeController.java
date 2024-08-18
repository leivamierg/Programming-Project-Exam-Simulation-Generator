package it.unibz.app.GUI.Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import it.unibz.app.App;
import it.unibz.model.implementations.DailyChallenge;
import it.unibz.model.implementations.Question;

public class StartDailyChallengeController {
    public void initialize() {
        String challengeDate = App.user.getChallengeDate();

        if (challengeDate == null || challengeDate.isEmpty()) {
            // TODO:
            App.user.setChallengeDate(LocalDate.now().toString());
            App.user.resetStreak();
            List<Question> questions = App.actionsController.getModel().getRandomQuestions(5);
            DailyChallenge dailyChallenge = new DailyChallenge(questions, App.user, App.actionsController.getModel());
            dailyChallenge.startDailyChallenge();
        } else {
            try {
                LocalDate lastChallengeDate = LocalDate.parse(App.user.getChallengeDate(),
                        DateTimeFormatter.ISO_LOCAL_DATE);
                if (lastChallengeDate.equals(LocalDate.now())) {
                    // TODO:
                    System.out.println(
                            "You've already completed the daily challenge for today. Please come back tomorrow.");
                    return;
                } else {
                    List<Question> questions = App.actionsController.getModel().getRandomQuestions(5);
                    DailyChallenge dailyChallenge = new DailyChallenge(questions, App.user,
                            App.actionsController.getModel());
                    dailyChallenge.startDailyChallenge();
                }
            } catch (DateTimeParseException e) {
                System.out.println("No date parsed");
                return;
            }
        }
    }
}
