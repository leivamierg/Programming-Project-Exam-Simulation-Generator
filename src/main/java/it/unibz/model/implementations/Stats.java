package it.unibz.model.implementations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unibz.model.interfaces.StatsInt;

import java.util.*;
import java.util.stream.Collectors;

public class Stats implements StatsInt {
    private List<Simulation> simulations;
    private Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> topicToStats;
    private Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> subtopicToStats;

    public Stats() {
        simulations = new ArrayList<>();
        topicToStats = new HashMap<>();
        subtopicToStats = new HashMap<>();
    }
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
        this.simulations = simulations;
    }

    private void setTopicToStats(Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> topicToStats) {
        this.topicToStats = topicToStats;
    }

    private void setSubtopicToStats(Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> subtopicToStats) {
        this.subtopicToStats = subtopicToStats;
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
            // if (currentSim.getTopic().equals(simulation.getTopic()))
                updateCorSel(correctQuestions, selectedQuestions,
                        currentSim.getAllCorrectQuestions(), new HashSet<>(currentSim.getAllQuestions()),
                        simulation.getTopic(), currentSim.getTopic());
        }
        double percentage = ((double) correctQuestions.size() / selectedQuestions.size()) * 100;

        List<Correct_Selected_TotalQuestionsAndPercentage> temp = topicToStats.get(simulation.getTopic());
        updateMap(subtopicToStats, simulation.getTopic(), temp,
                correctQuestions.size(), selectedQuestions.size(), allQuestions.size(), percentage);
        /*if (temp == null) {
            List<Correct_Selected_TotalQuestionsAndPercentage> stats = new ArrayList<>();
            stats.add(new Correct_Selected_TotalQuestionsAndPercentage(correctQuestions.size(),
                    selectedQuestions.size(), allQuestions.size(), percentage));
            topicToStats.put(simulation.getTopic(), stats);
        } else {
            temp.add(new Correct_Selected_TotalQuestionsAndPercentage(correctQuestions.size(),
                    selectedQuestions.size(), allQuestions.size(), percentage));
            topicToStats.put(simulation.getTopic(), temp);
        }*/

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
                // if (subtopic.getSubtopicName().equals(currentSubtopic.getSubtopicName()))
                    updateCorSel(correctQuestions, selectedQuestions,
                            simulation.getSubtopicCorrectQuestions(subtopic), simulation.getQuestionsPerSubtopic().get(subtopic),
                            subtopic.getSubtopicName(), currentSubtopic.getSubtopicName());
            }

        }
        double percentage = ((double) correctQuestions.size() / selectedQuestions.size()) * 100;

        List<Correct_Selected_TotalQuestionsAndPercentage> temp = subtopicToStats.get(subtopic.getSubtopicName());
        updateMap(subtopicToStats, subtopic.getSubtopicName(), temp,
                correctQuestions.size(), selectedQuestions.size(), allQuestions.size(), percentage);
        /*if (temp == null) {
            List<Correct_Selected_TotalQuestionsAndPercentage> stats = new ArrayList<>();
            stats.add(new Correct_Selected_TotalQuestionsAndPercentage(correctQuestions.size(),
                    selectedQuestions.size(), allQuestions.size(), percentage));
            subtopicToStats.put(subtopic.getSubtopicName(), stats);
        } else {
            temp.add(new Correct_Selected_TotalQuestionsAndPercentage(correctQuestions.size(),
                    selectedQuestions.size(), allQuestions.size(), percentage));
            topicToStats.put(subtopic.getSubtopicName(), temp);
        }*/
    }

    private void updateCorSel(Set<Question> correct, Set<Question> selected,
                              Set<Question> corToAdd, Set<Question> selToAdd,
                              String topicSubtopic, String currentTopicSubtopic) {
        if (topicSubtopic.equals(currentTopicSubtopic)) {
            correct.addAll(corToAdd);
            selected.addAll(selToAdd);
        }
    }

    private void updateMap(Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> map,
                           String topicSubtopic, List<Correct_Selected_TotalQuestionsAndPercentage> temp,
                           int nrCorrect, int nrSelected, int nrTotQ, double percentage) {
        if (temp == null) {
            List<Correct_Selected_TotalQuestionsAndPercentage> stats = new ArrayList<>();
            stats.add(new Correct_Selected_TotalQuestionsAndPercentage(nrCorrect, nrSelected, nrTotQ, percentage));
            map.put(topicSubtopic, stats);
        } else {
            temp.add(new Correct_Selected_TotalQuestionsAndPercentage(nrCorrect, nrSelected, nrTotQ, percentage));
            map.put(topicSubtopic, temp);
        }
    }

    @Override
    public String compareStats(Topic topic, int start, int end) {
        return printStatsComparison(topicToStats, start, end, topic.getTopicName());
    }

    @Override
    public String compareStats(Topic topic, int start) {
        return printStatsComparison(topicToStats, start, topicToStats.get(topic.getTopicName()).size(), topic.getTopicName());
    }

    @Override
    public String compareStats(Subtopic subtopic, int start, int end) {
        return printStatsComparison(subtopicToStats, start, end, subtopic.getSubtopicName());
    }

    @Override
    public String compareStats(Subtopic subtopic, int start) {
        return printStatsComparison(subtopicToStats, start, subtopicToStats.get(subtopic.getSubtopicName()).size(), subtopic.getSubtopicName());
    }

    private String printStatsComparison(Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> map,
                                        int start, int end, String topicSubtopic) {
        Correct_Selected_TotalQuestionsAndPercentage startingStats = map.get(topicSubtopic).get(start - 1);
        Correct_Selected_TotalQuestionsAndPercentage endingStats = map.get(topicSubtopic).get(end - 1);
        String result = topicSubtopic + ":" + System.lineSeparator();
        result += String.format("%-10s %-10s" + System.lineSeparator(),
                "From simulation number " + start + ":", "To simulation number " + end + ":");
        result += String.format("%-10s %-10s" + System.lineSeparator(),
                "Total number of questions of " + topicSubtopic + ": " + startingStats.total(),
                "Total number of questions of " + topicSubtopic + ": " + endingStats.total());
        result += String.format("%-10s %-10s" + System.lineSeparator(),
                "Correctly answered questions/Selected questions: " + startingStats.correct() + "/" + startingStats.selected(),
                "Correctly answered questions/Selected questions: " + endingStats.correct() + "/" + endingStats.selected());
        result += String.format("%-10s %-10s",
                "Score: " + startingStats.percentage(),
                "Score: " + endingStats.percentage());
        return result;
    }

    @Override
    public String showStats() {
        return null;
        // update general stats
        // general stats (average with all topics)
        // number of topics (with names)
        // stats for every topic
        // number of subtopics (with name) per topic
        // stats for every subtopic
    }
    @Override
    public Correct_Selected_TotalQuestionsAndPercentage updateGeneralStats() {
        int correct = 0, selected = 0, total = 0;
        for (String topic : topicToStats.keySet()) {
            int idxLastStats = topicToStats.get(topic).size() - 1;
            Correct_Selected_TotalQuestionsAndPercentage topicStats = topicToStats.get(topic).get(idxLastStats);
            correct += topicStats.correct();
            selected = topicStats.selected();
            total = topicStats.total();
        }
        return new Correct_Selected_TotalQuestionsAndPercentage(correct, selected, total, (double) correct/selected);
    }

    @Override
    public Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> getTopicToStats() {
        return topicToStats;
    }

    @Override
    public Map<String, List<Correct_Selected_TotalQuestionsAndPercentage>> getSubtopicToStats() {
        return subtopicToStats;
    }
}
