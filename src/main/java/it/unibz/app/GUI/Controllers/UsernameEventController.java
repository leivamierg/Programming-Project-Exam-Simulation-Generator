package it.unibz.app.GUI.Controllers;

import java.io.IOException;

import it.unibz.app.App;
import it.unibz.controller.Controller;
import it.unibz.model.implementations.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UsernameEventController {
    private Stage stage;

    @FXML
    AnchorPane scenePane;

    @FXML
    TextField nameField;

    public void login(ActionEvent event) throws IOException {
        String name = nameField.getText(); // get the text within the nameField

        // check its content
        if (!name.equals("")) {// if not empty

            FXMLLoader mainMenuLoader = App.getFXMLLoader("mainMenu");

            mainMenuLoader.load(); // if we dont load it the .getController method and other dont work

            MainMenuEventController mainMenuController = mainMenuLoader.getController();

            // initialize everything model, controller, ecc.

            App.user = App.loadUserData(name);
            App.actionsController = new Controller(new Model(), App.user);

            //

            mainMenuController.displayName(name);

            App.setRoot("mainMenu");
        } else {// if empty
            // show a message asking for a name
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Invalid access");
            alert.setHeaderText("You inserted an invalid username");
            alert.setContentText("Please, write a valid username to continue");

            alert.show();
        }

    }

    public void exit(ActionEvent event) {// ask the user if it really wants to exit and confirm it
        stage = (Stage) scenePane.getScene().getWindow();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Close the application");
        alert.setHeaderText("You are about to close the application");
        alert.setContentText("Are you sure you want to exit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Exit completed successfully!");
            stage.close();
        }
    }
}
