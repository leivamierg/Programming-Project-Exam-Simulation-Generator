package it.unibz.model.implementations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unibz.model.interfaces.StatsInt;

import java.util.*;
import java.util.stream.Collectors;

public class Stats implements StatsInt {
    private List<Simulation> simulations;
    private Map<String, List<Score>> topicToStats;
    private Map<String, List<Score>> subtopicToStats;

    public Stats() {
        simulations = new ArrayList<>();
        topicToStats = new HashMap<>();
        subtopicToStats = new HashMap<>();
    }
    @JsonCreator
    public Stats(@JsonProperty("simulations") List<Simulation> simulations, 
                 @JsonProperty("topicToStats") Map<String, List<Score>> topicToStats,
                 @JsonProperty("subtopicToStats") Map<String, List<Score>> subtopicToStats) {
        setSimulations(simulations);
        setTopicToStats(topicToStats);
        setSubtopicToStats(subtopicToStats);
        
    }
    // setter

    private void setSimulations(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    private void setTopicToStats(Map<String, List<Score>> topicToStats) {
        this.topicToStats = topicToStats;
    }

    private void setSubtopicToStats(Map<String, List<Score>> subtopicToStats) {
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
        Set<Question> wrongQuestions = new HashSet<>();
        Set<Question> blankQuestions = new HashSet<>();
        Set<Question> selectedQuestions = new HashSet<>();
        Set<Question> allQuestions = simulation.getAllSelected_NonSelectedQuestions();
        for (Simulation currentSim : simulations) {
            // if (currentSim.getTopic().equals(simulation.getTopic()))
                updateCorWrongBlankSel(correctQuestions, wrongQuestions, blankQuestions, selectedQuestions,
                        currentSim.getAllCorrectQuestions(), currentSim.getAllWrongQuestions(),
                        currentSim.getAllBlankQuestions(), new HashSet<>(currentSim.getAllQuestions()),
                        simulation.getTopic(), currentSim.getTopic());
        }
        double percentage = ((double) correctQuestions.size() / selectedQuestions.size()) * 100;

        List<Score> temp = topicToStats.get(simulation.getTopic());
        updateMap(subtopicToStats, simulation.getTopic(), temp,
                correctQuestions.size(), wrongQuestions.size(), blankQuestions.size(),
                selectedQuestions.size(), allQuestions.size(), percentage);
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
                flatMap(s -> s.getSubtopicToQuestions().keySet().stream()).
                forEach(this::updateSubtopicStats);
    }

    private void updateSubtopicStats(Subtopic subtopic) {
        Set<Question> correctQuestions = new HashSet<>();
        Set<Question> wrongQuestions = new HashSet<>();
        Set<Question> blankQuestions = new HashSet<>();
        Set<Question> selectedQuestions = new HashSet<>();
        Set<Question> allQuestions = subtopic.getQuestions();
        for (Simulation simulation : simulations) {
            for (Subtopic currentSubtopic : simulation.getSubtopicToQuestions().keySet()) {
                // if (subtopic.getSubtopicName().equals(currentSubtopic.getSubtopicName()))
                    updateCorWrongBlankSel(correctQuestions, wrongQuestions, blankQuestions, selectedQuestions,
                            simulation.getSubtopicCorrectQuestions(subtopic), simulation.getSubtopicWrongQuestions(subtopic),
                            simulation.getSubtopicBlankQuestions(subtopic), simulation.getSubtopicToQuestions().get(subtopic),
                            subtopic.getSubtopicName(), currentSubtopic.getSubtopicName());
            }

        }
        double percentage = ((double) correctQuestions.size() / selectedQuestions.size()) * 100;

        List<Score> temp = subtopicToStats.get(subtopic.getSubtopicName());
        updateMap(subtopicToStats, subtopic.getSubtopicName(), temp,
                correctQuestions.size(), wrongQuestions.size(), blankQuestions.size(),
                selectedQuestions.size(), allQuestions.size(), percentage);
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

    private void updateCorWrongBlankSel(Set<Question> correct, Set<Question> wrong,
                                        Set<Question> blank, Set<Question> selected,
                                        Set<Question> corToAdd, Set<Question> wrongToAdd,
                                        Set<Question> blankToAdd, Set<Question> selToAdd,
                              String topicSubtopic, String currentTopicSubtopic) {
        if (topicSubtopic.equals(currentTopicSubtopic)) {
            correct.addAll(corToAdd);
            wrong.addAll(wrongToAdd);
            blank.addAll(blankToAdd);
            selected.addAll(selToAdd);
        }
    }

    private void updateMap(Map<String, List<Score>> map,
                           String topicSubtopic, List<Score> temp,
                           int nrCorrect, int nrWrong, int nrBlank, int nrSelected, int nrTotal, double percentage) {
        Score score = new Score(nrCorrect, nrWrong, nrBlank, nrSelected, nrTotal, percentage);
        if (temp == null) {
            List<Score> stats = new ArrayList<>();
            stats.add(score);
            map.put(topicSubtopic, stats);
        } else {
            temp.add(score);
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

    private String printStatsComparison(Map<String, List<Score>> map,
                                        int start, int end, String topicSubtopic) {
        Score startingStats = map.get(topicSubtopic).get(start - 1);
        Score endingStats = map.get(topicSubtopic).get(end - 1);
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
    public Score updateGeneralStats() {
        int correct = 0, wrong = 0, blank = 0, selected = 0, total = 0;
        for (String topic : topicToStats.keySet()) {
            int idxLastStats = topicToStats.get(topic).size() - 1;
            Score topicStats = topicToStats.get(topic).get(idxLastStats);
            correct += topicStats.correct();
            wrong += topicStats.correct();
            blank += topicStats.correct();
            selected += topicStats.selected();
            total += topicStats.total();
        }
        return new Score(correct, wrong, blank, selected, total, (double) correct/selected);
    }

    @Override
    public Map<String, List<Score>> getTopicToStats() {
        return topicToStats;
    }

    @Override
    public Map<String, List<Score>> getSubtopicToStats() {
        return subtopicToStats;
    }
}
