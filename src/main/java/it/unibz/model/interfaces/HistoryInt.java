package it.unibz.model.interfaces;

import java.util.List;

import it.unibz.model.implementations.Simulation;
import it.unibz.model.implementations.TestRegister;

public interface HistoryInt {
    /**
     * Gets all the testRegisters of the History object
     * 
     * @return all the test registers contained in the whole history
     */
    public List<TestRegister> getTestRegisters();

    /**
     * Adds a TestRegister to the list of TestRegisters of the History
     * 
     * @param register a register representing a past simulation to be added
     */
    public void addTestRegister(TestRegister register);

    /**
     * Converts a Simulation into a TestRegister and then adds it to the History
     * 
     * @param simulation The simulation to be converted into a register and then
     *                   added to the History
     */

    public void updateHistory(Simulation simulation);

    /**
     * Returns a String containing all the test registers of the History
     * 
     * @return a String representing the whole history
     */
    public String showHistory();
}
