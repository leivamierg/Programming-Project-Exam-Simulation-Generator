package it.unibz.app.GUI.Controllers;

import java.io.IOException;

import it.unibz.app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GeneralStatsDisplayController {
    @FXML
    Label generalStats;
    static String statsText;

    @FXML
    public void initialize() {
        generalStats.setText(statsText);
    }

    public void goBack() throws IOException {
        App.setRoot("mainMenu");
    }

    public static void displayStats(String stats) {
        statsText = stats;
        System.out.println(App.actionsController.getModel().getLoadedStats().getSimulations().size());
    }
}
