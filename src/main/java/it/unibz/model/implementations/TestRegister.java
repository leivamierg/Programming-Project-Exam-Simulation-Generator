package it.unibz.model.implementations;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/*public record TestRegister(int testNumber, String requiredTimeOverTotalTime, int numberOfQuestions,
                           int correctlyAnsweredQuestions, int incorrectlyAnsweredQuestions, int blankQuestions,
                           double overallScore, String topic, String[] subtopics) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestRegister that = (TestRegister) o;
        return testNumber == that.testNumber && numberOfQuestions == that.numberOfQuestions && correctlyAnsweredQuestions == that.correctlyAnsweredQuestions && incorrectlyAnsweredQuestions == that.incorrectlyAnsweredQuestions && blankQuestions == that.blankQuestions && Double.compare(overallScore, that.overallScore) == 0 && Objects.equals(requiredTimeOverTotalTime, that.requiredTimeOverTotalTime) && Objects.equals(topic, that.topic) && Arrays.equals(subtopics, that.subtopics);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(testNumber, requiredTimeOverTotalTime, numberOfQuestions, correctlyAnsweredQuestions, incorrectlyAnsweredQuestions, blankQuestions, overallScore, topic);
        result = 31 * result + Arrays.hashCode(subtopics);
        return result;
    }
}*/

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
                    // && ((TestRegister) o).subtopics().equals(this.subtopics());
                    && Arrays.equals(subtopics, ((TestRegister) o).subtopics());
    }
    @Override
    public int hashCode() {
        return Objects.hash(testNumber, requiredTimeOverTotalTime, numberOfQuestions, correctlyAnsweredQuestions,
                incorrectlyAnsweredQuestions, blankQuestions, overallScore, topic, /*subtopics*/ Arrays.hashCode(subtopics));
    }
}
