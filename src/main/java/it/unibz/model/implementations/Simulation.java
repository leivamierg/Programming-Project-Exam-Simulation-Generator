package it.unibz.model.implementations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unibz.model.interfaces.SimulationInt;

import java.util.*;
import java.util.stream.Collectors;

public class Simulation implements SimulationInt {
    // attributes
    private Map<Subtopic, Set<Question>> subtopicToQuestions;
    private Map<Question, Character> questionToAnswer;
    private Map<Question, Map<String, Character>> questionToShuffledAnswers;
    private Question currentQuestion;

    public Simulation() {
        subtopicToQuestions = new HashMap<>();
        questionToAnswer = new HashMap<>();
        questionToShuffledAnswers = new HashMap<>();
    }
    @JsonCreator
    public Simulation(@JsonProperty("subtopicToQuestions") Map<Subtopic, Set<Question>> subtopicToQuestions,
                      @JsonProperty("questionToAnswer") Map<Question, Character> questionToAnswer,
                      @JsonProperty("questionToShuffledAnswers") Map<Question, Map<String, Character>> questionToShuffledAnswers) {
        setSubtopicToQuestions(subtopicToQuestions);
        setQuestionToAnswer(questionToAnswer);
        setQuestionToShuffledAnswers(questionToShuffledAnswers);
    }

    @Override
    public void select(Topic topic, int nrQuestionsPerSubtopic) throws NullPointerException {
        for (Subtopic subtopic : topic.getSubtopics()) {
            updateSubtopicToQuestions(subtopic, nrQuestionsPerSubtopic);
        }
    }

    @Override
    public void select(Set<Subtopic> subtopics, int nrQuestionsPerSubtopic) throws
            IllegalStateException, IllegalArgumentException, NullPointerException {
        if (!subtopics.isEmpty()) {
            List<Subtopic> temp = new ArrayList<>(subtopics);
            String topic = temp.get(0).getTopic();
            for (Subtopic subtopic : subtopics) {
                if (!subtopic.getTopic().equals(topic))
                    throw new IllegalArgumentException();
                updateSubtopicToQuestions(subtopic, nrQuestionsPerSubtopic);
            }
        } else throw new IllegalStateException();
    }

    private void updateSubtopicToQuestions(Subtopic subtopic, int nrQuestionsPerSubtopic) {
        Set<Question> pickedQuestions = subtopic.pickQuestions(nrQuestionsPerSubtopic);
        subtopicToQuestions.put(subtopic, pickedQuestions);
        updateQuestionToShuffledAnswers(pickedQuestions);
    }

    private void updateQuestionToShuffledAnswers(Set<Question> questions) {
        for (Question question : questions) {
            questionToShuffledAnswers.put(question, question.getShuffleMap());
        }
    }

    @Override
    public void start() {
        setCurrentQuestion(getAllQuestions().get(0));
    }

    @Override
    public void answer(char answer) throws IllegalArgumentException {
        switch (Character.toUpperCase(answer)) {
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case '-':
                questionToAnswer.put(currentQuestion, Character.toUpperCase(answer));
                break;
            default:
                throw new IllegalArgumentException();
        }
        List<Question> allQuestions = getAllQuestions();
        if (allQuestions.indexOf(currentQuestion) < allQuestions.size() - 1) {
            changeQuestion('+');
        }
    }

    @Override
    public void answer(Question question, char answer) throws IllegalArgumentException {
        switch (Character.toUpperCase(answer)) {
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case '-':
                questionToAnswer.put(question, Character.toUpperCase(answer));
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void changeQuestion(char prevOrNext) throws IllegalArgumentException {
        List<Question> allQuestions = getAllQuestions();
        int idxCurrentQuestion = allQuestions.indexOf(currentQuestion);
        if (prevOrNext != '+' && prevOrNext != '-')
            throw new IllegalArgumentException();
        if (idxCurrentQuestion >= 0 && idxCurrentQuestion < allQuestions.size() - 1 && prevOrNext == '+') {
            setCurrentQuestion(allQuestions.get(idxCurrentQuestion + 1));
        } else if (idxCurrentQuestion > 0 && idxCurrentQuestion <= allQuestions.size() - 1 && prevOrNext == '-')
            setCurrentQuestion(allQuestions.get(idxCurrentQuestion - 1));
    }

    @Override
    public void changeQuestion(int idxQuestion) throws IndexOutOfBoundsException {
        List<Question> allQuestions = getAllQuestions();
        setCurrentQuestion(allQuestions.get(idxQuestion - 1));
    }


    @Override
    public String terminate(Stats stats) {
        // update all parameters
        updateNonSelectedQuestions();
        updateCorrectWrongAndBlankQuestions();
        stats.updateStats(this);
        return computeResult();

    }

    private void updateNonSelectedQuestions() {
        getNonSelectedQuestions().stream().
                forEach(q -> q.setPriorityLevel(q.getPriorityLevel() + 1));
    }

    private void updateCorrectWrongAndBlankQuestions() {
        // correct
        getAllCorrectQuestions().stream().
                forEach(q -> q.setPriorityLevel(0));
        // wrong
        getAllWrongQuestions().stream().
                forEach(q -> q.setPriorityLevel(q.getPriorityLevel() + 2));
        // blank
        getAllBlankQuestions().stream().
                forEach(q -> q.setPriorityLevel(q.getPriorityLevel() + 2));
    }

    @Override
    public Set<Question> getSubtopicCorrectQuestions(Subtopic subtopic) {
        return getCorrectQuestions(subtopicToQuestions.get(subtopic));
    }

    @Override
    public Set<Question> getSubtopicWrongQuestions(Subtopic subtopic) {
        return getWrongQuestions(subtopicToQuestions.get(subtopic));
    }

    @Override
    public Set<Question> getSubtopicBlankQuestions(Subtopic subtopic) {
        return getBlankQuestions(subtopicToQuestions.get(subtopic));
    }

    @Override
    public Set<Question> getAllWrongQuestions() {
        return getWrongQuestions(new HashSet<>(getAllQuestions()));
    }

    @Override
    public Set<Question> getAllCorrectQuestions() {
        return getCorrectQuestions(new HashSet<>(getAllQuestions()));
    }

    @Override
    public Set<Question> getAllBlankQuestions() {
        return getBlankQuestions(new HashSet<>(getAllQuestions()));
    }
    private Set<Question> getCorrectQuestions(Set<Question> questions) {
        return questions.stream().
                filter(this::isCorrect).
                collect(Collectors.toSet());
    }

    private Set<Question> getWrongQuestions(Set<Question> questions) {
        return questions.stream().
                filter(q -> !isCorrect(q) && questionToAnswer.get(q) != '-').
                collect(Collectors.toSet());
    }

    private Set<Question> getBlankQuestions(Set<Question> questions) {
        return questions.stream().
                filter(q -> questionToAnswer.get(q) == '-').
                collect(Collectors.toSet());
    }
    @Override
    public Set<Question> getNonSelectedQuestions() {
        return getAllSelected_NonSelectedQuestions().stream().
                filter(q -> !getAllQuestions().contains(q)).
                collect(Collectors.toSet());
    }
    @Override
    public Set<Question> getAllSelected_NonSelectedQuestions() {
        return subtopicToQuestions.keySet().stream().
                flatMap(s -> s.getQuestions().stream()).
                collect(Collectors.toSet());
    }
    @Override
    public Set<Question> getSubtopicSelected_NonSelectedQuestions(Subtopic subtopic) {
        return subtopic.getQuestions();
    }

    private String computeResult () {
        Score simStats = computeSimStats();
        String simResult = getResult(simStats, "Simulation");
        String subtopicsResult = "";
        for (Subtopic subtopic : subtopicToQuestions.keySet()) {
            Score subtopicStats = computeSubtopicStats(subtopic);
            subtopicsResult += getResult(subtopicStats, subtopic.getSubtopicName());
        }
        return simResult + subtopicsResult;
    }

    private static String getResult(Score score, String subtopicSim) {
        String corAns = "Number of correct answers: " + score.correct() + "/" + score.selected();
        String wrongAns = "Number of wrong answers: " + score.wrong() + "/" + score.selected();
        String blankAns = "Number of blank answers: " + score.blank() + "/" + score.selected();
        String perc = "Percentage of correct answers: " + score.percentage() + "%";
        String result = subtopicSim + " result:" + System.lineSeparator() + corAns + System.lineSeparator()
                + wrongAns + System.lineSeparator() + blankAns + System.lineSeparator() +
                perc + System.lineSeparator() + System.lineSeparator();
        return result;
    }


    @Override
    public Score computeSubtopicStats(Subtopic subtopic) throws NullPointerException {
            Set<Question> questions = subtopicToQuestions.get(subtopic);
            return computeStats(questions, subtopic);
    }
    @Override
    public Score computeSimStats() {
        return computeStats(new HashSet<>(getAllQuestions()), null);
    }

    private Score computeStats(Set<Question> questions, Subtopic subtopic) {
        int nrOfCorrectAnswers = getCorrectQuestions(questions).size();
        int nrOfWrongAnswers = getWrongQuestions(questions).size();
        int nrOfBlankAnswers = getBlankQuestions(questions).size();
        int selected = (subtopic == null) ? getAllQuestions().size() : subtopicToQuestions.get(subtopic).size();
        int total = (subtopic == null) ? getAllSelected_NonSelectedQuestions().size() :
                getSubtopicSelected_NonSelectedQuestions(subtopic).size();

        double percentage = ((double) nrOfCorrectAnswers / questions.size()) * 100;
        percentage = Math.floor(percentage * 100)/100;
        return new Score(nrOfCorrectAnswers, nrOfWrongAnswers, nrOfBlankAnswers, selected, total, percentage);
    }
    @Override
    public boolean isCorrect(Question question) throws NullPointerException {
        if (questionToAnswer.get(question) == null)
            throw new NullPointerException();
        return questionToAnswer.get(question) == question.getCorrectAnswerLabel(questionToShuffledAnswers.get(question));
    }
    @Override
    public List<Question> getAllQuestions() {
        return subtopicToQuestions.entrySet().stream().
                flatMap(e -> e.getValue().stream()).
                collect(Collectors.toList());
    }

    @Override
    public Map<Subtopic, Set<Question>> getSubtopicToQuestions() {
        return subtopicToQuestions;
    }

    @Override
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    @Override
    public String getTopic() {
        List<Subtopic> subtopics = new ArrayList<>(subtopicToQuestions.keySet());
        return subtopics.get(0).getTopic();
    }

    @Override
    public Topic getTopicReference() {
        List<Subtopic> subtopics = new ArrayList<>(subtopicToQuestions.keySet());
        return subtopics.get(0).getTopicReference();
    }

    @Override
    public Map<Question, Character> getQuestionToAnswer() {
        return questionToAnswer;
    }

    @Override
    public Map<Question, Map<String, Character>> getQuestionToShuffledAnswers() {
        return questionToShuffledAnswers;
    }

    /**
     * do not use this method, only for debug purposes
     * @param currentQuestion the question you want to set as the current one
     */
    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public void setSubtopicToQuestions(Map<Subtopic, Set<Question>> subtopicToQuestions) {
        this.subtopicToQuestions = subtopicToQuestions;
    }

    public void setQuestionToAnswer(Map<Question, Character> questionToAnswer) {
        this.questionToAnswer = questionToAnswer;
    }

    public void setQuestionToShuffledAnswers(Map<Question, Map<String, Character>> questionToShuffledAnswers) {
        this.questionToShuffledAnswers = questionToShuffledAnswers;
    }
}
