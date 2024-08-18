package it.unibz.app.GUI.Controllers;

import java.io.IOException;

import it.unibz.app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SeeProfileController {
    @FXML
    Label username;
    @FXML
    Label profileInfo;

    @FXML
    public void initialize() {
        username.setText(App.user.getUsername());
        profileInfo.setText(App.user.toString());
    }

    public void goBack() throws IOException {
        App.setRoot("mainMenu");
    }
}
