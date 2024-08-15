package it.unibz.app.GUI.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unibz.app.App;
import it.unibz.model.implementations.Simulation;
import it.unibz.model.implementations.Topic;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class TopicSelectorController {
    @FXML
    AnchorPane displayPane;
    @FXML
    Button nextButton;

    List<Topic> topics;
    public static boolean subtopicMode;

    @FXML
    void initialize() throws IOException {// to add components dynamically we MUST add the initialize method
        // add all the topics as elements to the display.fxml scene
        this.topics = new ArrayList<Topic>();
        int x = 100;
        int y = x;
        int index = 1;
        ToggleGroup topicGroup = new ToggleGroup();

        Set<Topic> topics = App.actionsController.getModel().getTopics();

        for (Topic topic : topics) {
            addTopicButton(x, y, index + ". " + topic.getTopicName(), topicGroup);

            this.topics.add(topic);
            y += 30;
            index++;
        }

        if (subtopicMode) {
            nextButton.setText("Select subtopics");
        }
    }

    public RadioButton addTopicButton(double x, double y, String text, ToggleGroup group) {
        RadioButton newTopicButton = new RadioButton();
        newTopicButton.setText(text);
        newTopicButton.relocate(x, y);
        newTopicButton.setToggleGroup(group);
        displayPane.getChildren().add(newTopicButton);

        return newTopicButton;
    }

    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("mainMenu");

    }

    public void confirmSelection(ActionEvent event) throws IOException {
        for (Node child : displayPane.getChildren()) {
            if (subtopicMode) {
                if (child instanceof RadioButton && ((RadioButton) child).isSelected()) {
                    Topic selectedTopic = fromRadioButtonToTopic((RadioButton) child);
                    //
                    System.out.println(selectedTopic);
                    //
                    App.currentSimulation = new Simulation(selectedTopic);

                    FXMLLoader loader = App.getFXMLLoader("subtopicSelector");
                    loader.load();
                    SubtopicSelectorController controller = loader.getController();

                    controller.displaySelected(selectedTopic);

                    App.setRoot("subtopicSelector");

                }
            } else {
                if (child instanceof RadioButton && ((RadioButton) child).isSelected()) {

                    Topic selectedTopic = fromRadioButtonToTopic((RadioButton) child);
                    //
                    System.out.println(selectedTopic);
                    //
                    App.currentSimulation = new Simulation(selectedTopic);/// TODO:

                    FXMLLoader loader = App.getFXMLLoader("numberOfSubtopics");
                    loader.load();
                    NumberOfSubtopicsController controller = loader.getController();

                    controller.displayTopicSelected(selectedTopic);
                    controller.setSpinnerMax(selectedTopic);
                    NumberOfSubtopicsController.subTopicMode = false;

                    App.setRoot("numberOfSubtopics");
                }
            }

        }

    }

    private static Topic fromRadioButtonToTopic(RadioButton button) {
        Topic selectedTopic = App.actionsController
                .getTopicFromName(button.getText().substring(3));
        return selectedTopic;
    }

}
