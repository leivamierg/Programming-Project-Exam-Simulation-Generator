package it.unibz.app.GUI.Controllers;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UsernameEventController {
    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    TextField nameField;

    public void login(ActionEvent event) throws IOException {
        String name = nameField.getText();

        if (!name.equals("")) {
            FXMLLoader loader = new FXMLLoader(
                    new File("src/main/java/it/unibz/app/GUI/FXMLS/mainMenu.fxml").toURI().toURL());
            root = loader.load();

            mainMenuEventController mainMenuController = loader.getController();
            mainMenuController.displayName(name);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // initialize everything model, controller, ecc.
        } else {
            // show a message asking for a name
        }

    }
}
