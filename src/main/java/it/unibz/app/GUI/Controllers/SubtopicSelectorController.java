package it.unibz.app.GUI.Controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import it.unibz.app.App;
import it.unibz.model.implementations.Simulation;
import it.unibz.model.implementations.Subtopic;
import it.unibz.model.implementations.Topic;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

/**
 * Controller class of the subtopicSelector.fxml file, to which the user is
 * redirected from the topicSelectorController class if the subtopicMode was
 * activated
 */
public class SubtopicSelectorController {
    @FXML
    private AnchorPane displayPane;
    @FXML
    private Label topicText;

    private static Topic topic;
    private static Set<Subtopic> subtopics;

    /**
     * The initialize method adds Checkbox objects to the scene depending on the
     * previously selected Topic, one per each Subtopic it contains. The user can
     * then choose one or several Subtopics to start its test
     */
    @FXML
    void initialize() {
        new Thread() {
            @Override
            public void run() {
                System.out.println(topic);

                // synchronizing the threads or else the static properties are detected as 0 and
                // null
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        topicText.setText(topic.getTopicName());
                        SubtopicSelectorController.subtopics = SubtopicSelectorController.topic.getSubtopics();
                        int x = 100;
                        int y = x;
                        int index = 1;

                        for (Subtopic subtopic : SubtopicSelectorController.subtopics) {
                            addSubtopicCheckbox(x, y, index + ". " + subtopic.getSubtopicName());

                            y += 30;
                            index++;
                        }
                    }
                });
            }
        }.start();

    }

    /**
     * Helper method which return a CheckBox object based on some coordinates and a
     * text
     * 
     * @param x
     * @param y
     * @param text
     * @return
     */
    private CheckBox addSubtopicCheckbox(double x, double y, String text) {
        CheckBox newSubtopicCheckbox = new CheckBox();
        newSubtopicCheckbox.setText(text);
        newSubtopicCheckbox.relocate(x, y);
        displayPane.getChildren().add(newSubtopicCheckbox);

        return newSubtopicCheckbox;
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

    /**
     * Method which checks that the user has selected at least one subtopic Checkbox
     * and generates the corresponding data structures to start the test and which
     * the TestController will need
     * 
     * @param event
     * @throws IOException
     */
    public void confirmSelection(ActionEvent event) throws IOException {
        Set<Subtopic> selectedSubtopics = new HashSet<>();
        for (Node child : displayPane.getChildren()) { // for each checkbox selected add its Subtopic to a list
            if (child instanceof CheckBox && ((CheckBox) child).isSelected()) {

                Subtopic selectedSubtopic = fromCheckboxToSubtopic((CheckBox) child);
                //
                System.out.println(selectedSubtopic);
                selectedSubtopics.add(selectedSubtopic);
                //
            }
        }
        if (selectedSubtopics.size() == 0) {// if no subtopic was selected, show an alert
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No subtopic selected");
            alert.setHeaderText("You didn´t select any subtopic");
            alert.setContentText("Please select at least one subtopic to continue");
            alert.show();
        } else { // if at least one subtopic was selected update call some static methods of the
                 // questionPerSubtopic fxml file so it can update its content based on the
                 // subtopics selected
            new Thread() {
                @Override
                public void run() {
                    try {
                        FXMLLoader loader = App.getFXMLLoader("numberOfSubtopics");
                        loader.load();
                        NumberOfSubtopicsController controller = loader.getController();

                        controller.setSpinnerMax(selectedSubtopics);
                        controller.displaySubtopicsSelected(selectedSubtopics);
                        controller.displayTopicSelected(topic);// TODO:
                        NumberOfSubtopicsController.subTopicMode = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        public void run() {
                            try {
                                App.currentSimulation = new Simulation(topic);/// what changes is the select method
                                App.setRoot("numberOfSubtopics");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }.start();

        }

    }

    /**
     * Helper method to get the corresponding Subtopic object represented by a
     * checkbox object
     * 
     * @param checkBox
     * @return
     */
    private Subtopic fromCheckboxToSubtopic(CheckBox checkBox) {
        Subtopic selectedSubtopic = App.actionsController
                .getSubtopicFromName(checkBox.getText().substring(3));
        return selectedSubtopic;
    }

    /**
     * Public method to be acceses by other controllers to modify the current
     * controller static fields
     * 
     * @param topic
     */
    public void displaySelected(Topic topic) {
        SubtopicSelectorController.topic = topic;
    }
}
