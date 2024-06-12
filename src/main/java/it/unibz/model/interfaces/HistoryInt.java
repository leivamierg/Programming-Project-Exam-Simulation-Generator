package it.unibz.model.interfaces;

import it.unibz.model.implementations.TestRegister;

public interface HistoryInt {

    public TestRegister getTestRegisterByIndex(int i);

    public int getOverallScore();

    public void addTestRegister(TestRegister register);

}
