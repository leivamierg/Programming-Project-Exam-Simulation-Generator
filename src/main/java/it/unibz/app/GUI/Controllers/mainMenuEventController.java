package it.unibz.app.GUI.Controllers;

import java.io.IOException;

import it.unibz.app.App;
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
        App.setRoot("topicSelector");
    }

    public void logOut(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText("You are about to log out");
        alert.setContentText("Are you sure you want to close your account? (Data will be saved)");

        if (alert.showAndWait().get() == ButtonType.OK) {
            App.setRoot("username");
            // TODO: add progress savers methods, etc.
        }
    }
}
