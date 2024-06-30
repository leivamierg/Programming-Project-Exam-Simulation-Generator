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

    public int hashCode() {
        return Objects.hash(testNumber, requiredTimeOverTotalTime, numberOfQuestions, correctlyAnsweredQuestions,
                incorrectlyAnsweredQuestions, blankQuestions, overallScore, topic, subtopics);
    }
}
