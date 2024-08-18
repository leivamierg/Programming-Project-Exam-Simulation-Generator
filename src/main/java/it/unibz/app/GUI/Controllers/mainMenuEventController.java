package it.unibz.app.GUI.Controllers;

import java.io.IOException;
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

public class MainMenuEventController {

    @FXML
    Label username;

    static String name;

    public void displayName(String name) {
        MainMenuEventController.name = name;
    }

    @FXML
    public void initialize() {
        username.setText(name);
    }

    public void listTopics(ActionEvent event) throws IOException {
        App.setRoot("topicDisplay");
    }

    public void listSubtopics(ActionEvent event) throws IOException {
        App.setRoot("subtopicDisplay");
    }

    public void selectTopic(ActionEvent event) throws IOException {
        TopicSelectorController.subtopicMode = false;
        App.setRoot("topicSelector");
    }

    public void selectTopicAndSubtopics(ActionEvent event) throws IOException {
        TopicSelectorController.subtopicMode = true;
        App.setRoot("topicSelector");
    }

    public void seeHistory(ActionEvent event) throws IOException {
        App.setRoot("historyDisplay");
    }

    public void seeGeneralStats(ActionEvent event) throws IOException {
        GeneralStatsDisplayController
                .displayStats(App.actionsController.getModel().getLoadedStats().showGeneralStats());
        App.setRoot("generalStatsDisplay");
    }

    public void generatePdf(ActionEvent event) throws IOException {
        App.setRoot("generatePDFMenu");
    }

    public void seeProfileInfo(ActionEvent event) throws IOException {
        App.setRoot("seeProfileInfo");
    }

    public void startDailyChallenge(ActionEvent event) throws IOException {
        App.setRoot("startDailyChallenge");
    }

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
                // TODO: save profileInfo

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
