package it.unibz.app.GUI.Controllers;

import java.io.IOException;

import it.unibz.app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Class which handles the generalStatsDisplay.fxml file and shows the overall
 * stats taking every simulation completed in the past
 */
public class GeneralStatsDisplayController {
    @FXML
    private Label generalStats;
    static String statsText;

    /**
     * Initialize method to set the Text of the generalStats label
     */
    @FXML
    public void initialize() {
        generalStats.setText(statsText);
    }

    /**
     * Redirects the user to the mainMenu
     * 
     * @throws IOException
     */
    public void goBack() throws IOException {
        App.setRoot("mainMenu");
    }

    /**
     * Public method to be accessed by other controllers to modify the statsText
     * field
     * 
     * @param stats
     */
    public static void displayStats(String stats) {
        statsText = stats;
        System.out.println(App.actionsController.getModel().getLoadedStats().getSimulations().size());
    }
}
