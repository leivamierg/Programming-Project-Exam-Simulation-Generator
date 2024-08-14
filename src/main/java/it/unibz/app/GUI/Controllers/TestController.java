package it.unibz.app.GUI.Controllers;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import it.unibz.app.App;
import it.unibz.model.implementations.Question;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

public class TestController {
    @FXML
    Label questionNumber, questionStatement, timer;
    @FXML
    RadioButton buttonA, buttonB, buttonC, buttonD;
    @FXML
    Button backButton, nexButton;
    @FXML
    AnchorPane displayPane;

    static int index = 0; // starts as 0
    static Question currQuestion;
    static boolean newSim = true;

    public void initialize() {
        new Thread() {
            @Override
            public void run() {

                if (newSim) {
                    index = 0;
                    newSim = false;
                    App.currentSimulation.start();
                }

                App.currentSimulation.setCurrentQuestion(App.currentSimulation.getAllQuestions().get(index));
                currQuestion = App.currentSimulation.getCurrentQuestion(); // update the current question
                // synchronizing the threads or else sometimes the static properties are
                // detected as 0 and
                // null
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        questionStatement.setText(currQuestion.getQuestionStatement());
                        questionNumber.setText(Integer.toString(index + 1));
                        timer.setText(Integer.toString(App.currentSimulation.getTimer().getRemainingTime()));

                        updateButtons(App.currentSimulation.getQuestionToShuffledAnswers().get(currQuestion));
                        // System.out.println(App.currentSimulation.getQuestionStatementToAnswer());//
                        // works
                        displayAlreadyAnswered(App.currentSimulation.getQuestionStatementToAnswer(),
                                currQuestion.getQuestionStatement());

                        // System.out.println(getAnsweredQuestions());//
                    }
                });
            }
        }.start();
    }

    public void goBack(ActionEvent event) throws IOException {
        // decrease the index and re-initialize
        if (index != 0) {
            index--;
            App.setRoot("test");
        }
    }

    public void goNext(ActionEvent event) throws IOException {
        // increase the index and re-initialize
        if (index + 1 != App.currentSimulation.getNumberOfQuestions()) {
            index++;
            App.setRoot("test");
        }
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
                    App.currentSimulation.answer(currQuestion, 'A');
                } else if (((RadioButton) child).equals(buttonB)) {
                    App.currentSimulation.answer(currQuestion, 'B');
                } else if (((RadioButton) child).equals(buttonC)) {
                    App.currentSimulation.answer(currQuestion, 'C');
                } else if (((RadioButton) child).equals(buttonD)) {
                    App.currentSimulation.answer(currQuestion, 'D');
                }

            }
        }
    }

    public void finishSimulation(ActionEvent event) throws IOException {
        if (getAnsweredQuestions() == App.currentSimulation
                .getNumberOfQuestions()) {
            // finish the shit
            System.out.println("simulation completed! FULL");
            App.setRoot("mainMenu");
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Questions left on blank");
            alert.setHeaderText("You didn't answer " + (App.currentSimulation.getNumberOfQuestions()
                    - getAnsweredQuestions()) + " questions");
            alert.setContentText("Are you sure you want to finish the current simulation?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                System.out.println("simulation completed! EMPTY");
                App.setRoot("mainMenu");
            }
        }

        newSim = true;
    }

    // checks if the question was already answered, if yes, marks its key in the
    // initialization
    public void displayAlreadyAnswered(Map<String, Character> map, String questionStatement) {
        if (map.containsKey(questionStatement)) {// if the questionstatemen is already a key of the map
            mark(map.get(questionStatement));
        } else {
            buttonA.setSelected(false);
            buttonB.setSelected(false);
            buttonC.setSelected(false);
            buttonD.setSelected(false);
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
}
