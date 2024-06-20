package it.unibz.model.implementations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unibz.model.interfaces.StatsInt;

import java.util.*;
import java.util.stream.Collectors;

public class Stats implements StatsInt {
    private static List<Simulation> simulations;
    private static Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> topicToStats;
    private static Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> subtopicToStats;

    @JsonCreator
    public Stats(@JsonProperty("simulations") List<Simulation> simulations, 
                 @JsonProperty("topicToStats") Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> topicToStats,
                 @JsonProperty("subtopicToStats") Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> subtopicToStats) {
        setSimulations(simulations);
        setTopicToStats(topicToStats);
        setSubtopicToStats(subtopicToStats);
        
    }
    // setter

    private void setSimulations(List<Simulation> simulations) {
        Stats.simulations = simulations;
    }

    private void setTopicToStats(Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> topicToStats) {
        Stats.topicToStats = topicToStats;
    }

    private void setSubtopicToStats(Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> subtopicToStats) {
        Stats.subtopicToStats = subtopicToStats;
    }
    // methods

    @Override
    public void updateStats(Simulation simulation) {
        simulations.add(simulation);
        updateTopicToStats(simulation);
        updateSubtopicToStats();
    }

    private void updateTopicToStats(Simulation simulation) {
        Set<Question> correctQuestions = new HashSet<>();
        Set<Question> selectedQuestions = new HashSet<>();
        Set<Question> allQuestions = simulation.getTopicReference().getSubtopics().stream().
                flatMap(s -> s.getQuestions().stream()).
                collect(Collectors.toSet());
        for (Simulation currentSim : simulations) {
            if (currentSim.getTopic().equals(simulation.getTopic())) {
                correctQuestions.addAll(currentSim.getAllCorrectQuestions());
                selectedQuestions.addAll(currentSim.getAllQuestions());
            }

        }
        double percentage = ((double) correctQuestions.size() / selectedQuestions.size()) * 100;

        List<Correct_Selected_TotalQuestionsAndPercentage> temp = topicToStats.get(simulation.getTopic());
        if (temp == null) {
            List<Correct_Selected_TotalQuestionsAndPercentage> stats = new ArrayList<>();
            stats.add(new Correct_Selected_TotalQuestionsAndPercentage(correctQuestions.size(),
                    selectedQuestions.size(), allQuestions.size(), percentage));
            topicToStats.put(simulation.getTopic(), stats);
        } else {
            temp.add(new Correct_Selected_TotalQuestionsAndPercentage(correctQuestions.size(),
                    selectedQuestions.size(), allQuestions.size(), percentage));
            topicToStats.put(simulation.getTopic(), temp);
        }

    }

    private void updateSubtopicToStats() {
        simulations.stream().
                flatMap(s -> s.getQuestionsPerSubtopic().keySet().stream()).
                forEach(this::updateSubtopicStats);
    }

    private void updateSubtopicStats(Subtopic subtopic) {
        Set<Question> correctQuestions = new HashSet<>();
        Set<Question> selectedQuestions = new HashSet<>();
        Set<Question> allQuestions = subtopic.getQuestions();
        for (Simulation simulation : simulations) {
            for (Subtopic currentSubtopic : simulation.getQuestionsPerSubtopic().keySet()) {
                if (subtopic.getSubtopicName().equals(currentSubtopic.getSubtopicName())) {
                    correctQuestions.addAll(simulation.getSubtopicCorrectQuestions(subtopic));
                    selectedQuestions.addAll(simulation.getQuestionsPerSubtopic().get(subtopic));
                }
            }

        }

        double percentage = ((double) correctQuestions.size() / selectedQuestions.size()) * 100;

        List<Correct_Selected_TotalQuestionsAndPercentage> temp = subtopicToStats.get(subtopic.getSubtopicName());
        if (temp == null) {
            List<Correct_Selected_TotalQuestionsAndPercentage> stats = new ArrayList<>();
            stats.add(new Correct_Selected_TotalQuestionsAndPercentage(correctQuestions.size(),
                    selectedQuestions.size(), allQuestions.size(), percentage));
            subtopicToStats.put(subtopic.getSubtopicName(), stats);
        } else {
            temp.add(new Correct_Selected_TotalQuestionsAndPercentage(correctQuestions.size(),
                    selectedQuestions.size(), allQuestions.size(), percentage));
            topicToStats.put(subtopic.getSubtopicName(), temp);
        }
    }

    @Override
    public String compareStats(Topic topic, int start, int end) {
        return null;
    }

    @Override
    public String compareStats(Topic topic, int start) {
        return null;
    }

    @Override
    public String compareStats(Subtopic subtopic, int start, int end) {
        return null;
    }

    @Override
    public String compareStats(Subtopic subtopic, int start) {
        return null;
    }

    @Override
    public String showStats() {
        return null;
    }
}
