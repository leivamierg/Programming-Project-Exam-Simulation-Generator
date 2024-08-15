package it.unibz.app.GUI.Controllers;

import it.unibz.app.App;
import it.unibz.model.implementations.History;

public class HistoryDisplayController {
    History history = App.actionsController.getModel().getLoadedHistory();

    // build a navigable "e-book" in which you can see a max of 3 registers per
    // "page"
    public void initialize() {

    }
}
