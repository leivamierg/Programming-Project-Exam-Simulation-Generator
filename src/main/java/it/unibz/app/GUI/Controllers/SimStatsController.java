package it.unibz.app.GUI.Controllers;

import java.io.IOException;

import it.unibz.app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Class of the fxml file displaying the just completed test Stats and overall
 * results
 */
public class SimStatsController {
    @FXML
    private Label statsContent;
    public static String currentStats;

    /**
     * Initialize method which simply sets the just completed Test stats to the
     * statsContetn label text
     */
    public void initialize() {
        statsContent.setText(currentStats);
    }

    /**
     * Public method to be called by other controllers to modify the currentStats
     * field
     * 
     * @param stats
     */
    public static void updateStats(String stats) {
        SimStatsController.currentStats = stats;
    }

    /**
     * Redirects the user to the mainMenu
     * 
     * @param event
     * @throws IOException
     */
    public void backToMenu(ActionEvent event) throws IOException {
        App.setRoot("mainMenu");
    }
}
