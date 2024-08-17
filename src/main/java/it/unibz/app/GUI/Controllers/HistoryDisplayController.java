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

public class HistoryDisplayController {
    @FXML
    Label history1, history2, history3, page;

    static History history;
    static int numRegisters;
    int i;

    // build a navigable "e-book" in which you can see a max of 3 registers per
    // "page"
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

    public void updatePage() {
        page.setText(Integer.toString(i + 1));
        buildTestRegisterPage(getDisplayedRegisters(i));
    }

    public void next(ActionEvent event) {
        if ((i + 1) * 3 < numRegisters) {
            i++;
            updatePage();
        }
    }

    public void previous(ActionEvent event) {
        if (0 < i) {
            i--;
            updatePage();
        }
    }

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

    private void buildTestRegisterPage(List<TestRegister> registers) {
        setTestRegisterLabel(history1, registers.get(0));
        setTestRegisterLabel(history2, registers.get(1));
        setTestRegisterLabel(history3, registers.get(2));
    }

    private void setTestRegisterLabel(Label label, TestRegister register) {
        if (register == null) {
            label.setText("");
        } else {
            label.setText(register.toString());
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("mainMenu");
    }
}
