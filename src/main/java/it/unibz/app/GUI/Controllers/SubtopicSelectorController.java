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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class SubtopicSelectorController {
    @FXML
    AnchorPane displayPane;
    @FXML
    Label topicText;

    public static Topic topic;
    public static Set<Subtopic> subtopics;

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

    public CheckBox addSubtopicCheckbox(double x, double y, String text) {
        CheckBox newSubtopicCheckbox = new CheckBox();
        newSubtopicCheckbox.setText(text);
        newSubtopicCheckbox.relocate(x, y);
        displayPane.getChildren().add(newSubtopicCheckbox);

        return newSubtopicCheckbox;
    }

    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("mainMenu");

    }

    public void confirmSelection(ActionEvent event) throws IOException {// TODO:
        Set<Subtopic> selectedSubtopics = new HashSet<>();
        for (Node child : displayPane.getChildren()) {
            if (child instanceof CheckBox && ((CheckBox) child).isSelected()) {

                Subtopic selectedSubtopic = fromCheckboxToSubtopic((CheckBox) child);
                //
                System.out.println(selectedSubtopic);
                selectedSubtopics.add(selectedSubtopic);
                //

                // TODO:

                /*
                 * controller.displayTopicSelected(selectedSubtopic);
                 * controller.setSpinnerMax(selectedSubtopic);
                 */
            }
        }
        if (selectedSubtopics.size() == 0) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No subtopic selected");
            alert.setHeaderText("You didn´t select any subtopic");
            alert.setContentText("Please select at least one subtopic to continue");
            alert.show();
        } else {
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

    private static Subtopic fromCheckboxToSubtopic(CheckBox checkBox) {
        Subtopic selectedSubtopic = App.actionsController
                .getSubtopicFromName(checkBox.getText().substring(3));
        return selectedSubtopic;
    }

    public void displaySelected(Topic topic) {
        SubtopicSelectorController.topic = topic;
    }
}
