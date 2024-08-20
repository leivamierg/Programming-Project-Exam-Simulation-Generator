package it.unibz.app.GUI.Controllers;

import java.io.IOException;

import it.unibz.app.App;
import it.unibz.controller.Controller;
import it.unibz.model.implementations.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class UsernameEventController {

    @FXML
    private AnchorPane scenePane;

    @FXML
    private TextField nameField;

    /**
     * This method detects the username written by the user in the "nameField"
     * TextField class and initializes it as the user parameter of the App class.
     * It also initializes an old Controller object with a corresponding Model
     * object as static fields of App.
     * If no username is given it will only show a message requiring it not letting
     * the user access the other programm's functionalities.
     * 
     * @param event event of clicking the log in button
     * @throws IOException
     */
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

            // Setting static field of the mainMenuController class as a communication
            // method.

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

    public void exitOnClick(ActionEvent event) throws IOException {// ask the user if it really wants to exit and
                                                                   // confirm it
        App.exit(App.stage);
    }
}
