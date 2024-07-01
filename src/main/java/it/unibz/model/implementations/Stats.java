package it.unibz.model.implementations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unibz.model.interfaces.StatsInt;

import java.util.*;

public class Stats implements StatsInt {
    private List<Simulation> simulations;
    private Map<String, List<Score>> topicToStats;
    private Map<String, List<Score>> subtopicToStats;
    private Score generalStats;

    public Stats() {
        simulations = new ArrayList<>();
        topicToStats = new HashMap<>();
        subtopicToStats = new HashMap<>();
    }

    @JsonCreator
    public Stats(@JsonProperty("simulations") List<Simulation> simulations,
            @JsonProperty("topicToStats") Map<String, List<Score>> topicToStats,
            @JsonProperty("subtopicToStats") Map<String, List<Score>> subtopicToStats,
            @JsonProperty("generalStats") Score generalStats) {
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

    private void setGeneralStats(Score generalStats) {
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

        List<Score> temp = topicToStats.get(simulation.getTopicName());
        updateMap(topicToStats, simulation.getTopicName(), temp,
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
        generalStats = new Score(correct, wrong, blank, selected, total, percentage);
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
    public String compareStats(Topic topic, int start, int end) throws IllegalArgumentException {
        if (topic == null) {
            throw new IllegalArgumentException();
        }
        return printStatsComparison(topicToStats, start, end, topic.getTopicName());
    }

    @Override
    public String compareStats(Topic topic, int start) throws IllegalArgumentException {
        if (topic == null) {
            throw new IllegalArgumentException();
        }
        return printStatsComparison(topicToStats, start, topicToStats.get(topic.getTopicName()).size(),
                topic.getTopicName());
    }

    @Override
    public String compareStats(Subtopic subtopic, int start, int end) throws IllegalArgumentException {
        if (subtopic == null) {
            throw new IllegalArgumentException();
        }
        return printStatsComparison(subtopicToStats, start, end, subtopic.getSubtopicName());
    }

    @Override
    public String compareStats(Subtopic subtopic, int start) throws IllegalArgumentException {
        if (subtopic == null) {
            throw new IllegalArgumentException();
        }
        return printStatsComparison(subtopicToStats, start, subtopicToStats.get(subtopic.getSubtopicName()).size(),
                subtopic.getSubtopicName());
    }

    private String printStatsComparison(Map<String, List<Score>> map,
            int start, int end, String topicSubtopic) throws IllegalArgumentException {

        if (start >= end) {
            throw new IllegalArgumentException();

        }
        if (start < 1) {
            start = 1;
            System.err.println("The start index is too small, so it was set to 1");
        }
        if (end > map.get(topicSubtopic).size()) {
            end = map.get(topicSubtopic).size();
            System.err.println("The end index is too big, so it was reset to the last sim");
        }

        Score startingStats = map.get(topicSubtopic).get(start - 1);
        Score endingStats = map.get(topicSubtopic).get(end - 1);
        String result = topicSubtopic + ":" + System.lineSeparator();
        result += String.format("%-60s %-60s" + System.lineSeparator(),
                "From simulation number " + start + ":", "To simulation number " + end + ":");
        result += String.format("%-60s %-60s" + System.lineSeparator(),
                "Total number of questions of " + topicSubtopic + ": " + startingStats.total(),
                "Total number of questions of " + topicSubtopic + ": " + endingStats.total());
        result += String.format("%-60s %-60s" + System.lineSeparator(),
                "Correctly answered questions/Selected questions: " + startingStats.correct() + "/"
                        + startingStats.selected(),
                "Correctly answered questions/Selected questions: " + endingStats.correct() + "/"
                        + endingStats.selected());
        result += String.format("%-60s %-60s" + System.lineSeparator(),
                "Incorrectly answered questions/Selected questions: " + startingStats.wrong() + "/"
                        + startingStats.selected(),
                "Incorrectly answered questions/Selected questions: " + endingStats.wrong() + "/"
                        + endingStats.selected());
        result += String.format("%-60s %-60s" + System.lineSeparator(),
                "Not answered questions/Selected questions: " + startingStats.blank() + "/"
                        + startingStats.selected(),
                "Not answered questions/Selected questions: " + endingStats.blank() + "/"
                        + endingStats.selected());
        result += String.format("%-60s %-60s",
                "Score: " + startingStats.percentage() + "%",
                "Score: " + endingStats.percentage() + "%");
        return result;
    }

    @Override
    public String showGeneralStats() {
        if (getGeneralStats() == null) {
            return "General Stats:" + System.lineSeparator() +
                    "Number of correct answers: 0/0" + System.lineSeparator() +
                    "Number of wrong answers: 0/0" + System.lineSeparator() +
                    "Number of blank answers: 0/0" + System.lineSeparator() +
                    "Total number of questions: 0" + System.lineSeparator() +
                    "Percentage: 0.0%";
        }

        int selected = getGeneralStats().selected();
        int correct = getGeneralStats().correct();
        int incorrect = getGeneralStats().wrong();
        int blank = getGeneralStats().blank();
        int total = getGeneralStats().total();
        double percentage = getGeneralStats().percentage();
        return "General Stats:" + System.lineSeparator() + "Number of correct answers: " + correct + "/" + selected
                + System.lineSeparator() + "Number of wrong answers: " + incorrect + "/" + selected
                + System.lineSeparator() + "Number of blank answers: " + blank + "/" + selected + System.lineSeparator()
                + "Total number of questions: " + total + System.lineSeparator() + "Percentage: " + percentage + "%";
        // general stats (average with all topics)
        // number of topics (with names)
        // stats for every topic
        // number of subtopics (with name) per topic
        // stats for every subtopic

    }

    @Override
    public String showTopicStats(Topic topic, int simNum) throws IllegalArgumentException {
        if (simNum < 1 || topicToStats.get(topic.getTopicName()).size() < simNum || topic == null) {
            throw new IllegalArgumentException();
        }

        Score topicStats = topicToStats.get(topic.getTopicName()).get(simNum - 1);
        int selected = topicStats.selected();
        int correct = topicStats.correct();
        int incorrect = topicStats.wrong();
        int blank = topicStats.blank();
        int total = topicStats.total();
        double percentage = topicStats.percentage();
        return topic.getTopicName() + " Stats at Simulation #" + simNum + ":" + System.lineSeparator()
                + "Number of correct answers: " + correct + "/"
                + selected
                + System.lineSeparator() + "Number of wrong answers: " + incorrect + "/" + selected
                + System.lineSeparator() + "Number of blank answers: " + blank + "/" + selected + System.lineSeparator()
                + "Total number of questions: " + total + System.lineSeparator() + "Percentage: " + percentage + "%";

    }

    @Override
    public String showSubtopicStats(Subtopic subtopic, int simNum) throws IllegalArgumentException {
        if (simNum < 1 || subtopicToStats.get(subtopic.getSubtopicName()).size() < simNum || subtopic == null) {
            throw new IllegalArgumentException();
        }

        Score subtopicStats = subtopicToStats.get(subtopic.getSubtopicName()).get(simNum - 1);
        int selected = subtopicStats.selected();
        int correct = subtopicStats.correct();
        int incorrect = subtopicStats.wrong();
        int blank = subtopicStats.blank();
        int total = subtopicStats.total();
        double percentage = subtopicStats.percentage();
        return subtopic.getSubtopicName() + " Stats at Simulation #" + simNum + ":" + System.lineSeparator()
                + "Number of correct answers: " + correct + "/"
                + selected
                + System.lineSeparator() + "Number of wrong answers: " + incorrect + "/" + selected
                + System.lineSeparator() + "Number of blank answers: " + blank + "/" + selected + System.lineSeparator()
                + "Total number of questions: " + total + System.lineSeparator() + "Percentage: " + percentage + "%";

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
    public Score getGeneralStats() {
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
