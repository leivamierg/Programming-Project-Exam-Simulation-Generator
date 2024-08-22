package it.unibz.app.GUI.Controllers;

import java.io.IOException;
import java.time.LocalDate;
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
    private AnchorPane displayPane;
    @FXML
    private Label questionNumber, questionDisplay, date, Username;
    @FXML
    private RadioButton buttonA, buttonB, buttonC, buttonD;
    @FXML
    private Button backButton, nexButton;

    private int index;
    private List<Question> questions;
    private Question currQuestion;
    private DailyChallenge dailyChallenge;
    private String challengeDate;
    private boolean validAccess;

    /**
     * Initialize method which sets many private local fields and displays the first
     * question of the questions List
     */
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

    /**
     * Helper method which checks the last dailyChallenge done by the user date. And
     * then initializes the daily challenge field with a new daily challenge object.
     * If the user didn't do yesterday's daily challenge it breaks the daily streak
     */
    private void initializeDailyChallenge() {
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

    /**
     * Redirects the user to the mainMenu
     * 
     * @param event
     * @throws IOException
     */
    public void goBack(ActionEvent event) throws IOException {
        // decrease the index and re-initialize
        if (index != 0) {
            index--;

            updateQuestion();
        }
    }

    /**
     * Displays the next question, if any, by increasing the index field
     * 
     * @param event
     * @throws IOException
     */
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

    /**
     * Displays the previous question, if any, by decreasing the index field
     */
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

    /**
     * Helper method which updates each of the possible answers buttons according to
     * the current Question
     * 
     * @param map
     */
    private void updateButtons(Map<String, Character> map) {
        buttonA.setText("");
        buttonB.setText("");
        buttonC.setText("");
        buttonD.setText("");
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

    /**
     * Method that handles the selection of one of the answers and saves it in a Map
     * 
     * @param event
     */
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

    /**
     * Helper method which marks the already asnwered questions, if any.
     * 
     * @param map
     * @param questionStatement
     */
    private void displayAlreadyAnswered(Map<String, Character> map, String questionStatement) {
        if (map.containsKey(questionStatement)) {// if the questionstatemen is already a key of the map
            mark(map.get(questionStatement));
        }
    }

    /**
     * Helper method to mark one of the four possible answer methods
     * 
     * @param key
     */
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

    /**
     * Method which if every question was answered displays how many were wrong and
     * right and if the user gets today's daily challenge badge.
     * If not every question was answered an alert is shown asking to answer every
     * question
     * 
     * @param event
     */
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

    /**
     * Helper method which creates a List of all the marked answers of the user
     * 
     * @return
     */
    private List<String> generateLabelsList() {
        List<String> labels = new ArrayList<>();
        for (Question question : questions) {
            labels.add(Character
                    .toString(dailyChallenge.getQuestionStatementToAnswer().get(question.getQuestionStatement())));
        }
        return labels;
    }
}
