package it.unibz.model.implementations;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class History {
    private List<TestRegister> testRegisters;

    @JsonCreator
    public History(@JsonProperty("testRegisters") List<TestRegister> testRegisters) {
        setTestRegisters(testRegisters);
    }
    // methods

    public List<TestRegister> getTestRegisters() {
        return this.testRegisters;
    }

    private void setTestRegisters(List<TestRegister> registers) {
        this.testRegisters = registers;
    }

    public void addTestRegister(TestRegister register) {
        this.testRegisters.add(register);
    }

    // TODO: add updateHistory
    public void updateHistory(Simulation simulation) {
        TestRegister newTestRegister = new TestRegister(this.getTestRegisters().size() + 1,
                simulation.getTimer().getRemainingTime() + " out of " + simulation.getTimer().DURATION_SIMULATION,
                simulation.getNumberOfQuestions(), simulation.getAllCorrectQuestions().size(),
                simulation.getAllWrongQuestions().size(), simulation.getAllBlankQuestions().size(),
                simulation.computeSimStats().percentage(),
                simulation.getTopic(), (String[]) simulation.getSubtopicToQuestions().keySet().stream()
                        .map((subtopic) -> (subtopic.getSubtopicName())).toArray());
        this.addTestRegister(newTestRegister);
    }
}
