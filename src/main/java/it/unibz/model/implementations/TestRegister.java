package it.unibz.model.implementations;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TestRegister(@JsonProperty("testNumber") int testNumber,
                @JsonProperty("requiredTimeOverTotalTime") String requiredTimeOverTotalTime,
                @JsonProperty("numberOfQuestions") int numberOfQuestions,
                @JsonProperty("correctlyAnsweredQuestions") int correctlyAnsweredQuestions,
                @JsonProperty("incorrectlyAnsweredQuestions") int incorrectlyAnsweredQuestions,
                @JsonProperty("blankQuestions") int blankQuestions,
                @JsonProperty("overallScore") double overallScore, @JsonProperty("topic") String topic,
                @JsonProperty("subtopics") String[] subtopics) {

}
