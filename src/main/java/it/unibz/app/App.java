package it.unibz.app;

import it.unibz.controller.Controller;
import it.unibz.model.implementations.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * The main class of the program.
 *
 */
public class App extends Application {

    //
    public static Stage stage;
    private static Scene scene;
    public static User user;
    public static Controller actionsController;
    public static Simulation currentSimulation;

    /**
     * The whole App class is set ready and activated through the "launch()" method,
     * which will call the start() method.
     */
    public static void main(String[] args) throws IOException {
        launch();
    }

    /**
     * The start method first initializes the "scene" and "stage" fields.
     * The initial scene is set to the "username.fxml" file.
     * Then the scene is setted in the stage, and then it is showed. Displaying the
     * content within "username.fxml".
     * Lastly, a lambda function is sent to the setOnCloseRequest stage's method
     * which will be called whenever the user tries to close the program.
     */
    public void start(Stage stage) throws IOException {
        try {
            App.stage = stage;//
            scene = new Scene(getFXMLLoader("username").load());
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(event -> {
                try {
                    event.consume();
                    exit(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method looks for the user String given and loads the username.json file,
     * if any, to the App.user static field. If there's no corresponding .json file,
     * it is created together with a new User object, which is also set to App.user.
     * 
     * @param username The username written by the user
     * @return an User object corresponding the given username
     */
    public static User loadUserData(String username) {
        ObjectMapper mapper = new ObjectMapper();
        File userFile = new File("src/main/resources/challenge/" + username + ".json");
        if (userFile.exists()) {
            try {
                return mapper.readValue(userFile, User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        User newUser = new User(username);
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("src/main/resources/challenge/" + username + ".json"), newUser);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newUser;

    }

    /**
     * Helper method which switches the current scene of the stage. That is to say,
     * display the content of a different .fxml file.
     * 
     * @param fxml The name of the fxml file without the .fxml ending.
     * @throws IOException can be thrown when the fxml file is nonexistent.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(getFXMLLoader(fxml).load());
    }

    /**
     * Helper method which returns an FXMLLoader object based on the fxml file name
     * given.
     * It is useful for the "App.setRoot" method and others.
     * 
     * @param fxml name of the fxml file
     * @return a FXMLLoader object corresponding a certain fxml file.
     * @throws IOException
     */
    public static FXMLLoader getFXMLLoader(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                new File("src/main/java/it/unibz/app/GUI/FXMLS/" + fxml + ".fxml").toURI().toURL());
        return fxmlLoader;
    }

    /**
     * Method called when the application is going to be closed.It saves some data
     * as .json files if the program was closed uncorrectly (outside the
     * username.fxml scene) or it closes it directly when done correctly (logOut was
     * done first).
     * 
     * @param stage the Stage object field of App
     * @throws IOException
     */
    public static void exit(Stage stage) throws IOException {// ask the user if it really wants to exit and confirm it

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Close the application");
        alert.setHeaderText("You are about to close the application");
        alert.setContentText("Are you sure you want to exit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // check if the user was loaded
            new Thread() {
                public void run() {
                    // only when closing incorrectly
                    if (!(user == null)) {
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

                    }
                    Platform.runLater(new Runnable() {
                        public void run() {
                            System.out.println("Exiting the simulation");
                            stage.close();
                        }
                    });
                }
            }.start();
        }
    }
}