package it.unibz.app.GUI.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unibz.app.App;
import it.unibz.model.implementations.Simulation;
import it.unibz.model.implementations.Topic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 * Class that handles the topic selection for the creation of the tests
 */
public class TopicSelectorController {
    @FXML
    private AnchorPane displayPane;
    @FXML
    private Button nextButton;

    List<Topic> topics;
    public static boolean subtopicMode;

    /**
     * Method that creates RadioButton Objects representing the available Topics and
     * to be selected by the user
     * 
     * @throws IOException
     */
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

        // if the suptopicMode is activated it redirects the user to the
        // subtopicSelector instead of the numberOfQuestionsPerSubtopic Selector so the
        // button's text is modified accordingly
        if (subtopicMode) {
            nextButton.setText("Select subtopics");
        }
    }

    /**
     * Helper method to generate a RadioButton object based on some coordinates, a
     * given text, and a ToggleGroup
     * 
     * @param x
     * @param y
     * @param text
     * @param group
     * @return
     */
    private RadioButton addTopicButton(double x, double y, String text, ToggleGroup group) {
        RadioButton newTopicButton = new RadioButton();
        newTopicButton.setText(text);
        newTopicButton.relocate(x, y);
        newTopicButton.setToggleGroup(group);
        displayPane.getChildren().add(newTopicButton);

        return newTopicButton;
    }

    /**
     * Redirection to the mainMenu.fxml file
     * 
     * @param event
     * @throws IOException
     */
    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("mainMenu");

    }

    /**
     * Method that confirms the Topic selection of the User and sends the user no
     * another fxml file depending on the SubtopiMode or TopicMode. And that shows
     * an alert in the case that the user has not yet selected a Topic.
     * 
     * @param event
     * @throws IOException
     */
    public void confirmSelection(ActionEvent event) throws IOException {
        for (Node child : displayPane.getChildren()) {
            if (subtopicMode) {// if SubtopiCmode
                if (child instanceof RadioButton && ((RadioButton) child).isSelected()) {// If such a RadioButton is
                                                                                         // selected
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
            } else {// if instead TopicMode
                if (child instanceof RadioButton && ((RadioButton) child).isSelected()) {// If such a RadioButton is
                                                                                         // selected

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

    /**
     * Helper method that gets a Topic object based on the text of a RadioButton
     * object
     * 
     * @param button
     * @return
     */
    private Topic fromRadioButtonToTopic(RadioButton button) {
        Topic selectedTopic = App.actionsController
                .getTopicFromName(button.getText().substring(3));
        return selectedTopic;
    }

}
