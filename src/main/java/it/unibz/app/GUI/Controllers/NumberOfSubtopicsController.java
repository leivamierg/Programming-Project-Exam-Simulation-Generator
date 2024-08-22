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

/**
 * Class meant to handle the numberOfSubtopics.fxml which should be named
 * differently and controls and works with how many questions per Subtopic
 * should the generated test have. It has differente methods which handle both
 * Topic and Subtopic mode. The Topic mode means that the user wants to generate
 * a test based on all the subtopics of a Topic, and the SubtopicMode means that
 * it wants to generate a test based on some of the subtopics of a Topic
 */
public class NumberOfSubtopicsController {
    @FXML
    private Label selectedLabel;
    @FXML
    private Spinner<Integer> subtopicSpinner;

    public static boolean subTopicMode;

    private static int spinnerMax;

    private static Topic topic;

    private static Set<Subtopic> subtopicsSet;

    @FXML
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

    /**
     * Public method for the other controllers to modify the static field topic
     * 
     * @param topic
     */
    // TOPIC MODE
    public void displayTopicSelected(Topic topic) {
        NumberOfSubtopicsController.topic = topic;
    }

    /**
     * Public method for the other controllers to modify the static field subtopics
     * 
     * @param subtopics
     */
    // SUBTOPICS MODE
    public void displaySubtopicsSelected(Set<Subtopic> subtopics) {
        NumberOfSubtopicsController.subtopicsSet = subtopics;
    }

    /**
     * Helper method which returns the ToString of the list of the names of the
     * subtopics field
     * 
     * @param subtopics
     * @return
     */
    // SUBTOPICS MODE
    private String formatSubtopics(Set<Subtopic> subtopics) {
        Set<String> subtopicNames = new HashSet<>();
        for (Subtopic subtopic : subtopics) {
            subtopicNames.add(subtopic.getSubtopicName());
        }
        return subtopicNames.toString();
    }

    /**
     * Public method to be accessed by other controllers to modify the max number of
     * questions per subtopic that can be choosen in the TOPIC mode
     * 
     * @param topic
     */
    // TOPIC MODE
    public void setSpinnerMax(Topic topic) {
        NumberOfSubtopicsController.spinnerMax = NumberOfSubtopicsController.spinnerMaxCalculator(topic);
    }

    /**
     * Public method to be accessed y other controllers to modify the max number of
     * questions per subtopic that can be choosen in the SUBTOPIC mode
     * 
     * @param subtopics
     */
    // SUBTOPICS MODE
    public void setSpinnerMax(Set<Subtopic> subtopics) {
        NumberOfSubtopicsController.spinnerMax = NumberOfSubtopicsController.spinnerMaxCalculator(subtopics);
    }

    /**
     * Redirection to topicSelector.fxml which keeps the chosen topic or subtopic
     * mode
     * 
     * @param event
     * @throws IOException
     */
    // BOTH
    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("topicSelector");
    }

    /**
     * Method which starts the test and shows an alert first if one of the subtopics
     * to be selected does not have enough available questions compared to the
     * number chosen by the user. It works for both modes
     * 
     * @param event
     * @throws IOException
     */
    public void startSimulation(ActionEvent event) throws IOException {
        if (subTopicMode) {// SUBTOPIC MODE
            if (enoughQuestions(subtopicSpinner.getValue(), subtopicsSet)) {
                // start the simulation
                System.out.println("Simulation started succesfully... -s");
                //
                new Thread() {
                    @Override
                    public void run() {

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

    /**
     * Marks the max number of questions to be chosen through the Spinner GUI
     * component. It loops through the subtopics of the given topic and selects the
     * size of the subtopic which contains more available questions
     * 
     * @param topic
     * @return
     */
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

    /**
     * Marks the max number of questions to be chosen through the Spinner GUI
     * component. It loops through the subtopics selected and selects the
     * size of the subtopic which contains more available questions
     * 
     * @param subtopics
     * @return
     */
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

    /**
     * Helper method which checks if all subtopics of the given Topic have enough
     * available Questions to be selected
     * 
     * @param number
     * @param topic
     * @return
     */
    // TOPIC MODE
    private static boolean enoughQuestions(int number, Topic topic) {
        for (Subtopic subtopic : topic.getSubtopics()) {
            if (number > subtopic.getAvailableQuestions().size()) {
                return false;
            }
        }
        return true;
    }

    /**
     * * Helper method which checks if all subtopics of the given Set have enough
     * available Questions to be selected
     * 
     * @param number
     * @param subtopics
     * @return
     */
    // SUBTOPICS MODE
    private static boolean enoughQuestions(int number, Set<Subtopic> subtopics) {
        for (Subtopic subtopic : subtopics) {
            if (number > subtopic.getAvailableQuestions().size()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method which returns the string containing the names of the subtopics
     * with not enough available questions. TOPIC MODE
     * 
     * @param number
     * @param topic
     * @return
     */
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

    /**
     * Helper method which returns the string containing the names of the subtopics
     * with not enough available questions. SUBTOPICS MODE
     * 
     * @param number
     * @param subtopics
     * @return
     */
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
