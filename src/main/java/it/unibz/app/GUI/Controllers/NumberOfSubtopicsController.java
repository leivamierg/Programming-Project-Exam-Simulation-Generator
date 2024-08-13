package it.unibz.app.GUI.Controllers;

import java.io.IOException;

import it.unibz.app.App;
import it.unibz.model.implementations.Subtopic;
import it.unibz.model.implementations.Topic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class NumberOfSubtopicsController {
    @FXML
    Label selectedTopic;
    @FXML
    Spinner<Integer> subtopicSpinner;

    static int spinnerMax;
    static String topicName;
    Topic topic;

    public void initialize() {
        topic = getTopic();

        selectedTopic.setText(NumberOfSubtopicsController.topicName);
        spinnerMaxSetter(topic);

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                NumberOfSubtopicsController.spinnerMax);
        valueFactory.setValue(1);
        subtopicSpinner.setValueFactory(valueFactory);

    }

    public void showTopicSelected(String topicName) {
        NumberOfSubtopicsController.topicName = topicName;
    }

    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("topicSelector");
    }

    private void spinnerMaxSetter(Topic topic) {
        int max = 0;

        for (Subtopic subtopic : topic.getSubtopics()) {
            if (subtopic.getAvailableQuestions().size() > max) {
                max = subtopic.getAvailableQuestions().size();
            }
        }

        spinnerMax = max;
    }

    private static Topic getTopic() {
        Topic selectedTopic = App.actionsController
                .getTopicFromName(topicName);
        return selectedTopic;
    }
}
