package it.unibz.model.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unibz.model.interfaces.HistoryInt;

public class History implements HistoryInt {
    private List<TestRegister> testRegisters;

    @JsonCreator
    public History(@JsonProperty("testRegisters") List<TestRegister> testRegisters) {
        setTestRegisters(testRegisters);
    }

    public History() {
        setTestRegisters(new ArrayList<TestRegister>());
    }
    // methods

    @Override
    public List<TestRegister> getTestRegisters() {
        return this.testRegisters;
    }

    private void setTestRegisters(List<TestRegister> registers) {
        this.testRegisters = registers;
    }

    @Override
    public void addTestRegister(TestRegister register) {
        this.testRegisters.add(register);
    }

    @Override
    public void updateHistory(Simulation simulation) {
        List<String> selectedSubtopics = simulation.getSubtopicToQuestions().keySet().stream()
                .map((subtopic) -> (subtopic.getSubtopicName())).toList();
        TestRegister newTestRegister = new TestRegister(this.getTestRegisters().size() + 1,
                simulation.getTimer().getRemainingTime() + " out of " + simulation.getTimer().DURATION_SIMULATION,
                simulation.getNumberOfQuestions(), simulation.getAllCorrectQuestions().size(),
                simulation.getAllWrongQuestions().size(), simulation.getAllBlankQuestions().size(),
                simulation.computeSimStats().percentage(),
                simulation.getTopic(), selectedSubtopics.toArray(selectedSubtopics.toArray(new String[0])));
        this.addTestRegister(newTestRegister);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof History)) {
            return false;
        }

        else
            return ((History) o).getTestRegisters().equals(this.getTestRegisters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(testRegisters);
    }

    @Override
    public String showHistory() {
        String wholeString = new String("Complete History:" + System.lineSeparator());
        for (int i = 0; i < getTestRegisters().size(); i++) {
            wholeString = wholeString + getTestRegisters().get(i).toString() + System.lineSeparator();
        }
        return wholeString;
    }
}
