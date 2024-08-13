package it.unibz.app;

import it.unibz.controller.Controller;
import it.unibz.model.implementations.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class of the program.
 *
 */
public class App extends Application {

    /**
     * The main method of the program.
     *
     * @param args The command-line arguments.
     */

    //
    private static Scene scene;
    public static Controller actionsController;
    public static User user;
    public static Simulation currentSimulation;

    //
    public static void main(String[] args) throws IOException {
        //
        launch();
        /**
         * Scanner scanner = new Scanner(System.in);
         * 
         * System.out.println("Enter your username:");
         * String username = scanner.nextLine();
         **/
        // User user = loadUserData(username);

        // Controller controller = new Controller(new Model(), user);
        //
        /**
         * String input;
         * boolean showWelcomeMessage = true;
         * 
         * while (true) {
         * if (showWelcomeMessage)
         * System.out.println("Welcome to the exam simulation program! Click -h for
         * commands");
         * 
         * input = scanner.nextLine();
         * 
         * if (input.equalsIgnoreCase("exit")) {
         * Set<Topic> loadedTopics = FileLoader.getTopics();
         * FileLoader.saveBank(System.getProperty("user.dir") +
         * "/src/main/resources/bank/",
         * List.copyOf(loadedTopics));
         * 
         * HistoryStatsLoader.saveStats("src/main/resources/h_s/stats.json",
         * Model.getLoadedStats());
         * HistoryStatsLoader.saveHistory("src/main/resources/h_s/history.json",
         * Model.getLoadedHistory());
         * 
         * System.out.println("Exiting the simulation");
         * break;
         * }
         * 
         * if (input.equals("-h")) {
         * System.out.println("1) -t or --topics to list all topics");
         * System.out.println("2) 'topic' -s, --subtopics to list all subtopics");
         * System.out.println("3) 'topic' to start the test");
         * System.out.println("4) 'topic' --select to select the subtopic from the topic
         * to test");
         * System.out.println("5) --history to show the history of simulation");
         * System.out.println("6) --stats to show the general stats");
         * System.out.println(
         * "7) topic 'topic' 'from sim number' 'to sim number' --compareStats to compare
         * the stats" +
         * "of a topic from a certain simulation to another one");
         * System.out.println(
         * "8) subtopic 'subtopic' 'from sim number' 'to sim number' --compareStats to
         * compare the stats" +
         * "of a subtopic from a certain simulation to another one");
         * System.out.println("9) topic 'topic' 'from sim number' --compareStats to
         * compare the stats" +
         * "of a topic from a certain simulation to the last one");
         * System.out.println("10) subtopic 'subtopic' 'from sim number' --compareStats
         * to compare the stats" +
         * "of a subtopic from a certain simulation to the last one");
         * System.out.println("11) topic 'topic' 'simulation number' --showStats" +
         * " to show the stats of a certain topic after x simulations");
         * System.out.println("12) subtopic 'subtopic' 'simulation number' --showStats"
         * +
         * " to show the stats of a certain subtopic after x simulations");
         * System.out.println("13) 'topic' --download to download PDF version of exam");
         * System.out.println("14) '-d' to start the daily challenge");
         * System.out.println("15) '--profile' to see your profile");
         * System.out.println("16) 'exit' to close the exam simulation program");
         * showWelcomeMessage = false;
         * continue;
         * } else {
         * controller.elaborateArgs(input.split("\\s+"));
         * }
         * }
         * 
         * scanner.close();
         * 
         **/
    }

    public static User loadUserData(String username) {
        ObjectMapper mapper = new ObjectMapper();
        File userFile = new File("src/main/resources/challenge/" + username + ".json");
        if (userFile.exists()) {
            try {
                return mapper.readValue(userFile, User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new User(username);
    }

    public void start(Stage stage) throws IOException {
        try {
            scene = new Scene(getFXMLLoader("username").load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(getFXMLLoader(fxml).load());
    }

    public static FXMLLoader getFXMLLoader(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                new File("src/main/java/it/unibz/app/GUI/FXMLS/" + fxml + ".fxml").toURI().toURL());
        return fxmlLoader;
    }

}