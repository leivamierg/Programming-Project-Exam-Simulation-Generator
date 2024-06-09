package it.unibz.model.implementations;

import it.unibz.model.interfaces.SimulationInt;

import java.util.*;

public class Simulation implements SimulationInt {
    // attributes
    private Map<Subtopic, List<Question>> subtopicToQuestions;
    private Map<Question, Character> questionToAnswer;
    private Question currentQuestion;
    @Override
    public void select(Topic topic, int nrQuestionsPerSubtopic) throws NullPointerException {
            for (Subtopic subtopic : topic.getSubtopics()) {
                List<Question> pickedQuestions = new ArrayList<>(new Set<>(subtopic.pickQuestions(nrQuestionsPerSubtopic)));
                subtopicToQuestions.put(subtopic, pickedQuestions);
            }
    }
    @Override
    public void select(List<Subtopic> subtopics, int nrQuestionsPerSubtopic) throws IllegalStateException, NullPointerException {
        if (!subtopics.isEmpty()) {
            for (Subtopic subtopic : subtopics) {
                List<Question> pickedQuestions = new ArrayList<>(new Set<>(subtopic.pickQuestions(nrQuestionsPerSubtopic)));
                subtopicToQuestions.put(subtopic, pickedQuestions);
            }
        } else throw new IllegalStateException();
    }

    @Override
    public void start() {
        setCurrentQuestion(getAllQuestions().get(0));
    }
    @Override
    public void answer(char answer) throws IllegalArgumentException {
        switch (answer) {
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case '-': questionToAnswer.put(currentQuestion, answer);
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
        updatePriority();
        updateCorrectAndWrongQuestions();
        return computeResult();

    }

    private void updatePriority() {
        List<Question> allQuestions = getAllQuestions();
        for (Question question : allQuestions) {
            question.updatePriority();
        }
    }

    private void updateCorrectAndWrongQuestions() {
        for (Subtopic subtopic : subtopicToQuestions.keySet()) {
            subtopic.setCorrectQuestions(getSubtopicCorrectQuestions(subtopic));
            subtopic.setWrongQuestions(getSubtopicWrongQuestions(subtopic));
        }
    }



    private List<Question> getSubtopicCorrectQuestions(Subtopic subtopic) {
        return subtopicToQuestions.get(subtopic).stream().
                filter(this::isCorrect).
                toList();
    }

    private List<Question> getSubtopicWrongQuestions(Subtopic subtopic) {
        return subtopicToQuestions.get(subtopic).stream().
                filter(q -> !isCorrect(q)).
                toList();
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
            result += subtopic.getName() + ": " + System.lineSeparator() + simCorAns + System.lineSeparator()
                    + simPerc + System.lineSeparator();
        }
        return result;
    }


    @Override
    public CorrectAnswersAndPercentage computeSubtopicStats(Subtopic subtopic) throws NullPointerException {
            List<Question> questions = subtopicToQuestions.get(subtopic);
            return computeStats(questions);
        }

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
        return questionToAnswer.get(question) == question.getCorrectAnswerLabel();
    }
    private List<Question> getAllQuestions() {
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

    private void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }


}
