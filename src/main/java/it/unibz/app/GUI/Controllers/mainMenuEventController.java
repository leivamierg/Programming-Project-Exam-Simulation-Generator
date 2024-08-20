package it.unibz.app.GUI.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import it.unibz.app.App;
import it.unibz.model.implementations.FileLoader;
import it.unibz.model.implementations.HistoryStatsLoader;
import it.unibz.model.implementations.Model;
import it.unibz.model.implementations.Topic;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * Controller class which handles the mainMenu of the application. Whenever the
 * user clicks one of the buttons corresponding to the different actions that
 * the programm does, it may check some conditions and then redirect the user to
 * the corresponding .fxml file, which at the same time is handled by a
 * controller class.
 */
public class MainMenuEventController {

    @FXML
    private Label username;
    //
    private static String name;

    /**
     * Method that changes the static "name" parameter.
     * 
     * @param name The username inserted by the user
     */
    public void displayName(String name) {
        MainMenuEventController.name = name;
    }

    /**
     * Method called when the user is redirected to the mainMenu fxml file.
     * It simply changes the content of a the username empty label to the value
     * saved in the name static field
     */
    @FXML
    public void initialize() {
        username.setText(name);
    }

    /**
     * Redirection to topicDisplay.fxml
     * 
     * @param event
     * @throws IOException
     */
    public void listTopics(ActionEvent event) throws IOException {
        App.setRoot("topicDisplay");
    }

    /**
     * Redirection to subtopicDisplay.fxml
     * 
     * @param event
     * @throws IOException
     */
    public void listSubtopics(ActionEvent event) throws IOException {
        App.setRoot("subtopicDisplay");
    }

    /**
     * Redirection to topicSelector.fxml in "Topic" mode.
     * 
     * @param event
     * @throws IOException
     */
    public void selectTopic(ActionEvent event) throws IOException {
        TopicSelectorController.subtopicMode = false;
        App.setRoot("topicSelector");
    }

    /**
     * Redirection to topicSelector.fxml in "Subtopic" mode.
     * 
     * @param event
     * @throws IOException
     */
    public void selectTopicAndSubtopics(ActionEvent event) throws IOException {
        TopicSelectorController.subtopicMode = true;
        App.setRoot("topicSelector");
    }

    /**
     * Redirection to historyDisplay.fxml
     * 
     * @param event
     * @throws IOException
     */
    public void seeHistory(ActionEvent event) throws IOException {
        App.setRoot("historyDisplay");
    }

    /**
     * Setting the static field containing the stats of the simulation in the
     * generalStatsDisplay.fxml controller class, and then redirection to
     * generalStatsDisplay.fxml
     * 
     * @param event
     * @throws IOException
     */
    public void seeGeneralStats(ActionEvent event) throws IOException {
        GeneralStatsDisplayController
                .displayStats(App.actionsController.getModel().getLoadedStats().showGeneralStats());
        App.setRoot("generalStatsDisplay");
    }

    /**
     * Redirection to to generatePDFMenu.fxml
     * 
     * @param event
     * @throws IOException
     */
    public void generatePdf(ActionEvent event) throws IOException {
        App.setRoot("generatePDFMenu");
    }

    /**
     * Redirection to seeProfileInfo.fxml
     * 
     * @param event
     * @throws IOException
     */
    public void seeProfileInfo(ActionEvent event) throws IOException {
        App.setRoot("seeProfileInfo");
    }

    /**
     * Check if the current user has tried the daily challenge already and then
     * redirect it to startDailyChallenge.fxml
     * 
     * @param event
     * @throws IOException
     */
    public void startDailyChallenge(ActionEvent event) throws IOException {
        if (!App.user.getChallengeDate().equals("")) {
            LocalDate lastChallengeDate = LocalDate.parse(App.user.getChallengeDate(),
                    DateTimeFormatter.ISO_LOCAL_DATE);
            if (lastChallengeDate.equals(LocalDate.now())) {
                // Send back to mainMenu and show alert

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Challenge already done");
                alert.setHeaderText("You have already complete today's daily challenge");
                alert.setContentText("Come back tomorrow");
                alert.showAndWait();

            } else {
                App.setRoot("startDailyChallenge");
            }
        } else {
            App.setRoot("startDailyChallenge");
        }
    }

    /**
     * Saving of the simulations done, if any, and elimination of the App.user
     * field. Then redirection to mainMenu.fxml
     * 
     * @param event
     * @throws IOException
     */
    public void logOut(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText("You are about to log out");
        alert.setContentText("Are you sure you want to close your account? (Data will be saved)");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // save user data
            Set<Topic> loadedTopics = FileLoader.getTopics();
            FileLoader.saveBank(System.getProperty("user.dir") +
                    "/src/main/resources/bank/",
                    List.copyOf(loadedTopics));

            try {
                // saving everything
                HistoryStatsLoader.saveStats("src/main/resources/h_s/stats.json",
                        Model.getLoadedStats());
                HistoryStatsLoader.saveHistory("src/main/resources/h_s/history.json",
                        Model.getLoadedHistory());

            } catch (Exception e) {
                e.printStackTrace();
            }
            Platform.runLater(new Runnable() {
                public void run() {
                    try {
                        App.user = null;
                        App.setRoot("username");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
