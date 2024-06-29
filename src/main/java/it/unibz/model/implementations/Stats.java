package it.unibz.model.implementations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unibz.model.interfaces.StatsInt;

import java.util.*;

public class Stats implements StatsInt {
    private List<Simulation> simulations;
    private Map<String, List<Score>> topicToStats;
    private Map<String, List<Score>> subtopicToStats;
    private List<Score> generalStats;

    public Stats() {
        simulations = new ArrayList<>();
        topicToStats = new HashMap<>();
        subtopicToStats = new HashMap<>();
        generalStats = new ArrayList<>();
    }

    @JsonCreator
    public Stats(@JsonProperty("simulations") List<Simulation> simulations,
            @JsonProperty("topicToStats") Map<String, List<Score>> topicToStats,
            @JsonProperty("subtopicToStats") Map<String, List<Score>> subtopicToStats,
            @JsonProperty("generalStats") List<Score> generalStats) {
        setSimulations(simulations);
        setTopicToStats(topicToStats);
        setSubtopicToStats(subtopicToStats);
        setGeneralStats(generalStats);

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

    private void setGeneralStats(List<Score> generalStats) {
        this.generalStats = generalStats;
    }
    // methods

    @Override
    public void updateStats(Simulation simulation) {
        simulations.add(simulation);
        updateTopicToStats(simulation);
        updateSubtopicToStats(simulation);
        updateGeneralStats();
    }

    private void updateTopicToStats(Simulation simulation) {
        Set<Question> correctQuestions = new HashSet<>();
        Set<Question> wrongQuestions = new HashSet<>();
        Set<Question> blankQuestions = new HashSet<>();
        Set<Question> selectedQuestions = new HashSet<>();
        Set<Question> allQuestions = new HashSet<>();
        for (Simulation currentSim : simulations) {
            if (currentSim.getTopic().equals(simulation.getTopic()))
                updateCorWrongBlankSelTot(correctQuestions, wrongQuestions, blankQuestions, selectedQuestions,
                        allQuestions,
                        currentSim.getAllCorrectQuestions(), currentSim.getAllWrongQuestions(),
                        currentSim.getAllBlankQuestions(), new HashSet<>(currentSim.getAllQuestions()),
                        currentSim.getAllSelected_NonSelectedQuestions());
        }
        double percentage = computePercentage(correctQuestions.size(), selectedQuestions.size());

        List<Score> temp = topicToStats.get(simulation.getTopic());
        updateMap(topicToStats, simulation.getTopic(), temp,
                correctQuestions.size(), wrongQuestions.size(), blankQuestions.size(),
                selectedQuestions.size(), allQuestions.size(), percentage);
    }

    private void updateSubtopicToStats(Simulation simulation) {
        Set<Subtopic> selectedSubtopics = simulation.getSubtopicToQuestions().keySet();
        for (Subtopic subtopic : selectedSubtopics) {
            updateSubtopicStats(subtopic);
        }
    }

    private void updateSubtopicStats(Subtopic subtopic) {
        Set<Question> correctQuestions = new HashSet<>();
        Set<Question> wrongQuestions = new HashSet<>();
        Set<Question> blankQuestions = new HashSet<>();
        Set<Question> selectedQuestions = new HashSet<>();
        Set<Question> allQuestions = new HashSet<>();
        for (Simulation currentSim : simulations) {
            for (Subtopic currentSubtopic : currentSim.getSubtopicToQuestions().keySet()) {
                if (subtopic.getSubtopicName().equals(currentSubtopic.getSubtopicName()))
                    updateCorWrongBlankSelTot(correctQuestions, wrongQuestions, blankQuestions, selectedQuestions,
                            allQuestions,
                            currentSim.getSubtopicCorrectQuestions(subtopic),
                            currentSim.getSubtopicWrongQuestions(subtopic),
                            currentSim.getSubtopicBlankQuestions(subtopic),
                            currentSim.getSubtopicToQuestions().get(subtopic),
                            subtopic.getQuestions());
            }

        }
        double percentage = computePercentage(correctQuestions.size(), selectedQuestions.size());

        List<Score> temp = subtopicToStats.get(subtopic.getSubtopicName());
        updateMap(subtopicToStats, subtopic.getSubtopicName(), temp,
                correctQuestions.size(), wrongQuestions.size(), blankQuestions.size(),
                selectedQuestions.size(), allQuestions.size(), percentage);
    }

    private void updateGeneralStats() {
        int correct = 0, wrong = 0, blank = 0, selected = 0, total = 0;
        for (String topic : topicToStats.keySet()) {
            int idxLast = topicToStats.get(topic).size() - 1;
            Score topicStats = topicToStats.get(topic).get(idxLast);
            correct += topicStats.correct();
            wrong += topicStats.wrong();
            blank += topicStats.blank();
            selected += topicStats.selected();
            total += topicStats.total();
        }
        double percentage = computePercentage(correct, selected);
        generalStats.add(new Score(correct, wrong, blank, selected, total, percentage));
    }

    private void updateCorWrongBlankSelTot(Set<Question> correct, Set<Question> wrong,
            Set<Question> blank, Set<Question> selected, Set<Question> total,
            Set<Question> corToAdd, Set<Question> wrongToAdd,
            Set<Question> blankToAdd, Set<Question> selToAdd, Set<Question> totToAdd) {
        correct.addAll(corToAdd);
        wrong.addAll(wrongToAdd);
        blank.addAll(blankToAdd);
        selected.addAll(selToAdd);
        total.addAll(totToAdd);
        // if a question was answered once correctly and one wrongly or in blank
        // it is considered correct
        for (Question question : correct) {
            wrong.remove(question);
            blank.remove(question);
        }

        // if a question was answered once wrongly and one in blank
        // it is considered wrong
        for (Question question : wrong) {
            blank.remove(question);
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
        }
    }

    private double computePercentage(int correct, int selected) {
        double percentage = ((double) correct / selected) * 100;
        return Math.floor(percentage * 100) / 100;
    }

    @Override
    public String compareStats(Topic topic, int start, int end) {
        return printStatsComparison(topicToStats, start, end, topic.getTopicName());
    }

    @Override
    public String compareStats(Topic topic, int start) {
        return printStatsComparison(topicToStats, start, topicToStats.get(topic.getTopicName()).size(),
                topic.getTopicName());
    }

    @Override
    public String compareStats(Subtopic subtopic, int start, int end) {
        return printStatsComparison(subtopicToStats, start, end, subtopic.getSubtopicName());
    }

    @Override
    public String compareStats(Subtopic subtopic, int start) {
        return printStatsComparison(subtopicToStats, start, subtopicToStats.get(subtopic.getSubtopicName()).size(),
                subtopic.getSubtopicName());
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
                "Correctly answered questions/Selected questions: " + startingStats.correct() + "/"
                        + startingStats.selected(),
                "Correctly answered questions/Selected questions: " + endingStats.correct() + "/"
                        + endingStats.selected());
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

        // updateGeneralStats();

    }

    @Override
    public Map<String, List<Score>> getTopicToStats() {
        return topicToStats;
    }

    @Override
    public Map<String, List<Score>> getSubtopicToStats() {
        return subtopicToStats;
    }

    @Override
    public List<Score> getGeneralStats() {
        return generalStats;
    }

    public List<Simulation> getSimulations() {
        return simulations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Stats stats = (Stats) o;
        return Objects.equals(simulations, stats.simulations) && Objects.equals(topicToStats, stats.topicToStats)
                && Objects.equals(subtopicToStats, stats.subtopicToStats)
                && Objects.equals(generalStats, stats.generalStats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(simulations, topicToStats, subtopicToStats, generalStats);
    }
}
