package it.unibz.app.GUI.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class mainMenuEventController {
    @FXML
    Label username;

    public void displayName(String name) {
        username.setText(name);
    }
}
