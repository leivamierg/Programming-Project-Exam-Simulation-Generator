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
                secondsToString(simulation.getTimer().getRemainingTime()) + " out of "
                        + secondsToString(simulation.getTimer().DURATION_SIMULATION),
                simulation.getNumberOfQuestions(), simulation.getAllCorrectQuestions().size(),
                simulation.getAllWrongQuestions().size(), simulation.getAllBlankQuestions().size(),
                simulation.computeSimStats().percentage(),
                simulation.getTopicName(), selectedSubtopics.toArray(selectedSubtopics.toArray(new String[0])));
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

    private String secondsToString(int totalSeconds) {
        int minutes, seconds;
        minutes = totalSeconds / 60;
        seconds = totalSeconds % 60;

        String minutesString = String.valueOf(minutes);
        String secondsString = String.valueOf(seconds);

        if (minutesString.length() == 1) {
            minutesString = "0" + minutesString;
        }
        if (secondsString.length() == 1) {
            secondsString = "0" + secondsString;
        }

        return minutesString + ":" + secondsString;
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
