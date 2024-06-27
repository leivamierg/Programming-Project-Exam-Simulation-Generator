package it.unibz.model.implementations;

import java.util.List;

public class History {
    private List<TestRegister> testRegisters;

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
