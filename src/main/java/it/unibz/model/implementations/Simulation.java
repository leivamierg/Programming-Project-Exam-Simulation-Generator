package it.unibz.model.implementations;

import it.unibz.model.interfaces.SimulationInt;

import java.util.*;
import java.util.stream.Collectors;

public class Simulation implements SimulationInt {
    // attributes
    private Map<Subtopic, List<Question>> subtopicToQuestions;
    private Map<Question, Character> questionToAnswer;
    private Map<Question, Map<String, Character>> questionToShuffledAnswers;
    private Question currentQuestion;

    public Simulation() {
        subtopicToQuestions = new HashMap<>();
        questionToAnswer = new HashMap<>();
        questionToShuffledAnswers = new HashMap<>();
    }

    @Override
    public void select(Topic topic, int nrQuestionsPerSubtopic) throws NullPointerException {
            for (Subtopic subtopic : topic.getSubtopics()) {
                updateSubtopicToQuestions(subtopic, nrQuestionsPerSubtopic);
            }
    }
    @Override
    public void select(Set<Subtopic> subtopics, int nrQuestionsPerSubtopic) throws IllegalStateException, NullPointerException {
        if (!subtopics.isEmpty()) {
            for (Subtopic subtopic : subtopics) {
                updateSubtopicToQuestions(subtopic, nrQuestionsPerSubtopic);
            }
        } else throw new IllegalStateException();
    }

    private void updateSubtopicToQuestions(Subtopic subtopic, int nrQuestionsPerSubtopic) {
        List<Question> pickedQuestions = subtopic.pickQuestions(nrQuestionsPerSubtopic);
        subtopicToQuestions.put(subtopic, pickedQuestions);
        updateQuestionToShuffledAnswers(pickedQuestions);
    }

    private void updateQuestionToShuffledAnswers(List<Question> questions) {
        for (Question question: questions) {
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
            case '-': questionToAnswer.put(currentQuestion, Character.toUpperCase(answer));
                break;
            default: throw new IllegalArgumentException();
        }
        List<Question> allQuestions = getAllQuestions();
        if (allQuestions.indexOf(currentQuestion) < allQuestions.size() - 1) {
            changeQuestion('+');
        }
    }

    @Override
    public void changeQuestion(char prevOrNext) throws IllegalArgumentException {
        List<Question> allQuestions = getAllQuestions();
        int idxCurrentQuestion = allQuestions.indexOf(currentQuestion);
        if (idxCurrentQuestion > 0 || idxCurrentQuestion < allQuestions.size() - 1) {
            switch (prevOrNext) {
                case '+':  setCurrentQuestion(allQuestions.get(idxCurrentQuestion + 1));
                    break;
                case '-': setCurrentQuestion(allQuestions.get(idxCurrentQuestion - 1));
                    break;
                default: throw new IllegalArgumentException();
            }
        }
    }

    @Override
    public void changeQuestion(int idxQuestion) throws IndexOutOfBoundsException {
        List<Question> allQuestions = getAllQuestions();
        setCurrentQuestion(allQuestions.get(idxQuestion - 1));
    }


    @Override
    public String terminate() {
        // update all parameters
        updateNonSelectedQuestions();
        updateCorrectAndWrongQuestions();
        return computeResult();

    }

    private void updateNonSelectedQuestions() {
        getNonSelectedQuestions().stream().
                forEach(q -> q.setPriorityLevel(q.getPriorityLevel() + 1));
    }

    private void updateCorrectAndWrongQuestions() {
        // correct
        getAllCorrectQuestions().stream().
                forEach(q -> q.setPriorityLevel(0));
        // wrong
        getAllWrongQuestions().stream().
                forEach(q -> q.setPriorityLevel(q.getPriorityLevel() + 2));
    }

    @Override
    public Set<Question> getSubtopicCorrectQuestions(Subtopic subtopic) {
        return getCorrect_WrongQuestions(subtopicToQuestions.get(subtopic), true);
    }

    @Override
    public Set<Question> getSubtopicWrongQuestions(Subtopic subtopic) {
        return getCorrect_WrongQuestions(subtopicToQuestions.get(subtopic), false);
    }

    @Override
    public Set<Question> getAllWrongQuestions() {
        return getCorrect_WrongQuestions(getAllQuestions(), false);
    }

    @Override
    public Set<Question> getAllCorrectQuestions() {
        return getCorrect_WrongQuestions(getAllQuestions(), true);
    }
    private Set<Question> getCorrect_WrongQuestions(List<Question> questions, boolean corWrong) {
        return questions.stream().
                filter(q -> isCorrect(q) && corWrong).
                collect(Collectors.toSet());
    }
    @Override
    public Set<Question> getNonSelectedQuestions() {
        return getAllQuestions().stream().
                filter(q -> !getAllQuestions().contains(q)).
                collect(Collectors.toSet());
    }

    private String computeResult () {
        CorrectAnswersAndPercentage simStats = computeSimStats();
        String simCorAns = "Number of correct answers: " + simStats.correctAnswers() + "/" + getAllQuestions().size();
        String simPerc = "Percentage of correct answers: " + simStats.percentage() + "%";
        String result = "Simulation result: " + System.lineSeparator() + simCorAns + System.lineSeparator()
                + simPerc + System.lineSeparator();
        for (Subtopic subtopic : subtopicToQuestions.keySet()) {
            CorrectAnswersAndPercentage subtopicStats = computeSubtopicStats(subtopic);
            String subtopicCorAns = "Number of correct answers: " + simStats.correctAnswers() + "/" + getAllQuestions().size();
            String subtopicPerc = "Percentage of correct answers: " + simStats.percentage() + "%";
            result += subtopic.getSubtopicName() + ": " + System.lineSeparator() + subtopicCorAns + System.lineSeparator()
                    + subtopicPerc + System.lineSeparator();
        }
        return result;
    }


    @Override
    public CorrectAnswersAndPercentage computeSubtopicStats(Subtopic subtopic) throws NullPointerException {
            List<Question> questions = subtopicToQuestions.get(subtopic);
            return computeStats(questions);
        }
    @Override
    public CorrectAnswersAndPercentage computeSimStats() {
        return computeStats(getAllQuestions());
    }

    private CorrectAnswersAndPercentage computeStats(List<Question> questions) {
        long numberOfCorrectAnswers = questions.stream().
                filter(this::isCorrect).
                count();
        double percentage = (double) numberOfCorrectAnswers / questions.size();
        return new CorrectAnswersAndPercentage(numberOfCorrectAnswers, percentage);
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
                toList();
    }

    @Override
    public Map<Subtopic, List<Question>> getQuestionsPerSubtopic() {
        return subtopicToQuestions;
    }

    @Override
    public Question getCurrentQuestion() {
        return currentQuestion;
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


}
