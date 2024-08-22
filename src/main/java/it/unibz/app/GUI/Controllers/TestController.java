package it.unibz.app.GUI.Controllers;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import it.unibz.app.App;
import it.unibz.model.implementations.ExamTimer;
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

/**
 * By far the most complex class of the controllers, and it handles the test
 * events, inlcluding: marking answers, going to the next questions, finishing
 * the simulation, etc.
 */
public class TestController {
    @FXML
    private Label questionNumber, questionStatement, time;
    @FXML
    private RadioButton buttonA, buttonB, buttonC, buttonD;
    @FXML
    private Button backButton, nexButton;
    @FXML
    private AnchorPane displayPane;

    static int index = 0; // starts as 0
    static Question currQuestion;
    static boolean newSim = true;

    // timer fields
    private long mins, secs, hrs, totalSecs = 0;

    long initialTime;

    public static Timer simTimer;
    //

    /**
     * Initialize method to start the Timer and display the first question
     */
    @FXML
    public void initialize() {
        new Thread() {
            @Override
            public void run() {
                if (newSim) {
                    index = 0;
                    newSim = false;
                    App.currentSimulation.start();
                    setTimer();
                }
                updateQuestion();
            }
        }.start();
    }

    /**
     * Method to go back to the previous question, if any, by updating the index
     * field and calling the update question method
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
     * Method to go back to the next question, if any, by updating the index
     * field and calling the update question method
     * 
     * @param event
     * @throws IOException
     */
    public void goNext(ActionEvent event) throws IOException {
        // increase the index and re-initialize
        if (index + 1 != App.currentSimulation.getNumberOfQuestions()) {
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
     * Helper method which displays the question corresponding to the current value
     * of the index field
     */
    private void updateQuestion() {
        new Thread() {
            @Override
            public void run() {
                App.currentSimulation.setCurrentQuestion(App.currentSimulation.getAllQuestions().get(index));
                currQuestion = App.currentSimulation.getCurrentQuestion(); // update the current question
                // synchronizing the threads or else sometimes the static properties are
                // detected as 0 and null
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        questionStatement.setText(currQuestion.getQuestionStatement());
                        questionNumber.setText(Integer.toString(index + 1));
                        // clearinf the previously marked answer when the next or previous question is
                        // accesed
                        buttonA.setSelected(false);
                        buttonB.setSelected(false);
                        buttonC.setSelected(false);
                        buttonD.setSelected(false);
                        // check if the questions was already answered, if yes, mark its corresponding
                        // radio button
                        updateButtons(App.currentSimulation.getQuestionToShuffledAnswers().get(currQuestion));
                        displayAlreadyAnswered(App.currentSimulation.getQuestionStatementToAnswer(),
                                currQuestion.getQuestionStatement());
                    }
                });

            }
        }.start();
    }

    /*
     * Helper method to change the text of the different buttons showing the
     * possible answers
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
     * Method which detects the selection of an answer to the current question which
     * calls the old answer() method of the Model class
     * 
     * @param event
     */
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

    /**
     * Method to finish the simulation, move to the Statsdisplay.fxml and save the
     * results int the stats and history data structures. If not every question was
     * answered it asks the user if he really wants to continue.
     * 
     * @param event
     * @throws IOException
     */
    public void finishSimulation(ActionEvent event) throws IOException {
        if (getAnsweredQuestions() == App.currentSimulation
                .getNumberOfQuestions()) {
            // finish the shit
            System.out.println("simulation completed! FULL");

        } else {// show alert if not everything was answered
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Questions left on blank");
            alert.setHeaderText("You didn't answer " + (App.currentSimulation.getNumberOfQuestions()
                    - getAnsweredQuestions()) + " questions");
            alert.setContentText("Are you sure you want to finish the current simulation?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                System.out.println("simulation completed! EMPTY");

            }
        }
        new Thread() {// save stats and history
            public void run() {
                newSim = true;

                updateOldTimer(App.currentSimulation.getTimer(), (int) initialTime, (int) (initialTime - totalSecs));
                //
                String finishMessage = App.currentSimulation.terminate(
                        App.actionsController.getModel().getLoadedStats(),
                        App.actionsController.getModel().getLoadedHistory());

                simTimer.cancel();
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            SimStatsController.updateStats(finishMessage);
                            App.setRoot("showStats");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }.start();

    }

    /**
     * Checks if the question was already answered, if yes, marks its key when
     * the corresponding question is displayed
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
     * Helper method which marks the button of the already answered questions
     * 
     * @param key a, b, c or d
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

    /**
     * Helper method that returns the number of already answered questions of the
     * test
     * 
     * @return
     */
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
     * Method handling the Timer object setting and updating
     */
    private void setTimer() {
        totalSecs = App.currentSimulation.getNumberOfQuestions() * 60;
        //
        initialTime = totalSecs;
        //
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        convertTime();
                        if (totalSecs <= 0) {
                            System.out.println("times up!!!");
                            timer.cancel();

                            String finishMessage = App.currentSimulation.terminate(
                                    App.actionsController.getModel().getLoadedStats(),
                                    App.actionsController.getModel().getLoadedHistory());
                            SimStatsController.updateStats(finishMessage);
                            simTimer.cancel();
                            try {
                                App.setRoot("showStats");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
        TestController.simTimer = timer;
    }

    /**
     * Helper method to make the showed time on screen nicer
     * 
     * @param value
     * @return
     */
    private String format(long value) {
        if (value < 10) {
            return "0" + value;
        } else {
            return Long.toString(value);
        }
    }

    /**
     * Helper method that converts the total seconds to a String xx:xx:xx format
     */
    public void convertTime() {
        mins = TimeUnit.SECONDS.toMinutes(totalSecs);
        secs = totalSecs - (mins * 60);

        hrs = TimeUnit.MINUTES.toHours(mins);
        mins = mins - (hrs * 60);

        //
        time.setText(format(hrs) + ":" + format(mins) + ":" + format(secs));
        totalSecs--;
    }

    /**
     * Modifies the old TestTimer object fields so it can be comfotably accessed by
     * the already established methods
     * 
     * @param timer
     * @param initialTime
     * @param remainingTime
     */
    public void updateOldTimer(ExamTimer timer, int initialTime, int remainingTime) {
        timer.DURATION_SIMULATION = initialTime;
        App.actionsController.getModel().setRemainingTimeSimulation(remainingTime);
    }
}
