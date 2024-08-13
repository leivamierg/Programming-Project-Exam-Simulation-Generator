package it.unibz.app.GUI.Controllers;

import java.io.IOException;
import java.util.Set;

import it.unibz.app.App;
import it.unibz.model.implementations.Subtopic;
import it.unibz.model.implementations.Topic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SubtopicDisplayController {

    @FXML
    AnchorPane displayPane;

    @FXML
    void initialize() throws IOException {// to add components dynamically we MUST add the initialize method
        Set<Topic> topics = App.actionsController.getModel().getTopics();
        // add all the topics as elements to the display.fxml scene
        int x = 100;
        int y = x;
        int index = 1;

        for (Topic topic : topics) {
            addLabel(x, y, index + ". " + topic.getTopicName());
            y += 30;
            index++;
            for (Subtopic subtopic : topic.getSubtopics()) {
                addLabel(x + 20, y, subtopic.getSubtopicName());
                y += 20;
            }
        }
    }

    public void addLabel(double x, double y, String text) {
        Label newTopic = new Label();
        newTopic.setText(text);
        newTopic.relocate(x, y);
        displayPane.getChildren().add(newTopic);
    }

    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("mainMenu");

    }
}
