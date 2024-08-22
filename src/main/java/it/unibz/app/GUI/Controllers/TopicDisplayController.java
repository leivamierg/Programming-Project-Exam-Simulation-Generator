package it.unibz.app.GUI.Controllers;

import java.io.IOException;
import java.util.Set;

import it.unibz.app.App;
import it.unibz.model.implementations.Topic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Class meant to handle the topicDisplay.fxml file and show all the available
 * topics graphically
 */
public class TopicDisplayController {
    @FXML
    private AnchorPane displayPane;

    /**
     * Initilization method which creates a Label object per each topic contained in
     * the programm and then displays it on the current scene
     * 
     * @throws IOException
     */
    @FXML
    public void initialize() throws IOException {// to add components dynamically that were not already declared in the
                                                 // fxml file we MUST add the initialize method, or they wont be
                                                 // displayed
        Set<Topic> topics = App.actionsController.getModel().getTopics();
        // add all the topics as elements to the display.fxml scene
        int x = 100;
        int y = x;
        int index = 1;

        for (Topic topic : topics) {
            addTopic(x, y, index + ". " + topic.getTopicName());
            y += 30;
            index++;
        }
    }

    /**
     * Helper method which reieves coordinates and a topicName and returns a
     * corresponding new Label object to be displayed
     * 
     * @param x
     * @param y
     * @param text
     */
    private void addTopic(double x, double y, String text) {
        Label newTopic = new Label();
        newTopic.setText(text);
        newTopic.relocate(x, y);
        displayPane.getChildren().add(newTopic);
    }

    /**
     * Redirection to the mainMenu.fxml
     * 
     * @param event
     * @throws IOException
     */
    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("mainMenu");
    }

}
