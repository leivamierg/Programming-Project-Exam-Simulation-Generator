package it.unibz.model.implementations;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TestRegister(@JsonProperty("testNumber") int testNumber,
        @JsonProperty("requiredTimeOverTotalTime") String requiredTimeOverTotalTime,
        @JsonProperty("numberOfQuestions") int numberOfQuestions,
        @JsonProperty("correctlyAnsweredQuestions") int correctlyAnsweredQuestions,
        @JsonProperty("incorrectlyAnsweredQuestions") int incorrectlyAnsweredQuestions,
        @JsonProperty("blankQuestions") int blankQuestions,
        @JsonProperty("overallScore") double overallScore, @JsonProperty("topic") String topic,
        @JsonProperty("subtopics") String[] subtopics) {

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof TestRegister)) {
            return false;
        }

        else
            return ((TestRegister) o).testNumber() == this.testNumber()
                    && ((TestRegister) o).requiredTimeOverTotalTime().equals(this.requiredTimeOverTotalTime())
                    && ((TestRegister) o).numberOfQuestions() == (this.numberOfQuestions())
                    && ((TestRegister) o).correctlyAnsweredQuestions() == (this.correctlyAnsweredQuestions())
                    && ((TestRegister) o).incorrectlyAnsweredQuestions() == (this.incorrectlyAnsweredQuestions())
                    && ((TestRegister) o).blankQuestions() == (this.blankQuestions())
                    && ((TestRegister) o).overallScore() == (this.overallScore())
                    && ((TestRegister) o).topic().equals(this.topic())
                    && ((TestRegister) o).subtopics().equals(this.subtopics());
    }

    @Override
    public int hashCode() {
        return Objects.hash(testNumber, requiredTimeOverTotalTime, numberOfQuestions, correctlyAnsweredQuestions,
                incorrectlyAnsweredQuestions, blankQuestions, overallScore, topic, subtopics);
    }

    @Override
    public String toString() {
        return "Test Number #" + testNumber() + System.lineSeparator() + "Required time over total time: "
                + requiredTimeOverTotalTime() + System.lineSeparator() + "Number of selected questions: "
                + numberOfQuestions() + System.lineSeparator() + "Number of correct answers: "
                + correctlyAnsweredQuestions() + System.lineSeparator() + "Wrong answers: "
                + incorrectlyAnsweredQuestions() + System.lineSeparator() + "Blank answers: " + blankQuestions()
                + System.lineSeparator() + "Overall Score: " + overallScore() + System.lineSeparator() + "Topic: "
                + topic() + System.lineSeparator() + "Subtopics: " + subtopics();
    }
}
