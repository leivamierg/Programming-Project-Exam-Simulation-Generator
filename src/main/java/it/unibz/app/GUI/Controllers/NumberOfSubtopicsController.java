package it.unibz.app.GUI.Controllers;

import java.io.IOException;

import it.unibz.app.App;
import it.unibz.model.implementations.Subtopic;
import it.unibz.model.implementations.Topic;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Alert.AlertType;

public class NumberOfSubtopicsController {
    @FXML
    Label selectedTopic;
    @FXML
    Spinner<Integer> subtopicSpinner;

    static int spinnerMax;
    static Topic topic;

    public void initialize() {
        new Thread() {
            @Override
            public void run() {
                System.out.println(topic);
                System.out.println(spinnerMax);

                // synchronizing the threads or else the static properties are detected as 0 and
                // null
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                                spinnerMax);
                        factory.setValue(1);
                        subtopicSpinner.setValueFactory(factory);

                        selectedTopic.setText(topic.getTopicName());
                    }
                });
            }
        }.start();
    }

    public void displayTopicSelected(Topic topic) {
        NumberOfSubtopicsController.topic = topic;
    }

    public void setSpinnerMax(Topic topic) {
        NumberOfSubtopicsController.spinnerMax = NumberOfSubtopicsController.spinnerMaxCalculator(topic);
    }

    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("topicSelector");
    }

    public void startSimulation(ActionEvent event) throws IOException {
        if (enoughQuestions(subtopicSpinner.getValue(), topic)) {
            // start the simulation
            System.out.println("Simulation started succesfully...");
            //
            new Thread() {
                @Override
                public void run() {
                    App.currentSimulation.select(topic, subtopicSpinner.getValue());
                    App.currentSimulation.start();
                    // synchronizing the threads or else the static properties are detected as 0 and
                    // null
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                App.setRoot("test");
                            } catch (Exception e) {
                                // TODO: handle exception
                            }

                        }
                    });
                }
            }.start();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("WARNING: Not enough questions");
            alert.setHeaderText("Attention, the following subtopic do not count with enough questions to be selected:"
                    + System.lineSeparator() + returnInsufficientSubtopics(subtopicSpinner.getValue(), topic));
            alert.setContentText("Do you want to continue?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                // start the simulation anyways
                System.out.println("Simulation started succesfully...");
                //
                new Thread() {
                    @Override
                    public void run() {
                        App.currentSimulation.select(topic, subtopicSpinner.getValue());
                        App.currentSimulation.start();
                        // synchronizing the threads or else the static properties are detected as 0 and
                        // null
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    App.setRoot("test");
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }

                            }
                        });
                    }
                }.start();
            }
        }
    }

    private static int spinnerMaxCalculator(Topic topic) {
        int max = 0;

        for (Subtopic subtopic : topic.getSubtopics()) {
            if (subtopic.getAvailableQuestions().size() > max) {
                max = subtopic.getAvailableQuestions().size();
            }
        }

        return max;
    }

    private static boolean enoughQuestions(int number, Topic topic) {
        for (Subtopic subtopic : topic.getSubtopics()) {
            if (number > subtopic.getQuestions().size()) {
                return false;
            }
        }
        return true;
    }

    private static String returnInsufficientSubtopics(int number, Topic topic) {
        String message = "";
        for (Subtopic subtopic : topic.getSubtopics()) {
            if (number > subtopic.getQuestions().size()) {
                message = message + subtopic.getSubtopicName() + ", ";
            }
        }

        message = message.substring(0, message.length() - 2);

        return message;
    }
}
