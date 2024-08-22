package it.unibz.app.GUI.Controllers;

import java.io.IOException;

import it.unibz.app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DailyChallengeResultsController {
    @FXML
    Label displayResults;
    public static String message;

    public void initialize() {
        displayResults.setText(message);
    }

    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("mainMenu");
    }
}
