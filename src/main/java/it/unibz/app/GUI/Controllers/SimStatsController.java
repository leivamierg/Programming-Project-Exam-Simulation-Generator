package it.unibz.app.GUI.Controllers;

import java.io.IOException;

import it.unibz.app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SimStatsController {
    @FXML
    Label statsContent;
    public static String currentStats;

    public void initialize() {
        statsContent.setText(currentStats);
    }

    public static void updateStats(String stats) {
        SimStatsController.currentStats = stats;
    }

    public void backToMenu(ActionEvent event) throws IOException {
        App.setRoot("mainMenu");
    }
}
