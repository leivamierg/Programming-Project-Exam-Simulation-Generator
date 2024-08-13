package it.unibz.model.implementations;

import java.io.IOException;
import java.util.*;

import it.unibz.model.interfaces.ModelInt;

public class Model implements ModelInt {

    private String RESOURCES_PATH = System.getProperty("user.dir") + "/src/main/resources/bank/";
    private Set<Topic> topics = null;
    private Set<Subtopic> subtopics = null;
    private static Stats stats;
    private static History history;
    private static final int DURATION_SIMULATION = 60 * 30;
    private static int remainingTimeSimulation;

    public Model() {

        try {
            stats = HistoryStatsLoader.loadStats("src/main/resources/h_s/stats.json");
        } catch (Exception e) {
            stats = new Stats();
        }

        try {
            history = HistoryStatsLoader.loadHistory("src/main/resources/h_s/history.json");
        } catch (Exception e) {
            history = new History();
        }

        try {
            topics = FileLoader.loadBank(RESOURCES_PATH);
        } catch (IOException e) {
            System.out.println("We couldn't find any topics for the test in " + RESOURCES_PATH);
            e.printStackTrace();
        }
    }

    @Override
    public void list() {
        for (Topic topic : this.topics)
            System.out.println(topic.getTopicName());
    }

    @Override
    public void listSubtopics(String topicString) {
        String stdTopic = topicString.toLowerCase().strip();
        Topic selectedTopic = topics.stream()
                .filter(t -> t.getTopicName().equalsIgnoreCase(stdTopic))
                .findFirst()
                .orElse(null);

        if (selectedTopic == null) {
            System.out.println("The topic you are looking for doesn't exist.");
            return;
        }

        for (Subtopic subtopic : selectedTopic.getSubtopics())
            System.out.println(subtopic.getSubtopicName());

    }

    @Override
    public void test(String topic, String subtopic) {
        Topic selectedTopic = topics.stream()
                .filter(t -> t.getTopicName().equalsIgnoreCase(topic.strip()))
                .findFirst()
                .orElse(null);

        if (selectedTopic == null) {
            System.out.println("Invalid command or the topic you are looking for doesn't exist.");
            return;
        }

        if (subtopic == null) {
            testAllSubtopics(selectedTopic);
            return;
        }

        Subtopic selectedSubtopic = selectedTopic.getSubtopics().stream()
                .filter(s -> s.getSubtopicName().equalsIgnoreCase(subtopic.strip()))
                .findFirst()
                .orElse(null);

        if (selectedSubtopic == null) {
            System.out.println("The subtopic you are looking for doesn't exist.");
            return;
        }

        testSubtopic(selectedSubtopic);
    }

    // TODO:
    private void testAllSubtopics(Topic selectedTopic) {
        Simulation simulation = new Simulation(selectedTopic);

        Scanner scanner = new Scanner(System.in);
        int numberOfQuestionsSubtopic;

        while (true) {
            try {
                System.out.println("How many questions per subtopic?");
                numberOfQuestionsSubtopic = scanner.nextInt();
                break;
            } catch (Exception e) {
                scanner.nextLine();
            }
        }

        scanner.nextLine();
        simulation.select(selectedTopic, numberOfQuestionsSubtopic);
        simulation.start();

        runSimulation(simulation, scanner);
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static String formatTime(int seconds) {
        int mins = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }

    private void testSubtopic(Subtopic selectedSubtopic) {
    }

    private String sanitizeAnswer(String answer) {
        String res = answer.toUpperCase();
        if (!res.matches("[A-D+-]|\\s+|\\d+")) {
            return "n";
        }

        return res;
    }

    @Override
    public void testSubtopics(String topicName) {
        Topic selectedTopic = topics.stream()
                .filter(t -> t.getTopicName().equalsIgnoreCase(topicName.strip()))
                .findFirst()
                .orElse(null);

        if (selectedTopic == null) {
            System.out.println("The topic doesn't exist.");
            return;
        }

        List<Subtopic> subtopicsList = new ArrayList<>(selectedTopic.getSubtopics());

        if (subtopicsList.isEmpty()) {
            System.out.println("No subtopics available");
            return;
        }

        System.out.println("Select subtopics:");
        for (int i = 0; i < subtopicsList.size(); i++) {
            char letter = (char) ('A' + i);
            System.out.println(letter + ": " + subtopicsList.get(i).getSubtopicName());
        }

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toUpperCase().strip();

        Set<Subtopic> selectedSubtopics = new HashSet<>();
        for (char ch : input.toCharArray()) {
            int index = ch - 'A';
            if (index >= 0 && index < subtopicsList.size()) {
                selectedSubtopics.add(subtopicsList.get(index));
            } else {
                System.out.println("Invalid selection: " + ch);
                return;
            }
        }

        if (selectedSubtopics.isEmpty()) {
            System.out.println("No valid subtopics selected.");
            return;
        }

        Simulation simulation = new Simulation(selectedTopic);
        int numberOfQuestionsSubtopic;
        while (true) {
            try {
                System.out.println("How many questions per subtopic?");
                numberOfQuestionsSubtopic = scanner.nextInt();
                break;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Invalid number. Please enter an integer.");
            }
        }

        scanner.nextLine();
        simulation.select(selectedSubtopics, numberOfQuestionsSubtopic);
        simulation.start();

        runSimulation(simulation, scanner);
    }

    // helper for duplicated code

    private void runSimulation(Simulation simulation, Scanner scanner) {
        remainingTimeSimulation = DURATION_SIMULATION;

        while (remainingTimeSimulation > 0) {
            long questionStartTime = System.currentTimeMillis();

            clearConsole();
            System.out.println("Timer: " + formatTime(remainingTimeSimulation));

            Question currentQuestion = simulation.getCurrentQuestion();
            int indexCurrentQuestion = simulation.getAllQuestions().indexOf(currentQuestion) + 1;
            int totalNumberQuestions = simulation.getAllQuestions().size();

            System.out.println("Subtopic: " + currentQuestion.getSubtopic());
            System.out.println("Index question: " + indexCurrentQuestion + "/" + totalNumberQuestions);
            System.out.println("Previous answer: " + simulation.getAnswer(currentQuestion));
            System.out.println(currentQuestion.getQuestionAndAnswers());
            System.out.print("Select an answer: ");

            String answerInput = scanner.nextLine().trim();

            if (answerInput.equalsIgnoreCase("terminate")) {
                System.out.println(simulation.terminate(stats, history));
                break;
            }

            long questionEndTime = System.currentTimeMillis();
            int timeSpentOnQuestion = (int) ((questionEndTime - questionStartTime) / 1000);
            remainingTimeSimulation -= timeSpentOnQuestion;

            if (remainingTimeSimulation <= 0) {
                break;
            }

            if (indexCurrentQuestion == totalNumberQuestions) {
                System.out.println(simulation.terminate(stats, history));
                break;
            }

            try {
                simulation.insertCommand(sanitizeAnswer(answerInput));
            } catch (Exception e) {
                System.err.println("Invalid command!");
                continue;
            }
        }
        System.out.println("Test completed.");
    }

    public static Stats getLoadedStats() {
        return stats;
    }

    public static History getLoadedHistory() {
        return history;
    }

    public static String getRemainingTime() {
        return String.valueOf(formatTime(DURATION_SIMULATION - remainingTimeSimulation));
    }

    // randomizer method for daily challenge questions (from all the topics)
    public List<Question> getRandomQuestions(int numberOfQuestions) {
        List<Question> allQuestions = new ArrayList<>();
        for (Topic topic : FileLoader.getTopics()) {
            for (Subtopic subtopic : topic.getSubtopics()) {
                allQuestions.addAll(subtopic.getQuestions());
            }
        }
        Collections.shuffle(allQuestions);
        return allQuestions.subList(0, numberOfQuestions);
    }

    public Set<Topic> getTopics() {
        return topics;
    }
}
