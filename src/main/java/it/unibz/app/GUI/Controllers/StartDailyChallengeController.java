package it.unibz.app.GUI.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.unibz.app.App;
import it.unibz.model.implementations.DailyChallenge;
import it.unibz.model.implementations.Question;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class StartDailyChallengeController {
    @FXML
    AnchorPane displayPane;
    @FXML
    Label questionNumber, questionDisplay, date, Username;
    @FXML
    RadioButton buttonA, buttonB, buttonC, buttonD;
    @FXML
    Button backButton, nexButton;

    int index;
    List<Question> questions;
    Question currQuestion;
    DailyChallenge dailyChallenge;
    String challengeDate;
    boolean validAccess;

    public void initialize() {
        new Thread() {
            public void run() {
                initializeDailyChallenge();
                index = 0;
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (validAccess) {
                            date.setText(challengeDate);
                            Username.setText(App.user.getUsername());
                            currQuestion = questions.get(index);
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    updateQuestion();
                                }
                            });
                        }
                    }
                });
            }
        }.start();
    }

    public void initializeDailyChallenge() {
        challengeDate = App.user.getChallengeDate();

        if (challengeDate == null || challengeDate.isEmpty()) {
            // TODO:
            App.user.setChallengeDate(LocalDate.now().toString());
            App.user.resetStreak();
            questions = App.actionsController.getModel().getRandomQuestions(5);
            dailyChallenge = new DailyChallenge(questions, App.user, App.actionsController.getModel());
            validAccess = true;
        } else {
            try {

                questions = App.actionsController.getModel().getRandomQuestions(5);
                dailyChallenge = new DailyChallenge(questions, App.user, App.actionsController.getModel());
                // dailyChallenge.startDailyChallenge();
                validAccess = true;

            } catch (DateTimeParseException e) {
                System.out.println("No date parsed");
                return;
            }
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        // decrease the index and re-initialize
        if (index != 0) {
            index--;

            updateQuestion();
        }
    }

    public void goNext(ActionEvent event) throws IOException {
        // increase the index and re-initialize
        if (index + 1 != questions.size()) {
            new Thread() {
                @Override
                public void run() {
                    index++;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestion();
                        }
                    });
                }
            }.start();
        }
    }

    public void updateQuestion() {
        currQuestion = questions.get(index);

        questionDisplay.setText(currQuestion.getQuestionStatement());
        questionNumber.setText(Integer.toString(index + 1));
        // clearinf the previously marked answer when the next or previous question is
        // accesed
        buttonA.setSelected(false);
        buttonB.setSelected(false);
        buttonC.setSelected(false);
        buttonD.setSelected(false);
        // check if the questions was already answered, if yes, mark its corresponding
        // radio button
        updateButtons(dailyChallenge.getQuestionToShuffleMap().get(currQuestion));
        displayAlreadyAnswered(dailyChallenge.getQuestionStatementToAnswer(), currQuestion.getQuestionStatement());
    }

    private void updateButtons(Map<String, Character> map) {
        for (Entry<String, Character> entry : map
                .entrySet()) {
            if (entry.getValue() == 'A') {
                buttonA.setText(entry.getKey());
            } else if (entry.getValue() == 'B') {
                buttonB.setText(entry.getKey());
            } else if (entry.getValue() == 'C') {
                buttonC.setText(entry.getKey());
            } else if (entry.getValue() == 'D') {
                buttonD.setText(entry.getKey());
            }
        }
    }

    public void selectAnswer(ActionEvent event) {
        for (Node child : displayPane.getChildren()) {
            if (child instanceof RadioButton && ((RadioButton) child).isSelected()) {

                if (((RadioButton) child).equals(buttonA)) {
                    dailyChallenge.getQuestionStatementToAnswer().put(currQuestion.getQuestionStatement(), 'A');
                } else if (((RadioButton) child).equals(buttonB)) {
                    dailyChallenge.getQuestionStatementToAnswer().put(currQuestion.getQuestionStatement(), 'B');
                } else if (((RadioButton) child).equals(buttonC)) {
                    dailyChallenge.getQuestionStatementToAnswer().put(currQuestion.getQuestionStatement(), 'C');
                } else if (((RadioButton) child).equals(buttonD)) {
                    dailyChallenge.getQuestionStatementToAnswer().put(currQuestion.getQuestionStatement(), 'D');
                }

            }
        }
    }

    public void displayAlreadyAnswered(Map<String, Character> map, String questionStatement) {
        if (map.containsKey(questionStatement)) {// if the questionstatemen is already a key of the map
            mark(map.get(questionStatement));
        }
    }

    private void mark(Character key) {
        if (key.equals('A')) {
            buttonA.setSelected(true);
        } else if (key.equals('B')) {
            buttonB.setSelected(true);
        } else if (key.equals('C')) {
            buttonC.setSelected(true);
        } else if (key.equals('D')) {
            buttonD.setSelected(true);
        }
    }

    private int getAnsweredQuestions() {
        Map<String, Character> map = App.currentSimulation.getQuestionStatementToAnswer();
        int i = 0;
        for (Entry<String, Character> entry : map.entrySet()) {
            if (!entry.getValue().equals(' ')) {
                i++;
            }
        }
        return i;
    }

    public void finishDailyChallenge(ActionEvent event) {
        if (dailyChallenge.getQuestionStatementToAnswer().size() == 5) {
            List<String> answers = generateLabelsList();
            String results = dailyChallenge.GUICheckAnswers(answers);

            // send the message to the next scene
            DailyChallengeResultsController.message = results;

            // change of scene
            try {
                dailyChallenge.saveUserData();//
                App.setRoot("dailyChallengeResults");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Not all questions were answered");
            alert.setHeaderText("In order to complete the daily challenge, all questions must be answered");
            alert.setContentText("Please complete the "
                    + (5 - dailyChallenge.getQuestionStatementToAnswer().size() + " questions left"));
            alert.show();
        }

    }

    public List<String> generateLabelsList() {
        List<String> labels = new ArrayList<>();
        for (Question question : questions) {
            labels.add(Character
                    .toString(dailyChallenge.getQuestionStatementToAnswer().get(question.getQuestionStatement())));
        }
        return labels;
    }
}
