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
}
