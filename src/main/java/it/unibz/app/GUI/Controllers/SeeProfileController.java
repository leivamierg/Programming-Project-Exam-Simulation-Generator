package it.unibz.app.GUI.Controllers;

import java.io.IOException;

import it.unibz.app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SeeProfileController {
    @FXML
    private Label username;
    @FXML
    private Label profileInfo;

    /**
     * Initilize method that sets the Username Label text to the Username String of
     * App.user, and the profileInfo Label text to the toString method of the
     * App.user object
     */
    @FXML
    public void initialize() {
        username.setText(App.user.getUsername());
        profileInfo.setText(App.user.toString());
    }

    /**
     * Redirects the user back to the mainMenu
     * 
     * @throws IOException
     */
    public void goBack() throws IOException {
        App.setRoot("mainMenu");
    }
}
