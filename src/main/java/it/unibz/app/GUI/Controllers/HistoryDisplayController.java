package it.unibz.app.GUI.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.unibz.app.App;
import it.unibz.model.implementations.History;
import it.unibz.model.implementations.TestRegister;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Class meant to control the historyDisplay.fxml file and which displays the
 * previosuly completed tests in a sort of e-book with different pages and
 * entries
 */
public class HistoryDisplayController {
    @FXML
    private Label history1, history2, history3, page;

    static History history;
    static int numRegisters;
    int i;

    /**
     * Builds a navigable "e-book" in which you can see a max of 3 registers per
     * "page", everything is set up so yu can go from one page to the other and in
     * each page you can see at most 3 different previosu Simulation data
     */
    @FXML
    public void initialize() {
        new Thread() {
            public void run() {
                history = App.actionsController.getModel().getLoadedHistory();
                numRegisters = App.actionsController.getModel().getLoadedHistory().getTestRegisters().size();
                i = 0;

                Platform.runLater(new Runnable() {
                    public void run() {
                        updatePage();
                        System.out.println(numRegisters);
                    }
                });
            }

        }.start();
    }

    /**
     * Helper method to upfate the current page based on the index (i) field
     */
    private void updatePage() {
        page.setText(Integer.toString(i + 1));
        buildTestRegisterPage(getDisplayedRegisters(i));
    }

    /**
     * Method that takes the user to the next page of TestRegisters
     * 
     * @param event
     */
    public void next(ActionEvent event) {
        if ((i + 1) * 3 < numRegisters) {
            i++;
            updatePage();
        }
    }

    /**
     * Method that takes the user to the previosu page of TestRegisters
     * 
     * @param event
     */
    public void previous(ActionEvent event) {
        if (0 < i) {
            i--;
            updatePage();
        }
    }

    /**
     * Helper method to get the list of the 3 or less TestRegisters to be displayed
     * in the current page
     * 
     * @param index
     * @return
     */
    private static List<TestRegister> getDisplayedRegisters(int index) {
        List<TestRegister> displayedRegisterList = new ArrayList<>();
        int getter = index * 3;
        for (int i = 0; i < 3; i++) {
            if (getter + i >= numRegisters) {
                displayedRegisterList.add(null);
            } else {
                displayedRegisterList.add(history.getTestRegisters().get(getter + i));
            }
        }
        return displayedRegisterList;
    }

    /**
     * Helper method that modifies the history Labels texts to display the current
     * page
     * 
     * @param registers
     */
    private void buildTestRegisterPage(List<TestRegister> registers) {
        setTestRegisterLabel(history1, registers.get(0));
        setTestRegisterLabel(history2, registers.get(1));
        setTestRegisterLabel(history3, registers.get(2));
    }

    /**
     * Helper method to set The text of a sinle Label based on the info of a
     * TestRegister
     * 
     * @param label
     * @param register
     */
    private void setTestRegisterLabel(Label label, TestRegister register) {
        if (register == null) {
            label.setText("");
        } else {
            label.setText(register.toString());
        }
    }

    /**
     * Method that redirects the user to the mainMenu.fxml
     * 
     * @param event
     * @throws IOException
     */
    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("mainMenu");
    }
}
