package it.unibz.app.GUI.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unibz.app.App;
import it.unibz.model.implementations.Topic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class GeneratePDFMenuController {
    @FXML
    AnchorPane displayPane;
    List<Topic> topics;

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
    }

    public RadioButton addTopicButton(double x, double y, String text, ToggleGroup group) {
        RadioButton newTopicButton = new RadioButton();
        newTopicButton.setText(text);
        newTopicButton.relocate(x, y);
        newTopicButton.setToggleGroup(group);
        displayPane.getChildren().add(newTopicButton);

        return newTopicButton;
    }

    public void generatePDF(ActionEvent event) {
        for (Node child : displayPane.getChildren()) {
            if (child instanceof RadioButton && ((RadioButton) child).isSelected()) {

                Topic selectedTopic = fromRadioButtonToTopic((RadioButton) child);
                //
                System.out.println(selectedTopic);
                //
                String jsonFilePath = "src/main/resources/bank/" + selectedTopic.getTopicName().replaceAll("\\s+", "_")
                        + ".json";
                String filePath = "src/main/resources/pdf/" + selectedTopic.getTopicName().replaceAll("\\s+", "_")
                        + ".pdf";
                App.actionsController.getPdfGenerator().transformJsonIntoPDF(jsonFilePath, filePath);

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("PDF generation was succesful");
                alert.setHeaderText(
                        "The PDF of the topic " + selectedTopic.getTopicName() + "was sucesfully generated");
                alert.setContentText("It can be found in the following path:" + System.lineSeparator() + filePath);

                alert.show();

            }
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("mainMenu");
    }

    private static Topic fromRadioButtonToTopic(RadioButton button) {
        Topic selectedTopic = App.actionsController
                .getTopicFromName(button.getText().substring(3));
        return selectedTopic;
    }
}
