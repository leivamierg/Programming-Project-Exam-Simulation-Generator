package it.unibz.app.GUI.Controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
    Label selectedLabel;
    @FXML
    Spinner<Integer> subtopicSpinner;

    static boolean subTopicMode;

    static int spinnerMax;

    static Topic topic;

    static Set<Subtopic> subtopicsSet;

    public void initialize() {
        // SUBTOPICS MODE
        if (subTopicMode) {
            new Thread() {
                @Override
                public void run() {
                    System.out.println(subtopicsSet);
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

                            selectedLabel.setText(formatSubtopics(subtopicsSet));
                        }
                    });
                }
            }.start();
        } else {// TOPIC MODE
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

                            selectedLabel.setText(topic.getTopicName());
                        }
                    });
                }
            }.start();
        }

    }

    // TOPIC MODE
    public void displayTopicSelected(Topic topic) {
        NumberOfSubtopicsController.topic = topic;
    }

    // SUBTOPICS MODE
    public void displaySubtopicsSelected(Set<Subtopic> subtopics) {
        NumberOfSubtopicsController.subtopicsSet = subtopics;
    }

    private String formatSubtopics(Set<Subtopic> subtopics) {
        Set<String> subtopicNames = new HashSet<>();
        for (Subtopic subtopic : subtopics) {
            subtopicNames.add(subtopic.getSubtopicName());
        }
        return subtopicNames.toString();
    }

    // TOPIC MODE
    public void setSpinnerMax(Topic topic) {
        NumberOfSubtopicsController.spinnerMax = NumberOfSubtopicsController.spinnerMaxCalculator(topic);
    }

    // SUBTOPICS MODE
    public void setSpinnerMax(Set<Subtopic> subtopics) {
        NumberOfSubtopicsController.spinnerMax = NumberOfSubtopicsController.spinnerMaxCalculator(subtopics);
    }

    // BOTH
    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("topicSelector");
    }

    public void startSimulation(ActionEvent event) throws IOException {
        if (subTopicMode) {// SUBTOPIC MODE
            if (enoughQuestions(subtopicSpinner.getValue(), subtopicsSet)) {
                // start the simulation
                System.out.println("Simulation started succesfully... -s");
                //
                new Thread() {
                    @Override
                    public void run() {
                        // TODO: fix the simulation start
                        App.currentSimulation.select(subtopicsSet, subtopicSpinner.getValue());
                        App.currentSimulation.start();
                        // synchronizing the threads or else the static properties are detected as 0 and
                        // null
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    App.setRoot("test");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                }.start();
            } else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("WARNING: Not enough questions");
                alert.setHeaderText(
                        "Attention, the following subtopic do not count with enough questions to be selected:"
                                + System.lineSeparator()
                                + returnInsufficientSubtopics(subtopicSpinner.getValue(), subtopicsSet));
                alert.setContentText("Do you want to continue?");

                if (alert.showAndWait().get() == ButtonType.OK) {
                    // start the simulation anyways
                    System.out.println("Simulation started succesfully... -s");
                    //
                    new Thread() {
                        @Override
                        public void run() {
                            // TODO: the same
                            App.currentSimulation.select(subtopicsSet, subtopicSpinner.getValue());
                            App.currentSimulation.start();
                            // synchronizing the threads or else the static properties are detected as 0 and
                            // null
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        App.setRoot("test");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        }
                    }.start();
                }
            }
        } else {// TOPIC MODE
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
                alert.setHeaderText(
                        "Attention, the following subtopic do not count with enough questions to be selected:"
                                + System.lineSeparator()
                                + returnInsufficientSubtopics(subtopicSpinner.getValue(), topic));
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

    }

    // TOPIC MODE
    private static int spinnerMaxCalculator(Topic topic) {
        int max = 0;

        for (Subtopic subtopic : topic.getSubtopics()) {
            if (subtopic.getAvailableQuestions().size() > max) {
                max = subtopic.getAvailableQuestions().size();
            }
        }

        return max;
    }

    // SUBTOPIC MODE
    private static int spinnerMaxCalculator(Set<Subtopic> subtopics) {
        int max = 0;

        for (Subtopic subtopic : subtopics) {
            if (subtopic.getAvailableQuestions().size() > max) {
                max = subtopic.getAvailableQuestions().size();
            }
        }

        return max;
    }

    // TOPIC MODE
    private static boolean enoughQuestions(int number, Topic topic) {
        for (Subtopic subtopic : topic.getSubtopics()) {
            if (number > subtopic.getAvailableQuestions().size()) {
                return false;
            }
        }
        return true;
    }

    // SUBTOPICS MODE
    private static boolean enoughQuestions(int number, Set<Subtopic> subtopics) {
        for (Subtopic subtopic : subtopics) {
            if (number > subtopic.getAvailableQuestions().size()) {
                return false;
            }
        }
        return true;
    }

    // TOPIC MODE
    private static String returnInsufficientSubtopics(int number, Topic topic) {
        String message = "";
        for (Subtopic subtopic : topic.getSubtopics()) {
            if (number > subtopic.getAvailableQuestions().size()) {
                message = message + subtopic.getSubtopicName() + ", ";
            }
        }

        message = message.substring(0, message.length() - 2);

        return message;
    }

    // SUBTOPICS MODE
    private static String returnInsufficientSubtopics(int number, Set<Subtopic> subtopics) {
        String message = "";
        for (Subtopic subtopic : subtopics) {
            if (number > subtopic.getAvailableQuestions().size()) {
                message = message + subtopic.getSubtopicName() + ", ";
            }
        }

        message = message.substring(0, message.length() - 2);

        return message;
    }
}
